package inso.rest.model;

import org.parceler.Parcel;

import java.util.Date;

public class PowerPlant {

    public PowerPlant() {
    }

    private Integer id;

    private String name;

    private Date commissioningDate;

    private String turbineType;

    private String pressureType;

    private Double breakdownRate;

    private Double yearlyIncreaseOfMaintenance;

    private Integer residualTime;

    private Integer periodOfOverflow;

    private String storageType;

    private String maintenanceStrategy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCommissioningDate() {
        return commissioningDate;
    }

    public void setCommissioningDate(Date comissioningDate) {
        this.commissioningDate = comissioningDate;
    }

    public String getTurbineType() {
        return turbineType;
    }

    public void setTurbineType(String turbineType) {
        this.turbineType = turbineType;
    }

    public String getPressureType() {
        return pressureType;
    }

    public void setPressureType(String pressureType) {
        this.pressureType = pressureType;
    }

    public Double getBreakdownRate() {
        return breakdownRate;
    }

    public void setBreakdownRate(Double breakdownRate) {
        this.breakdownRate = breakdownRate;
    }

    public Double getYearlyIncreaseOfMaintenance() {
        return yearlyIncreaseOfMaintenance;
    }

    public void setYearlyIncreaseOfMaintenance(Double yearlyIncreaseOfMaintenance) {
        this.yearlyIncreaseOfMaintenance = yearlyIncreaseOfMaintenance;
    }

    public Integer getResidualTime() {
        return residualTime;
    }

    public void setResidualTime(Integer residualTime) {
        this.residualTime = residualTime;
    }

    public Integer getPeriodOfOverflow() {
        return periodOfOverflow;
    }

    public void setPeriodOfOverflow(Integer periodOfOverflow) {
        this.periodOfOverflow = periodOfOverflow;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getMaintenanceStrategy() {
        return maintenanceStrategy;
    }

    public void setMaintenanceStrategy(
            String maintenanceStrategy) {
        this.maintenanceStrategy = maintenanceStrategy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PowerPlant{" +
                "name='" + name + '\'' +
                ", commissioningDate=" + commissioningDate +
                ", turbineType='" + turbineType + '\'' +
                ", pressureType='" + pressureType + '\'' +
                ", breakdownRate=" + breakdownRate +
                ", yearlyIncreaseOfMaintenance=" + yearlyIncreaseOfMaintenance +
                ", residualTime=" + residualTime +
                ", periodOfOverflow=" + periodOfOverflow +
                ", storageType='" + storageType + '\'' +
                ", maintenanceStrategy='" + maintenanceStrategy + '\'' +
                '}';
    }
}
