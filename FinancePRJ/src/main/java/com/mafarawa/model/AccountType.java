package com.mafarawa.model;

public enum AccountType {
	CHECKING_ACCOUNT(1, "Расчетный"),
	LOAN_ACCOUNT(2, "Ссудной"),
	FOREING_CURRENCY_ACCOUNT(3,"Валютный"),
	CURRENT_ACCOUNT(4, "Текущий"),
	DEPOSIT_ACCOUNT(5, "Депозитный"),
	PERSONAL_ACCOUNT(6,"Лицевой"),
	CARD_ACCOUNT(7, "Карточный"),
	SPECIAL_ACCOUNT(8, "Специальный");

	private int accountId;
	private String accountType;

	AccountType(int accountId, String accountType) {
		this.accountId = accountId;
		this.accountType = accountType;
	}

    public static int getIdByType(String type) {
    	for(AccountType accountType : values()) {
    		if(accountType.accountType == type) return accountType.getAccountId();
    	}

    	return 0;
    }
    
    public static String getTypeById(int id) {
    	for(AccountType accountType : values()) {
    		if(accountType.accountId == id) return accountType.getAccountType();
    	}

    	return "";
    }	

	public int getAccountId() { return this.accountId; }
	public String getAccountType() { return this.accountType; }
}