package com.atm;

import java.util.ArrayList;
import java.util.List;

public class AccountRepo {
	private static List<AccountHolder> accountHolder = new ArrayList<>();
	
	public void addAccount (AccountHolder user) {
		accountHolder.add(user);
	}
	
	public AccountHolder findById(int id) {
		for (int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				return accountHolder.get(i);
			}
		}
		return null;
	}
	
	public boolean deleteById(int id) {
		for (int i = 0; i < accountHolder.size(); i++) {
			if(accountHolder.get(i).getId() == id) {
				accountHolder.remove(i);
				return true;
			}
		}
		return false;
	}
}
