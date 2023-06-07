package com.atm;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import com.atm.AccountRepo;


public class Bank extends Functionality{
	private String bankName = "iNeuron Bank";
	private Random random = new Random();
	Scanner sc = new Scanner(System.in);
	
	public String functionality() {
		System.out.println("Welcome to " + bankName + " Bank");
		System.out.println("Please Choose one of below");
		System.out.println("1 - Create an Account\n"
				+ "2 - Delete an Account\n"
				+ "3 - Deposit Balance\n"
				+ "4 - Withdraw Balance\n"
				+ "5 - Balance Inquiry\n"
				+ "6 - Transaction\n"
				+ "7 - Transaction History\n"
				+ "8 - Exit\n");
		int operation = 0;
		while (operation < 1 || operation > 8) {
            try {
                System.out.print("Please select a valid operation (1-6) : ");
                operation = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number");
                sc.nextLine(); // Clear the input buffer
            }
        }
		
		if( operation == 1) {
			int userId = createAccount();
			return "Your user id is : " + userId+ " \nThis is required while returning to bank\n"
					+ "- - - - - - - - - - - - ACCOUNT CREATED - - - - - - - - - - - -";
		}
		else if (operation == 2) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isDeleted = deleteAccount(Integer.parseInt(response));
				return "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ "+isDeleted+" ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~";
			}
			else return response;
			
		}
		else if (operation == 3) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isDeposited = depositBalance(Integer.parseInt(response));
				return "Update amount " + isDeposited;
			}
			else return response;
		}
		else if (operation == 4) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isWithdrawn = withdrawBalance(Integer.parseInt(response));
				return "Update amount " + isWithdrawn;
			}
			else return response;
		}
		else if (operation == 5) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String balance = checkBalance(Integer.parseInt(response));
				return "Total Balance is : " + balance;
			}
			else return response;
		}
		else if (operation == 6) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isTransact= transaction(Integer.parseInt(response));
				return isTransact;
			}
			else return response;
		}
		else if (operation == 7) {
			String response = login();
			if(Character.isDigit(response.charAt(0))) {
				String isTransact = transactionHistory(Integer.parseInt(response));
				return isTransact;
			}
			else return response;
		}
		else if (operation == 8) {
			return exit();
		}
		else return "Enter Proper Input";
	}

	private int createAccount() {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ CREATING ACCOUNT ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = new AccountHolder();
		user.setId(random.nextInt(Integer.MAX_VALUE - 100000) + 1000000);
		System.out.print("Create Password : ");
		user.setPassword(sc.next());
		sc.nextLine();
		user.setRegestredDate();
		System.out.print("Enter your name : ");
		user.setName(sc.nextLine());
		user.setBalance(0);

		String pin = "";
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.print("Enter 6 digit PIN for ATM use only : ");
				pin = sc.next();
				sc.nextLine();
				int isPinValid = Integer.parseInt(pin);
				if(pin.length() == 6) {
					isValid = true;
				}
			}
			catch (Exception e){
				System.out.println("Please Enter Proper 6 Digit PIN");
			}
		}
		user.setPin(Integer.parseInt(pin));
		user.setBlocked(false);
		
		accountRepo.addAccount(user);
		
		return user.getId();
	}
	
	@Override
	public String login() {
		boolean isAuthenticated = false;
		int userId = 0;
		String password = "";
		int maxAttempts = 3;
        int attempts = 0;
		
		while (attempts < maxAttempts) {
            try {
                System.out.print("Enter user ID : ");
                userId = sc.nextInt();
                sc.nextLine(); // Clear the newline character from the input buffer

                System.out.print("Enter password : ");
                password = sc.nextLine();

                AccountHolder user = accountRepo.findById(userId);
                if (user != null && user.getPassword().equals(password)) {
                    isAuthenticated = true;
                    if(user.isBlocked()) {
                    	System.out.println("Your Account is Blocked\n"
                    			+ "Do You want to UNBLOCK\n"
                    			+ "1 - YES\n"
                    			+ "2 - NO\n");
                    	String input = "";
                    	while (input != "1" || input != "2") {
                    		System.out.print("Enter input : ");
                    		input = sc.nextLine();
                    		if(input.equals("1")) {
                    			user.setBlocked(false);
                    			System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Account Unblocked ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
                    			break;
                    		}
                        	else if (input.equals("2")) {
                        		user.setBlocked(true);
                        		break;
                        	}
                    	}
                    }
                    if(!user.isBlocked()) {
                    	return String.valueOf(userId);
                    }
                    else {
                    	return "~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ Account is Blocked ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~";
                    }
                } else {
                    System.out.println("Incorrect user ID or password. Please try again.");
                    attempts++;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
                sc.nextLine(); // Clear the input buffer
                attempts++;
            }
        }

        if (attempts == maxAttempts) {
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
		transaction.setVia(bankName);
		transaction.setOperation(operation);
		return transaction;
	}
	
	public String depositBalance(int userId) {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ DEPOSIT BALANCE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = accountRepo.findById(userId);
		
		int amountToDeposit = 0;
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.print("Enter amount to Deposit : ");
				amountToDeposit = sc.nextInt();
				if(amountToDeposit > 0) {
					isValid = true;
				}
			}
			catch (InputMismatchException e){
				System.out.println("Please enter proper amount");
				sc.nextLine();
			}
		}
		user.setBalance(user.getBalance() + amountToDeposit);
		deleteAccount(userId);
		
		Transaction transaction = newTransaction("Self", "Self", "Deposit", amountToDeposit);
		List<Transaction> transactionHistory = user.getTransactionHistory();
		transactionHistory.add(transaction);
		user.setTransactionHistory(transactionHistory);
		
		accountRepo.addAccount(user);
		return user.getBalance() + "";
	}
	
	private String exit() {
		return "Thanks For Visiting " + bankName;
	}
}
