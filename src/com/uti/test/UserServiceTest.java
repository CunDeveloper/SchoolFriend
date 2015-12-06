package com.uti.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nju.model.User;
import com.nju.service.UserService;

public class UserServiceTest {

	private UserService userService = new UserService();
	@Test
	public void testSave() {
		User user = new User();
		user.setUsername("16372697902@qq.com");
		user.setPassword("1234");
		user.setSign("HHH");
		System.out.println(userService.save(user));
	}

	@Test
	public void testQueryUser() {
		User user = userService.query("16372697902@qq.com","1234");
		user.setPassword("12345");
		user.setSign("你就是个神经病dfsfsad");
		System.out.println(userService.updateUser(user));
		System.out.println(user.toString());
	}

}
