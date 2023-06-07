package com.atm;

import java.util.ArrayList;
import java.util.List;

public class AccountRepo {
	private static List<AccountHolder> accounts = new ArrayList<>();
	
	public void addAccount (AccountHolder user) {
		accounts.add(user);
	}
	
	public AccountHolder findById(int id) {
		for (int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getId() == id) {
				return accounts.get(i);
			}
		}
		return null;
	}
	
	public boolean deleteById(int id) {
		for (int i = 0; i < accounts.size(); i++) {
			if(accounts.get(i).getId() == id) {
				accounts.remove(i);
				return true;
			}
		}
		return false;
	}
}
