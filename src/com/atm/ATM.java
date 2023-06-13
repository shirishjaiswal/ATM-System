package com.atm;

import java.util.InputMismatchException;

public class ATM extends Functionality{
	private String atmName = "iNeuron ATM";

	public String functionality() {
		System.out.println("Welcome to " + atmName);
		System.out.println("Please Choose one of below");
		System.out.println("1 - Withdraw Balance\n"
				+ "2 - Balance Inquiry\n"
				+ "3 - Make Transaction\n"
				+ "4 - Exit\n");
		int operation = 0;
		while (operation < 1 || operation > 4) {
            try {
                System.out.print("Please select a valid operation (1-4) : ");
                operation = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                sc.nextLine(); // Clear the input buffer
            }
        }
		
		if( operation == 1) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isWithdrawn = withdrawBalance(Integer.parseInt(response));
				return "Update amount " + isWithdrawn;
			}
			else return response;
		}
		else if (operation == 2) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String balance = checkBalance(Integer.parseInt(response));
				return "Total Balance is : " + balance;
			}
			else return response;
		}
		else if (operation == 3) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isTransact= transaction(Integer.parseInt(response));
				return isTransact;
			}
			else return response;
		}
		else if (operation == 4) {
			exit();
		}
		return "To be added";
	}
	private void exit() {
		System.out.println("Thanks For Visiting " + atmName);
	}
	
	@Override
	public String login() {
		boolean isAuthenticated = false;
		int userId = 0;
		int pin = 0;
		int maxAttempts = 3;
        int attempts = 0;
        AccountHolder user;
		while (attempts < maxAttempts) {
            try {
                System.out.print("Enter user ID : ");
                userId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter 6 Digit PIN : ");
                pin = sc.nextInt();

                user = accountRepo.findById(userId);
                if (user != null && user.getPin() == pin) {
                    isAuthenticated = true;
                    if(user.isBlocked()) {
                    	return "User Account is Blocked Due to Mulitple Wrong attemp to access\n"
                    			+ "Visit Bank to Unblock";
                    }
                    return String.valueOf(userId);
                } else {
                    System.out.println("Incorrect user ID or PIN. Please try again.");
                    attempts++;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine(); // Clear the input buffer
                attempts++;
            }
        }

        if (attempts == maxAttempts) {
        	user = accountRepo.findById(userId);
        	if(user != null) {
        		user.setBlocked(true);
            	accountRepo.deleteById(userId);
            	accountRepo.addAccount(user);
        	}
        	return "Maximum number of attempts reached. Access denied.";
        }
		return null;
	}

	@Override
	protected Transaction newTransaction(String recipient, String sender, String operation, int amount) {
		Transaction transaction = new Transaction();
		transaction.setRecipient(recipient);
		transaction.setSender(sender);
		transaction.setAmount(amount);
		transaction.setDate_Time();
		transaction.setVia(atmName);
		transaction.setOperation(operation);
		return transaction;
	}
}
