package com.gr.ntua.web.rest.vm.Busulfan;

import java.util.Date;
import java.util.TreeMap;

/**
 * Created by Angelos Valsamis on 18/11/2016.
 */
public class PlotValues {

    private TreeMap<Double,Double> map;

    public PlotValues() {
    }

    public PlotValues(TreeMap<Double, Double> map) {
        this.map = map;
    }

    public TreeMap<Double, Double> getMap() {
        return map;
    }

    public void setMap(TreeMap<Double, Double> map) {
        this.map = map;
    }
}
