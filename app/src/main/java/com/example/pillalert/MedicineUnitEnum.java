package com.example.pillalert;

public enum MedicineUnitEnum {
    mL(0, "mL"),
    Tablet(1, "Tablet"),
    Capsule(2, "Capsule"),
    Sachet(3, "Sachet"),
    Spoon(4, "Sendok"),
    TeaSpoon(5, "Sendok teh");

    private final int value;
    private final String englishTranslation;

    MedicineUnitEnum(int value, String englishTranslation) {
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
    public static MedicineUnitEnum fromValue(int value) {
        for (MedicineUnitEnum status : MedicineUnitEnum.values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}
