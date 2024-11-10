package gui;

public class EventOccurString {
    private String event;
    private String action;
    private Double time;
    private Integer countEvents;
    private Integer countDeny;


    public EventOccurString(String event, Double time, String action, Integer countEvents, Integer countDeny) {
        this.event = event;
        this.action = action;
        this.time = Math.round(time * 1000.0) / 1000.0;
        this.countEvents = countEvents;
        this.countDeny = countDeny;
    }

    public Double getTime() {
        return time;
    }

    public String getEvent() {
        return event;
    }

    public String getAction() {
        return action;
    }

    public Integer getCountEvents() {
        return countEvents;
    }

    public Integer getCountDeny() {
        return countDeny;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public void setCountEvents(Integer countEvents) {
        this.countEvents = countEvents;
    }

    public void setCountDeny(Integer countDeny) {
        this.countDeny = countDeny;
    }
}
