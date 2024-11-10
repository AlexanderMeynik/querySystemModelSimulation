package gui;

public class DispertionsInfoString {
    public Double getDStay() {
        return dStay;
    }

    public void setdStay(Double dStay) {
        this.dStay = Math.round(dStay*1000000.0)/1000000.0;;
    }

    public Double dStay;

    public Double getDProc() {
        return dProc;
    }

    public void setdProc(Double dProc) {
        this.dProc = Math.round(dProc*1000000.0)/1000000.0;
    }

    public Double dProc;

    public DispertionsInfoString(Double dStay,Double dProc) {
        this.dStay = Math.round(dStay*1000000.0)/100000.0;
        this.dProc=Math.round(dProc*1000000.0)/100000.0;
    }
}
