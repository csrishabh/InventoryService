package com.mongo.demo.document;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class CaseSearchResult {
	
	@Id
	private String id;
	private Case Case;
	private String bookingDate;
	private String appointmentDate;
	private String deliverdDate;
	private String patientName;
	private String doctorName;
	private String vendorName;
	private String crownDetails;
	private String createdBy;
	private String status;
	private String updateDate;
	private String updateBy;
	private List<CaseStatus> actions  = new ArrayList<CaseStatus>();
	private boolean isEditable = true;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Case getCase() {
		return Case;
	}
	public void setCase(Case case1) {
		Case = case1;
	}
	public String getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getDeliverdDate() {
		return deliverdDate;
	}
	public void setDeliverdDate(String deliverdDate) {
		this.deliverdDate = deliverdDate;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vandorName) {
		this.vendorName = vandorName;
	}
	public String getCrownDetails() {
		return crownDetails;
	}
	public void setCrownDetails(String crownDetails) {
		this.crownDetails = crownDetails;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<CaseStatus> getActions() {
		return actions;
	}
	public void setActions(List<CaseStatus> actions) {
		this.actions = actions;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
