package com.dabao.databaseUtil.entity;

import lombok.Data;

public class Project {

	private static boolean controller = false;
	private static boolean service = false;
	private static boolean entity = false;
	private static boolean dao = false;
	private static boolean mybatis = false;
	private static boolean doc = false;
	
	//实体类开发
	private static boolean isEntityDevelop = false;
	
	//是否使用注解开发
	private static boolean noteDevelop  = false;
	
	//是否使用驼峰式命名转化
	private static boolean transforFiled  = true;
	
	
	public static boolean getController() {
		return controller;
	}
	public static void setController(boolean controller) {
		Project.controller = controller;
	}
	public static boolean getService() {
		return service;
	}
	public static void setService(boolean service) {
		Project.service = service;
	}
	public static boolean getEntity() {
		return entity;
	}
	public static void setEntity(boolean entity) {
		Project.entity = entity;
	}
	public static boolean getDao() {
		return dao;
	}
	public static void setDao(boolean dao) {
		Project.dao = dao;
	}
	public static boolean getMybatis() {
		return mybatis;
	}
	public static void setMybatis(boolean mybatis) {
		Project.mybatis = mybatis;
	}
	public static boolean getDoc() {
		return doc;
	}
	public static void setDoc(boolean doc) {
		Project.doc = doc;
	}
	public static boolean getNoteDevelop() {
		return noteDevelop;
	}
	public static void setNoteDevelop(boolean noteDevelop) {
		Project.noteDevelop = noteDevelop;
	}
	public static boolean isEntityDevelop() {
		return isEntityDevelop;
	}
	public static void setEntityDevelop(boolean isEntityDevelop) {
		Project.isEntityDevelop = isEntityDevelop;
	}
	public static boolean isTransforFiled() {
		return transforFiled;
	}
	public static void setTransforFiled(boolean transforFiled) {
		Project.transforFiled = transforFiled;
	}
	
	
}
