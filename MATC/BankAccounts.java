/**
 * This program creates a superclass and two subclasses for account, savingsAccount, and
 * 	checkingAccount respectively, as well as implementing a few methods (withdraw and deposit)
 * @author Steven Wiener
 * @Version 1
 */

public class BankAccounts {
	public static void main(String[] args) {
		// Creates account, savingsAccount, and checkingAccount
		Account account = new Account(0, 0, 1.5);
		SavingsAccount savingsAccount = new SavingsAccount(1, 0.0, 4.5);
		CheckingAccount checkingAccount = new CheckingAccount(2, 0.0, 5.5, 2000);
		
		// Prints accounts to console
		System.out.println(account.toString() + "\n" + savingsAccount.toString() + "\n" + checkingAccount.toString());
		
		// Prints balance before deposit
		System.out.println("Account balance before deposit: " + account.getBalance());
		
		// Deposits $200
		account.deposit(200.00);
		
		// Prints balance after deposit and before withdrawal
		System.out.println("Account balance after deposit and before withdrawal: " + account.getBalance());
		
		// Withdraw $150
		account.withdraw(150.00);
		
		// Prints balance after withdrawal
		System.out.println("Account balance after withdrawal: " + account.getBalance());
	}
}

class Account	{
	// Creates properties for Account(s)
	private int accountNumber;
	private double balance;
	private double annualInterestRate;
	private java.util.Date dateCreated;
	
	// Default constructor
	Account(int accountNumber, double balance, double annualInterestRate)	{
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.annualInterestRate = annualInterestRate;
		dateCreated = new java.util.Date();
	}
	
	// get methods for properties
	public int getAccountNumber()	{
		return accountNumber;
	}

	public double getBalance()	{
		return balance;
	}
	
	public double getAnnualInterestRate()	{
		return annualInterestRate;
	}
	
	public java.util.Date getDateCreated()	{
		return dateCreated;
	}
	
	// Method for depositing money
	public void deposit(double amount)	{
		balance += amount;
	}
	
	// Method for withdrawing money
	public void withdraw(double amount)	{
		balance -= amount;
	}
}

// SavingsAccount class extending Account class
class SavingsAccount extends Account	{
	// Doesn't require a limit		
	SavingsAccount(int accountNumber, double balance, double annualInterestRate)	{
		super(accountNumber, balance, annualInterestRate);
	}
}

// CheckingAccount class extending Account class
class CheckingAccount extends Account	{
	double overdraftLimit;
	// Requires a limit for creation
	CheckingAccount(int accountNumber, double balance, double annualInterestRate, double overdraftLimit)	{
		super(accountNumber, balance, annualInterestRate);
		this.overdraftLimit = overdraftLimit;
	}
}