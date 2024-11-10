package gui;

public class DeviceEfficiencyResultString {


    private String source;
    private Double efficiency;

    public DeviceEfficiencyResultString(String source, Double efficiency) {
        this.source = source;
        this.efficiency = Math.round(efficiency * 1000.0) / 1000.0;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }
}
