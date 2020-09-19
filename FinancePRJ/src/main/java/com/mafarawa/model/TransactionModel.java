package com.mafarawa.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TransactionModel {
	private SimpleIntegerProperty id;
	private SimpleStringProperty fromPoint;
	private SimpleStringProperty action;
	private SimpleIntegerProperty amount;
	private SimpleStringProperty toPoint;
	private SimpleStringProperty date;

	public TransactionModel(int id, String fromPoint, String action, int amount, String toPoint, String date) {
		this.id = new SimpleIntegerProperty(id);
		this.fromPoint = new SimpleStringProperty(fromPoint);
		this.action = new SimpleStringProperty(action);
		this.amount = new SimpleIntegerProperty(amount);
		this.toPoint = new SimpleStringProperty(toPoint);
		this.date = new SimpleStringProperty(date);
	}

	public void setId(int id) { this.id.set(id); };
	public void setFromPoint(String fromPoint) { this.fromPoint.set(fromPoint); }
	public void setAction(String action) { this.action.set(action); }
	public void setAmount(Integer amount) { this.amount.set(amount); }
	public void setToPoint(String toPoint) { this.toPoint.set(toPoint); }
	public void setDate(String date) { this.date.set(date); }

	public int getId() { return this.id.get(); };
	public String getFromPoint() { return this.fromPoint.get(); }
	public String getAction() { return this.action.get(); }
	public int getAmount() { return this.amount.get(); }
	public String getToPoint() { return this.toPoint.get(); }
	public String getDate() { return this.date.get(); }
}