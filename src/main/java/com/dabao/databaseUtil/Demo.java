package com.dabao.databaseUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dabao.databaseUtil.entity.DataConnect;
import com.dabao.databaseUtil.entity.Project;

public class Demo {

	public static void main(String[] args) {
		//数据库配置
		DataConnect.setDRIVER("com.mysql.cj.jdbc.Driver");
new Date();
		
//		DataConnect.setURL("jdbc:mysql://127.0.0.1:3306/dataSource1?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true&allowMultiQueries=true");
//		DataConnect.setUSERNAME("root");
//		DataConnect.setPASSWORD("123456");
		

		//需要文件相关配置
		Project.setController(true);
		
		Project.setService(true);
		Project.setDoc(true);
		Project.setEntity(true);
		Project.setDao(true);
		Project.setMybatis(true);
		
		//是否采用下划线转驼峰式
		Project.setTransforFiled(false);
		
	    //是否为注解开发
//		Project.setNoteDevelop(true);
		//参数为实体类
//		Project.setEntityDevelop(true);
		
		//创建表的设置
		String	url = "E://数据";
		
		String	pakegeName = "com.fineone.agriculturePc";
//		String	pakegeName = "com.fineone.Pasteup";
		Map<String, String> tableMap = new HashMap<String, String>();
		//表名（key）类名（value）
    	tableMap.put("ylf_source_data_gather", "SourceDataGather"+","+"=编码表");
    	
		Create createStart = new Create();
		
		createStart.start(url, pakegeName, tableMap);
		
	}
	
	
	

	
}
