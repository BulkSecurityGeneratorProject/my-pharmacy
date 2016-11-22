package com.gr.ntua.web.rest.vm.Busulfan;

import com.gr.ntua.domain.Drug;
import com.gr.ntua.domain.Patient;

import java.util.Date;

/**
 * Created by Angelos Valsamis on 11/11/2016.
 */
public class BusulfanVM {
    private Patient patient;
    private Drug drug;
    private Double dosage;
    private Date time1;
    private Date time2;
    private Date time3;
    private Date time4;
    private Date time5;
    private Double concentration1;
    private Double concentration2;
    private Double concentration3;
    private Double concentration4;
    private Double concentration5;

    public BusulfanVM() {
    }

    public BusulfanVM(Patient patient, Drug drug, Double dosage, Date time1, Date time2, Date time3, Date time4, Date time5, Double concentration1, Double concentration2, Double concentration3, Double concentration4, Double concentration5) {
        this.patient = patient;
        this.drug = drug;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.dosage = dosage;
        this.concentration1 = concentration1;
        this.concentration2 = concentration2;
        this.concentration3 = concentration3;
        this.concentration4 = concentration4;
        this.concentration5 = concentration5;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Drug getDrug() {
        return drug;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public Date getTime3() {
        return time3;
    }

    public void setTime3(Date time3) {
        this.time3 = time3;
    }

    public Date getTime4() {
        return time4;
    }

    public void setTime4(Date time4) {
        this.time4 = time4;
    }

    public Date getTime5() {
        return time5;
    }

    public void setTime5(Date time5) {
        this.time5 = time5;
    }

    public Double getConcentration1() {
        return concentration1;
    }

    public void setConcentration1(Double concentration1) {
        this.concentration1 = concentration1;
    }

    public Double getConcentration2() {
        return concentration2;
    }

    public void setConcentration2(Double concentration2) {
        this.concentration2 = concentration2;
    }

    public Double getConcentration3() {
        return concentration3;
    }

    public void setConcentration3(Double concentration3) {
        this.concentration3 = concentration3;
    }

    public Double getConcentration4() {
        return concentration4;
    }

    public void setConcentration4(Double concentration4) {
        this.concentration4 = concentration4;
    }

    public Double getConcentration5() {
        return concentration5;
    }

    public void setConcentration5(Double concentration5) {
        this.concentration5 = concentration5;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    @Override
    public String toString() {
        return "BusulfanVM{" +
            "patient=" + patient +
            ", drug=" + drug +
            ", time1=" + time1 +
            ", time2=" + time2 +
            ", time3=" + time3 +
            ", time4=" + time4 +
            ", time5=" + time5 +
            ", concentration1=" + concentration1 +
            ", concentration2=" + concentration2 +
            ", concentration3=" + concentration3 +
            ", concentration4=" + concentration4 +
            ", concentration5=" + concentration5 +
            '}';
    }
}
