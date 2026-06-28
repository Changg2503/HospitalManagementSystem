/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author PC
 */
public class Doctor {
    private int doctorID;
    private String fullName;
    private String specialization;
    private String phone;
    private int departmentID;

    public Doctor() {
    }

    public Doctor(int doctorID, String fullName, String specialization, String phone, int departmentID) {
        this.doctorID = doctorID;
        this.fullName = fullName;
        this.specialization = specialization;
        this.phone = phone;
        this.departmentID = departmentID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public String toString() {
        return "Doctor{" + "doctorID=" + doctorID + ", fullName=" + fullName + ", specialization=" + specialization + ", phone=" + phone + ", departmentID=" + departmentID + '}';
    }
    
    
    
}
