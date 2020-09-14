package com.mafarawa.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionModel {
	private SimpleStringProperty fromPoint;
	private SimpleStringProperty action;
	private SimpleIntegerProperty amount;
	private SimpleStringProperty toPoint;
	private SimpleStringProperty date;

	public TransactionModel(String fromPoint, String action, int amount, String toPoint, String date) {
		this.fromPoint = new SimpleStringProperty(fromPoint);
		this.action = new SimpleStringProperty(action);
		this.amount = new SimpleIntegerProperty(amount);
		this.toPoint = new SimpleStringProperty(toPoint);
		this.date = new SimpleStringProperty(date);
	}

	public void setFromPoint(String fromPoint) { this.fromPoint.set(fromPoint); }
	public void setAction(String action) { this.action.set(action); }
	public void setAmount(Integer amount) { this.amount.set(amount); }
	public void setToPoint(String toPoint) { this.toPoint.set(toPoint); }
	public void setDate(String date) { this.date.set(date); }

	public String getFromPoint() { return this.fromPoint.get(); }
	public String getAction() { return this.action.get(); }
	public Integer getAmount() { return this.amount.get(); }
	public String getToPoint() { return this.toPoint.get(); }
	public String getDate() { return this.date.get(); }
}