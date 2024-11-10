package gui;

public class buffTabbleString {
    private Integer bufferNum;
    private Double currectTime;
    private Integer sourceNumber;
    private Integer requestNumber;

    public buffTabbleString(Integer bufferNum, Double currentTime, Integer sourceNumber, Integer requestNumber) {
        this.bufferNum = bufferNum;
        this.currectTime = Math.round(currentTime * 1000.0) / 1000.0;
        this.sourceNumber = sourceNumber;
        this.requestNumber = requestNumber;
    }

    public Integer getBufferNum() {
        return bufferNum;
    }

    public void setBufferNum(Integer bufferNum) {
        this.bufferNum = bufferNum;
    }

    public Double getCurrectTime() {
        return currectTime;
    }

    public void setCurrectTime(Double currectTime) {
        this.currectTime = currectTime;
    }

    public Integer getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(Integer sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public Integer getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(Integer requestNumber) {
        this.requestNumber = requestNumber;
    }
}
