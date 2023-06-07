package com.atm;

import java.time.LocalDateTime;

public class Transaction {
	private LocalDateTime date_Time;
	private String operation;
	private String via;
	private String sender;
	private String recipient;
	private int amount;
	
	public LocalDateTime getDate_Time() {
		return date_Time;
	}
	public void setDate_Time() {
		this.date_Time = LocalDateTime.now();
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
