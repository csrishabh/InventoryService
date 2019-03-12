package com.mongo.demo.document;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private double quantity;

	private TransctionType type;

	private String productId;

	private double amount;

	private Date date;

	private String addBy;

	private String adtBy;

	private Date adtDate;

	private boolean isAdtable;

	private boolean isAdtDone;

	public Transction(String productId, double quantity, TransctionType type, Date date, double amount) {

		this.productId = productId;
		this.quantity = quantity;
		this.type = type;
		this.date = date;
		this.amount = amount;
	}

	public Transction() {

	}

	public String getAddBy() {
		return addBy;
	}

	public void setAddBy(String addBy) {
		this.addBy = addBy;
	}

	public String getAdtBy() {
		return adtBy;
	}

	public void setAdtBy(String adtBy) {
		this.adtBy = adtBy;
	}

	public Date getAdtDate() {
		return adtDate;
	}

	public void setAdtDate(Date adtDate) {
		this.adtDate = adtDate;
	}

	public boolean isAdtable() {
		return isAdtable;
	}

	public void setAdtable(boolean isAdtable) {
		this.isAdtable = isAdtable;
	}

	public boolean isAdtDone() {
		return isAdtDone;
	}

	public void setAdtDone(boolean isAdtDone) {
		this.isAdtDone = isAdtDone;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public TransctionType getType() {
		return type;
	}

	public void setType(TransctionType type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
