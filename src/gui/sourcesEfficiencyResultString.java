package gui;

public class sourcesEfficiencyResultString {

    private String source;
    private Integer amountOfRequests;
    private Double probabilityOfRefusal;
    private Double tStay;
    private Double tWait;
    private Double tProc;


    public sourcesEfficiencyResultString(String source, Integer amountOfRequests, Double probabilityOfRefusal, Double tStay, Double tWait, Double tProc) {
        this.source = source;
        this.tStay = Math.round(tStay * 1000.0) / 1000.0;
        this.probabilityOfRefusal = Math.round(probabilityOfRefusal * 10000.0) / 10000.0;
        this.amountOfRequests = amountOfRequests;
        this.tWait = Math.round(tWait * 1000.0) / 1000.0;
        this.tProc = Math.round(tProc * 1000.0) / 1000.0;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Integer getAmountOfRequests() {
        return amountOfRequests;
    }

    public void setAmountOfRequests(Integer amountOfRequests) {
        this.amountOfRequests = amountOfRequests;
    }

    public Double getProbabilityOfRefusal() {
        return probabilityOfRefusal;
    }

    public void setProbabilityOfRefusal(Double probabilityOfRefusal) {
        this.probabilityOfRefusal = probabilityOfRefusal;
    }

    public Double getTStay() {
        return tStay;
    }

    public void setTStay(Double tStay) {
        this.tStay = tStay;
    }

    public Double getTWait() {
        return tWait;
    }

    public void settWait(Double tWait) {
        this.tWait = tWait;
    }

    public Double getTProc() {
        return tProc;
    }

    public void settProc(Double tProc) {
        this.tProc = tProc;
    }
}
