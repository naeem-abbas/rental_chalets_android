package com.example.rentalchalets.utils;

public class ReservationInfo {
    private String selectedRoom;
    private String selectedBeautyTreatment;
    private String activityBuffet;
    private String activityMassage;
    private String activitySauna;
    private Boolean isSpecial;
    private Boolean isValidFormModal;

    // Constructor
    public ReservationInfo() {
        // Default constructor
    }

    // Getter and Setter methods for selectedRoom
    public String getSelectedRoom() {
        return selectedRoom;
    }

    public void setSelectedRoom(String selectedRoom) {
        this.selectedRoom = selectedRoom;
    }

    // Getter and Setter methods for selectedBeautyTreatment
    public String getSelectedBeautyTreatment() {
        return selectedBeautyTreatment;
    }

    public void setSelectedBeautyTreatment(String selectedBeautyTreatment) {
        this.selectedBeautyTreatment = selectedBeautyTreatment;
    }

    // Getter and Setter methods for activityBuffet
    public String getActivityBuffet() {
        return activityBuffet;
    }

    public void setActivityBuffet(String activityBuffet) {
        this.activityBuffet = activityBuffet;
    }

    // Getter and Setter methods for activityMassage
    public String getActivityMassage() {
        return activityMassage;
    }

    public void setActivityMassage(String activityMassage) {
        this.activityMassage = activityMassage;
    }

    // Getter and Setter methods for activitySauna
    public String getActivitySauna() {
        return activitySauna;
    }

    public void setActivitySauna(String activitySauna) {
        this.activitySauna = activitySauna;
    }

    // Getter and Setter methods for isSpecial
    public Boolean getSpecial() {
        return isSpecial;
    }

    public void setSpecial(Boolean special) {
        isSpecial = special;
    }

    // Getter and Setter methods for validations
    public Boolean getIsValidFormModal() {
        return isValidFormModal;
    }

    public void setIsValidFormModal(Boolean isValid) {
        isValidFormModal = isValid;
    }
}
