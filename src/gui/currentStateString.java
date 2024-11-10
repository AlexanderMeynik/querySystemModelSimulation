package gui;

public class currentStateString {
    private String device;
    private Double currentTime;
    private String state;
    private  String requestInfo;

    public currentStateString(String device, Double currentTime, String state, String requestInfo) {
        this.device = device;
        this.currentTime = Math.round(currentTime * 1000.0) / 1000.0;
        this.state = state;
        this.requestInfo = requestInfo;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Double getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Double currentTime) {
        this.currentTime = currentTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }
}
