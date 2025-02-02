package com.example.pillalert;

public enum MedicineTrackingTypeEnum {
    NotYet(0, "-"),
    OnTime(1, "Tepat waktu"),
    Late(2, "Telat minum"),
    Faster(3, "Lebih cepat minum"),
    Forgot(4, "Lupa minum");

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
