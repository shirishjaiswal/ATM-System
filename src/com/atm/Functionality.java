package com.atm;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class Functionality {
	Scanner sc = new Scanner(System.in);
	protected AccountRepo accountRepo = new AccountRepo();
	
	public  abstract String login();
	
	protected abstract Transaction newTransaction(String recipient, String sender, String operation, int amount);

	public String withdrawBalance(int userId) {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ WITHDRAW BALANCE ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = accountRepo.findById(userId);
		
		int amountToWithdraw = 0;
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.print("Enter amount to Withdraw : ");
				amountToWithdraw = sc.nextInt();
				if(amountToWithdraw > 0) {
					isValid = true;
				}
			}
			catch (InputMismatchException e){
				System.out.print("Please enter proper amount");
				sc.nextLine();
			}
		}
		if(user.getBalance() < amountToWithdraw) {
			return "InSufficient Balance";
		}
		user.setBalance(user.getBalance() - amountToWithdraw);
		deleteAccount(userId);
		
		Transaction transaction = newTransaction("Self", "Self", Operation.WITHDRAW.toString(), amountToWithdraw);
		List<Transaction> transactionHistory = user.getTransactionHistory();
		transactionHistory.add(transaction);
		user.setTransactionHistory(transactionHistory);
		
		accountRepo.addAccount(user);
		return user.getBalance() + "";
	}
	
	public String transaction(int userId) {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ MAKE TRANSACTION ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = accountRepo.findById(userId);
		int recipientId = 0;
		boolean isValid = false;
		while (!isValid) {
			try {
				System.out.print("Enter Recipient Id : ");
				recipientId = sc.nextInt();
				isValid = true;
			}
			catch (InputMismatchException e){
				System.out.print("Please enter correct Recipient Id ");
				sc.nextLine();
			}
		}
		AccountHolder recipient = accountRepo.findById(recipientId);
		if(recipient == null) {
			return "Recipient Id Not Found";
		}
		int amountToTransact = 0;
		isValid = false;
		while (!isValid) {
			try {
				System.out.print("Enter amount to Transact : ");
				amountToTransact = sc.nextInt();
				if(amountToTransact > 0) {
					isValid = true;
				}
			}
			catch (InputMismatchException e){
				System.out.print("Please enter proper amount");
				sc.nextLine();
			}
		}
		if(user.getBalance() < amountToTransact) {
			return "Insufficient Ammount";
		}
		user.setBalance(user.getBalance() - amountToTransact);
		recipient.setBalance(recipient.getBalance() + amountToTransact);
		accountRepo.deleteById(userId);
		accountRepo.deleteById(recipientId);
		
		Transaction senderTransaction = newTransaction(String.valueOf(recipient.getId()), "Self", Operation.TRANSACT.toString(), amountToTransact);
		List<Transaction> senderTransactionHistory = user.getTransactionHistory();
		senderTransactionHistory.add(senderTransaction);
		user.setTransactionHistory(senderTransactionHistory);
		
		Transaction recipientTransaction = newTransaction("Self", String.valueOf(user.getId()), Operation.DEPOSIT.toString(), amountToTransact);
		List<Transaction> recipientTransactionHistory = recipient.getTransactionHistory();
		recipientTransactionHistory.add(recipientTransaction);
		recipient.setTransactionHistory(recipientTransactionHistory);
	
		accountRepo.addAccount(user);
		accountRepo.addAccount(recipient);
		
		return "Transaction Successful"
				+ "Current Balance is :" + user.getBalance();
	}
	
	public String transactionHistory (int userId) {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ TRANSACTION  HISTORY ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = accountRepo.findById(userId);
		List<Transaction> transactionHistory = user.getTransactionHistory();
		if(transactionHistory.isEmpty()) {
			return "============== NO DATA AVAILABLE ==============";
		}
		for (Transaction statement : transactionHistory) {
			StringBuilder statementDetails = new StringBuilder();
			statementDetails.append("\n____________________ ")
				.append(statement.getOperation())
				.append(" ____________________")
				.append("\nProcessed Through : ")
				.append(statement.getVia())
				.append("\nDate and Time : ")
				.append(statement.getDate_Time())
				.append("\nSent By : ")
				.append(statement.getSender())
				.append("\nRecived By : ")
				.append(statement.getRecipient())
				.append("\nAmount Transacted : ")
				.append(statement.getAmount());

			System.out.println(statementDetails);
		}
		return "\n=========== END ===========";
	}
	
	public String checkBalance(int userId) {
		System.out.println("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~  CHECK  BALANCE  ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~");
		AccountHolder user = accountRepo.findById(userId);
		return user.getBalance() + "";
	}
	
	public String deleteAccount(int userId) {
        boolean isDeleted = accountRepo.deleteById(userId);
        if(isDeleted) {
        	return "Account Deleted";
        }
        else return "Account Not Deleted";
	}
}
