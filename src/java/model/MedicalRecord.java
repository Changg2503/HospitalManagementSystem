/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
/**
 *
 * @author Nguyen Trang
 */

public class MedicalRecord {
    private int recordID;
    private int patientID;
    private int doctorID;
    private String diagnosis;
    private String treatment;
    private Timestamp createdDate;

    public MedicalRecord() {
    }

    public MedicalRecord(int recordID, int patientID, int doctorID, String diagnosis, String treatment, Timestamp createdDate) {
        this.recordID = recordID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.createdDate = createdDate;
    }

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "MedicalRecord{" + "recordID=" + recordID + ", patientID=" + patientID + ", doctorID=" + doctorID + ", diagnosis=" + diagnosis + ", treatment=" + treatment + ", createdDate=" + createdDate + '}';
    }
    
    
}
