package com.atm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountHolder {
	private int id;
	private String password;
	private int pin;
	private String name;
	private LocalDate regestredDate;
	private int balance;
	private boolean blocked;
	private List<Transaction> transactionHistory = new ArrayList<>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getRegestredDate() {
		return regestredDate;
	}
	public void setRegestredDate() {
		this.regestredDate = LocalDate.now();
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public List<Transaction> getTransactionHistory() {
		return transactionHistory;
	}
	public void setTransactionHistory(List<Transaction> transactionHistory) {
		this.transactionHistory = transactionHistory;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}
