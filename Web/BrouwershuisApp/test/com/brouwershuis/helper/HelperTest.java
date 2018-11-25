package com.brouwershuis.helper;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class HelperTest {
	
	@Before
    public void setUp() throws Exception {
    }
	
	@Test
    public void test() {
		Date date = new Date();
		
		String formattedDate = Helper.formatDate(date);
		assertTrue("Invalid Formatted Date", formattedDate.length() > 0);
		
		date = null;
		formattedDate = Helper.formatDate(date);
		assertTrue("Invalid Formatted Date", formattedDate == null);
    }
	
}
