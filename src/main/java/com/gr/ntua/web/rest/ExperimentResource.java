package com.gr.ntua.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gr.ntua.web.rest.vm.Busulfan.BusulfanStats;
import com.gr.ntua.web.rest.vm.Busulfan.BusulfanVM;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.Base64;


/**
 * REST controller for managing Experiments.
 */
@RestController
@RequestMapping("/api")
public class ExperimentResource {

    private final Logger log = LoggerFactory.getLogger(ExperimentResource.class);

    /**
     * POST  /patients : Create busulfan report.
     *
     * @param busulfanVM the busulfan stats to create report for
     */
    @RequestMapping(value = "/experiments/busulfan",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BusulfanStats> createBusulfanReport(@Valid @RequestBody BusulfanVM busulfanVM) throws URISyntaxException {
        log.debug("REST request to create Busulfan report for patient: {}", busulfanVM.getPatient().getSurname() + " "+ busulfanVM.getPatient().getName());

        BusulfanStats busulfanStats = new BusulfanStats();

        Period period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime2().getTime()).toPeriod();
        busulfanStats.setRelativeTime1(period.getHours()+period.getMinutes()/60.0);

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime3().getTime()).toPeriod();
        busulfanStats.setRelativeTime2(period.getHours()+period.getMinutes()/60.0);

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime4().getTime()).toPeriod();
        busulfanStats.setRelativeTime3(period.getHours()+period.getMinutes()/60.0);

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime5().getTime()).toPeriod();
        busulfanStats.setRelativeTime4(period.getHours()+period.getMinutes()/60.0);

        Double AUC02 = busulfanStats.getRelativeTime1() * (busulfanVM.getConcentration1()+busulfanVM.getConcentration2())/2.0;
        Double AUC2_230 = (busulfanStats.getRelativeTime2()-busulfanStats.getRelativeTime1()) * (busulfanVM.getConcentration2()+busulfanVM.getConcentration3())/2.0;
        Double AUC230_4 = (busulfanStats.getRelativeTime3()-busulfanStats.getRelativeTime2()) * (busulfanVM.getConcentration3()+busulfanVM.getConcentration4())/2.0;
        Double AUC4_6 = (busulfanStats.getRelativeTime4()-busulfanStats.getRelativeTime3()) * (busulfanVM.getConcentration4()+busulfanVM.getConcentration5())/2.0;

        busulfanStats.setAUCtotal(AUC02+AUC2_230+AUC230_4+AUC4_6);
        busulfanStats.setAUCtotal243(busulfanStats.getAUCtotal()*243.6);
        busulfanStats.setCL(busulfanVM.getDosage()/busulfanStats.getAUCtotal());
        busulfanStats.setCLkg(busulfanStats.getCL()*busulfanVM.getPatient().getWeight());
        busulfanStats.setDoseAdjusted(busulfanVM.getDosage()*1125/busulfanStats.getAUCtotal243());

        log.info(busulfanStats.getRelativeTime1()+" "+busulfanStats.getRelativeTime2()+" "+busulfanStats.getRelativeTime3()+" "+busulfanStats.getRelativeTime4());
        log.info("AUC total "+busulfanStats.getAUCtotal());
        log.info("AUC TOTAL *243,6 μM/min "+busulfanStats.getAUCtotal243());
        log.info("CL (L/h*kg) "+busulfanStats.getCL());
        log.info("Βάρος "+busulfanVM.getPatient().getWeight());
        log.info("CL (L/h)) "+busulfanStats.getCLkg());
        log.info("DOSE adjusted "+busulfanStats.getDoseAdjusted());

        SimpleRegression regression = new SimpleRegression();

        regression.addData(busulfanStats.getRelativeTime1(),Math.log(busulfanVM.getConcentration2()));
        regression.addData(busulfanStats.getRelativeTime2(),Math.log(busulfanVM.getConcentration3()));
        regression.addData(busulfanStats.getRelativeTime3(),Math.log(busulfanVM.getConcentration4()));
        regression.addData(busulfanStats.getRelativeTime4(),Math.log(busulfanVM.getConcentration5()));

        busulfanStats.setKel(-regression.getSlope());
        busulfanStats.setT1_2(Math.log(2)/busulfanStats.getKel());
        log.info("kel "+ busulfanStats.getKel());
        log.info("t1_2 "+ busulfanStats.getT1_2());

        busulfanStats.setKel(round(busulfanStats.getKel(),2));
        busulfanStats.setCL(round(busulfanStats.getCL(),2));
        busulfanStats.setAUCtotal(round(busulfanStats.getAUCtotal(),2));
        busulfanStats.setT1_2(round(busulfanStats.getT1_2(),2));
        busulfanStats.setAUCtotal243(round(busulfanStats.getAUCtotal243(),2));
        busulfanStats.setCLkg(round(busulfanStats.getCLkg(),2));
        busulfanStats.setDoseAdjusted(round(busulfanStats.getDoseAdjusted(),2));


        return ResponseEntity.ok()
            .body(busulfanStats);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



    @RequestMapping(value = "/experiments/simplePlot",
        method = RequestMethod.GET,
        produces = MediaType.IMAGE_PNG_VALUE)
    @Timed
    public ResponseEntity<byte[]> getPlot(@RequestParam String x, @RequestParam String y, @RequestParam Double[] xxs, @RequestParam Double[] yys)  {

        try {
            XYSeries series = new XYSeries("PK Profile");

            for (int i = 0; i < xxs.length; ++i) {
                series.add(xxs[i],yys[i]);
            }

            final XYSeriesCollection data = new XYSeriesCollection(series);
            final JFreeChart chart = ChartFactory.createXYLineChart(
                "",
                x,
                y,
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            );
            Color trans = new Color(0xFF, 0xFF, 0xFF, 0);
            chart.setBackgroundPaint(trans);

            ByteArrayOutputStream bao = new ByteArrayOutputStream();

            ChartUtilities.writeChartAsPNG(bao,chart,300,300);
            byte[] base64 = Base64.getEncoder().encode(bao.toByteArray());

            return new ResponseEntity<byte[]>(base64, HttpStatus.CREATED);

        } catch (IOException e) {
            log.error(String.valueOf(e));
            throw new RuntimeException(e);
        }
    }



}
