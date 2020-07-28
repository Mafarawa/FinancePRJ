package com.mafarawa.model;

import org.junit.Assert;
import org.junit.Test;

public class UserModelTests {
	@Test
	public void testCreateShukherCode() {
		UserModel user1 = new UserModel("Поц", "mafarawa5@gmail.com", "asd", UserImage.WHITE_USER.getImage());

		long expected = UserModel.createShukherCode(user1.getName(), user1.getEmail(), user1.getPassword());
		long actual = user1.getShukherCode();

		Assert.assertEquals(expected, actual);
	}
}