package com.example.pillalert;

public class MedicineTrackingModel {
    private int id;
    private int medicineId;
    private String targetDate;
    private String consumeDate;
    private MedicineTrackingTypeEnum trackingType;
    private String notes;

    public MedicineTrackingModel(int id, int medicineId, String targetDate, String consumeDate, MedicineTrackingTypeEnum trackingType, String notes) {
        this.id = id;
        this.medicineId = medicineId;
        this.targetDate = targetDate;
        this.consumeDate = consumeDate;
        this.trackingType = trackingType;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public String getTargetDateOnlyDate() {
        String[] split = targetDate.split("\\s+");
        return split.length > 0 ? split[0] : "";
    }

    public String getTargetDateOnlyTime() {
        String[] split = targetDate.split("\\s+");
        return split.length == 2 ? split[1] : "";
    }

    public String getConsumeDate() {
        return consumeDate;
    }

    public String getConsumeDateOnlyDate() {
        String[] split = consumeDate.split("\\s+");
        return split.length > 0 ? split[0] : "";
    }

    public String getConsumeDateOnlyTime() {
        String[] split = consumeDate.split("\\s+");
        return split.length == 2 ? split[1] : "";
    }

    public MedicineTrackingTypeEnum getTrackingType() {
        return trackingType;
    }

    public String getNotes() {
        return notes;
    }
}
