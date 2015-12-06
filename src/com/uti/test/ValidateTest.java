package com.uti.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.nju.util.Validate;

public class ValidateTest {

	@Test
	public void testIsNumber() {
		System.out.println(Validate.isNumber("1"));
		System.out.println(Validate.isNumber("1.0"));
		System.out.println(Validate.isNumber("-1"));
		System.out.println(Validate.isNumber("111.111"));
	}

}
