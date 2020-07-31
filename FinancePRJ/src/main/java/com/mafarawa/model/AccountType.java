package com.mafarawa.model;

public enum AccountType {
	CHECKING_ACCOUNT("Расчетный"),
	LOAN_ACCOUNT("Ссудной"),
	FOREING_CURRENCY_ACCOUNT("Валютный"),
	CURRENT_ACCOUNT("Текущий"),
	DEPOSIT_ACCOUNT("Депозитный"),
	PERSONAL_ACCOUNT("Лицевой"),
	CARD_ACCOUNT("Карточный"),
	SPECIAL_ACCOUNT("Специальный");

	private String accountType;

	AccountType(String accountType) { this.accountType = accountType; }

	public String getAccountType() { return this.accountType; }
}