package com.dawn.restroview.model;

public class AddBusiness {

    private String businessID;
    private String businessName;
    private String businessRegistrationCode;
    private String businessAddress;

    public AddBusiness(){}

    public AddBusiness(String businessName) {
        this.businessName = businessName;
    }

    public AddBusiness(String businessID, String businessName, String businessRegistrationCode, String businessAddress) {
        this.businessID = businessID;
        this.businessName = businessName;
        this.businessRegistrationCode = businessRegistrationCode;
        this.businessAddress = businessAddress;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessRegistrationCode() {
        return businessRegistrationCode;
    }

    public void setBusinessRegistrationCode(String businessRegistrationCode) {
        this.businessRegistrationCode = businessRegistrationCode;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
}
