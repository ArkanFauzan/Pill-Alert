package com.example.pillalert;

public enum MedicineTrackingTypeEnum {
    OnTime(0, "On Time"),
    Late(1, "Late"),
    Faster(2, "Faster"),
    NoDrink(3, "No Drink");

    private final int value;
    private final String englishTranslation;

    MedicineTrackingTypeEnum(int value, String englishTranslation) {
        this.value = value;
        this.englishTranslation = englishTranslation;
    }

    public int getValue() {
        return value;
    }

    public String getEnglishTranslation() {
        return englishTranslation;
    }

    // Method to get StatusEnum from integer
    public static MedicineTrackingTypeEnum fromValue(int value) {
        for (MedicineTrackingTypeEnum status : MedicineTrackingTypeEnum.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
