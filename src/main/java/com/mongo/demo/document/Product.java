package com.mongo.demo.document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;
	
	@Transient
	private double alert;
	
	private long alertBack;

	private Unit unit;

	@Transient
	private double lastPrice;
	
	private long lastPriceBack;

	@Transient
	private double qtyAbl;
	
	private long qtyAblBack;

	private String lstAddBy;

	private Date lstAddDate;

	private String lstAdtBy;

	private Date lstAdtDate;
	
	private List<Carted> carted;
	
	private List<Assigned> assigned;

	public Product() {
		
	}
	public Product(String name, double alert, Unit unit) {

		this.name = name;
		this.alert = alert;
		this.unit = unit;
	}

	public String getLstAddBy() {
		return lstAddBy;
	}

	public void setLstAddBy(String lstAddBy) {
		this.lstAddBy = lstAddBy;
	}

	public Date getLstAddDate() {
		return lstAddDate;
	}

	public void setLstAddDate(Date lstAddDate) {
		this.lstAddDate = lstAddDate;
	}

	public String getLstAdtBy() {
		return lstAdtBy;
	}

	public void setLstAdtBy(String lstAdtBy) {
		this.lstAdtBy = lstAdtBy;
	}

	public Date getLstAdtDate() {
		return lstAdtDate;
	}

	public void setLstAdtDate(Date lstAdtDate) {
		this.lstAdtDate = lstAdtDate;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAlert() {
		return alert;
	}

	public void setAlert(double alert) {
		this.alert = alert;
	}

	public double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public double getQtyAbl() {
		return qtyAbl;
	}

	public void setQtyAbl(double qtyAbl) {
		this.qtyAbl = qtyAbl;
	}
	public long getAlertBack() {
		return alertBack;
	}
	public void setAlertBack(long alertBack) {
		this.alertBack = alertBack;
	}
	public long getLastPriceBack() {
		return lastPriceBack;
	}
	public void setLastPriceBack(long lastPriceBack) {
		this.lastPriceBack = lastPriceBack;
	}
	public long getQtyAblBack() {
		return qtyAblBack;
	}
	public void setQtyAblBack(long qtyAblBack) {
		this.qtyAblBack = qtyAblBack;
	}
	public List<Carted> getCarted() {
		return carted;
	}
	public void setCarted(List<Carted> carted) {
		this.carted = carted;
	}
	public List<Assigned> getAssigned() {
		return assigned;
	}
	public void setAssigned(List<Assigned> assigned) {
		this.assigned = assigned;
	}	
}
