package com.gr.ntua.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.gr.ntua.web.rest.vm.Busulfan.BusulfanStats;
import com.gr.ntua.web.rest.vm.Busulfan.BusulfanVM;
import org.apache.commons.math.stat.regression.SimpleRegression;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URISyntaxException;


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
        Double duration1 = period.getHours()+period.getMinutes()/60.0;

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime3().getTime()).toPeriod();
        Double duration2 = period.getHours()+period.getMinutes()/60.0;

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime4().getTime()).toPeriod();
        Double duration3 = period.getHours()+period.getMinutes()/60.0;

        period = new Interval(busulfanVM.getTime1().getTime(), busulfanVM.getTime5().getTime()).toPeriod();
        Double duration4 = period.getHours()+period.getMinutes()/60.0;

        Double AUC02 = duration1 * (busulfanVM.getConcentration1()+busulfanVM.getConcentration2())/2.0;
        Double AUC2_230 = (duration2-duration1) * (busulfanVM.getConcentration2()+busulfanVM.getConcentration3())/2.0;
        Double AUC230_4 = (duration3-duration2) * (busulfanVM.getConcentration3()+busulfanVM.getConcentration4())/2.0;
        Double AUC4_6 = (duration4-duration3) * (busulfanVM.getConcentration4()+busulfanVM.getConcentration5())/2.0;

        busulfanStats.setAUCtotal(AUC02+AUC2_230+AUC230_4+AUC4_6);
        busulfanStats.setAUCtotal243(busulfanStats.getAUCtotal()*243.6);
        busulfanStats.setCL(busulfanVM.getDosage()/busulfanStats.getAUCtotal());
        busulfanStats.setCLkg(busulfanStats.getCL()*busulfanVM.getPatient().getWeight());
        busulfanStats.setDoseAdjusted(busulfanVM.getDosage()*1125/busulfanStats.getAUCtotal243());

        log.info(duration1+" "+duration2+" "+duration3+" "+duration4);
        log.info("AUC total "+busulfanStats.getAUCtotal());
        log.info("AUC TOTAL *243,6 μM/min "+busulfanStats.getAUCtotal243());
        log.info("CL (L/h*kg) "+busulfanStats.getCL());
        log.info("Βάρος "+busulfanVM.getPatient().getWeight());
        log.info("CL (L/h)) "+busulfanStats.getCLkg());
        log.info("DOSE adjusted "+busulfanStats.getDoseAdjusted());

        SimpleRegression regression = new SimpleRegression();

        regression.addData(duration1,Math.log(busulfanVM.getConcentration2()));
        regression.addData(duration2,Math.log(busulfanVM.getConcentration3()));
        regression.addData(duration3,Math.log(busulfanVM.getConcentration4()));
        regression.addData(duration4,Math.log(busulfanVM.getConcentration5()));

        busulfanStats.setKel(-regression.getSlope());
        busulfanStats.setT1_2(Math.log(2)/busulfanStats.getKel());
        log.info("kel "+ busulfanStats.getKel());
        log.info("t1_2 "+ busulfanStats.getT1_2());

        return ResponseEntity.ok()
            .body(busulfanStats);
    }


}
