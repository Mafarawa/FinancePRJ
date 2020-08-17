package com.mafarawa.model;

public class AccountModel {
	private String accountName;
	private String accountType;
	public int accountBalance;

	public AccountModel() { this("", "", 0); }

	public AccountModel(AccountModel acc) {
		this(acc.getAccountName(), acc.getAccountType(), acc.getAccountBalance());
	}

	public AccountModel(String accountName, String accountType, int accountBalance) {
		this.accountName = accountName;
		this.accountType = accountType;
		this.accountBalance = accountBalance;
	}

	public String getAccountName() { return this.accountName; }
	public String getAccountType() { return this.accountType; }
	public int getAccountBalance() { return this.accountBalance; }

	public void setAccountName(String accountName) { this.accountName = accountName; }
	public void setAccountType(String accountType) { this.accountType = accountType; }
	public void setAccountBalance(int accountBalance) { this.accountBalance = accountBalance; }

	@Override
	public String toString() {
		return this.accountName;
	}
}