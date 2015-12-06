package com.uti.test;

import org.junit.Test;

import com.nju.model.UserInfo;
import com.nju.service.UserInfoService;

public class UserInfoServiceTest {

//	@Test
//	public void testSave() {
//		 UserInfo info = new UserInfo();
//		 info.setName("张小军");
//		 info.setSex("男");
//		 info.setSchool("南京大学");
//		 info.setXueyuan("软件学院");
//		 info.setMajor("软件工程");
//		 info.setDegree("硕士");
//		 info.setStartDate("2014");
//		 new UserInfoService().save(info,"Slf4jMLog$Slf4jMLogge");
//	}
	
	@Test
	public void testUpdate() {
		 new UserInfoService().update(12, "Slf4jMLog$Slf4jMLogge");
	}

}
