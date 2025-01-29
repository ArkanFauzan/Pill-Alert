package com.example.pillalert;

public class MedicineModel {
    private int id;
    private int diseaseId;
    private String name;
    private String description;
    private MedicineUnitEnum unit;
    private int dosePerDay;
    private int dosePerConsume;
    private int amount;
    private String startDate;
    private String endDate;

    public MedicineModel(int id,int diseaseId, String name, String description, MedicineUnitEnum unit, int dosePerDay, int dosePerConsume, int amount, String startDate, String endDate) {
        this.id = id;
        this.diseaseId = diseaseId;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.dosePerDay = dosePerDay;
        this.dosePerConsume = dosePerConsume;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public int getDiseaseId() {
        return diseaseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public MedicineUnitEnum getUnit() {
        return unit;
    }

    public int getDosePerDay() {
        return dosePerDay;
    }

    public int getDosePerConsume() {
        return dosePerConsume;
    }

    public int getAmount() {
        return amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartDateOnlyDate() {
        String[] splited = startDate.split("\\s+");
        return splited.length > 0 ? splited[0] : "";
    }

    public String getStartDateOnlyTime() {
        String[] splited = startDate.split("\\s+");
        return splited.length == 2 ? splited[1] : "";
    }

    public String getEndDate() {
        return endDate;
    }

    public String getConsumeSummary() {
        return dosePerDay + "x perhari @ " + dosePerConsume + " " + unit.getEnglishTranslation();
    }

}
