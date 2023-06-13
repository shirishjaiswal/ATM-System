package com.atm;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BankATMSystemDriver {
	public static void main (String [] args) {
		Scanner sc = new Scanner (System.in);
		Bank bank = new Bank();
		ATM atm = new ATM();
		int bankOrAtm = 0;
		while (bankOrAtm != 3) {
			StringBuilder display = new StringBuilder();
			display.append("~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ WELCOME ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ ~")
					.append("\n1 - Bank")
					.append("\n2 - ATM")
					.append("\n3 - Exit")
					.append("\nYou want to visit : ");

			System.out.print(display);

			while (bankOrAtm < 1 || bankOrAtm > 3) {
	            try {
	                bankOrAtm = sc.nextInt();
	                if (bankOrAtm < 1 || bankOrAtm > 3) {
	                    System.out.print("Enter valid input : ");
	                }
	            } catch (InputMismatchException e) {
	                System.out.print("Enter valid input : ");
	                sc.nextLine(); // Clear the input buffer
	            }
	        }
			System.out.println();
			if (bankOrAtm == 1) {
				String response = bank.functionality();
				System.out.println(response);
			}
			else if (bankOrAtm == 2) {
				String response = atm.functionality();
				System.out.println(response);
			}
			else if (bankOrAtm == 3) {
				System.out.println("Thanks  Visiting");
				break;
			}
			System.out.println();
			bankOrAtm = 0;
		}
	}
}
