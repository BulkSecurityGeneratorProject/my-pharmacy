package com.gr.ntua.web.rest.vm.Busulfan;

/**
 * Created by Angelos Valsamis on 11/11/2016.
 */
public class BusulfanStats {

    private Double relativeTime1;
    private Double relativeTime2;
    private Double relativeTime3;
    private Double relativeTime4;

    private Double AUCtotal;
    private Double AUCtotal243;
    private Double CL;
    private Double CLkg;
    private Double DoseAdjusted;
    private Double kel;
    private Double t1_2;

    public BusulfanStats() {
    }

    public BusulfanStats(Double AUCtotal, Double AUCtotal243, Double CL, Double CLkg, Double doseAdjusted, Double kel, Double t1_2) {
        this.AUCtotal = AUCtotal;
        this.AUCtotal243 = AUCtotal243;
        this.CL = CL;
        this.CLkg = CLkg;
        DoseAdjusted = doseAdjusted;
        this.kel = kel;
        this.t1_2 = t1_2;
    }

    public Double getRelativeTime1() {
        return relativeTime1;
    }

    public void setRelativeTime1(Double relativeTime1) {
        this.relativeTime1 = relativeTime1;
    }

    public Double getRelativeTime2() {
        return relativeTime2;
    }

    public void setRelativeTime2(Double relativeTime2) {
        this.relativeTime2 = relativeTime2;
    }

    public Double getRelativeTime3() {
        return relativeTime3;
    }

    public void setRelativeTime3(Double relativeTime3) {
        this.relativeTime3 = relativeTime3;
    }

    public Double getRelativeTime4() {
        return relativeTime4;
    }

    public void setRelativeTime4(Double relativeTime4) {
        this.relativeTime4 = relativeTime4;
    }

    public Double getAUCtotal() {
        return AUCtotal;
    }

    public void setAUCtotal(Double AUCtotal) {
        this.AUCtotal = AUCtotal;
    }

    public Double getAUCtotal243() {
        return AUCtotal243;
    }

    public void setAUCtotal243(Double AUCtotal243) {
        this.AUCtotal243 = AUCtotal243;
    }

    public Double getCL() {
        return CL;
    }

    public void setCL(Double CL) {
        this.CL = CL;
    }

    public Double getCLkg() {
        return CLkg;
    }

    public void setCLkg(Double CLkg) {
        this.CLkg = CLkg;
    }

    public Double getDoseAdjusted() {
        return DoseAdjusted;
    }

    public void setDoseAdjusted(Double doseAdjusted) {
        DoseAdjusted = doseAdjusted;
    }

    public Double getKel() {
        return kel;
    }

    public void setKel(Double kel) {
        this.kel = kel;
    }

    public Double getT1_2() {
        return t1_2;
    }

    public void setT1_2(Double t1_2) {
        this.t1_2 = t1_2;
    }
}
