package com.example.sharityadmin;

public class Admin {
    private String adminId;
    private int licenseNumber;
    private String password;

    public Admin() {

    }

    public Admin(String adminId, int licenseNumber, String password) {
        this.adminId = adminId;
        this.licenseNumber = licenseNumber;
        this.password = password;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

