package oops;

public class BankAccount {
//	Static field: bankName, totalAccounts (tracks count)
//	Instance fields: accountNumber, holderName, balance
//	Static method: getBankInfo() returns bank name and total accounts
//	Instance methods: deposit(), withdraw(), getBalance()
//	Auto-generate account numbers using static counter

	// static fields
	static String bankName = "HDFC";
	static int totalAccounts = 0;
	static int accountCounter = 1000; // for auto account numbers

	// instance fields
	private int accountNumber;
	private String holderName;
	private double balance;

	// Constructor
	public BankAccount(String holderName, double initialBalance) {
		this.holderName = holderName;
		this.balance = initialBalance;
		this.accountNumber = ++accountCounter;
		totalAccounts++;
	}

	static public String getBankInfo() {
		return bankName + " - Total Accounts: " + totalAccounts;

	}

	private void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		}
	}

	private void withdraw(double amount) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
		}
	}

	private double getBalance() {
		return balance;

	}

	public static void main(String[] args) {
		BankAccount acc1 = new BankAccount("Alice", 1000);
		BankAccount acc2 = new BankAccount("Bob", 500);
		System.out.println(BankAccount.getBankInfo());
		System.out.println("Alice Balance: " + acc1.getBalance());
		System.out.println("Bob Balance: " + acc2.getBalance());
	}

}
