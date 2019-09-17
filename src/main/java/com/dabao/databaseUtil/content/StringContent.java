package com.dabao.databaseUtil.content;

import com.dabao.databaseUtil.util.FileUtil;

/**
 * 替换的模板
 * @author WRF
 *
 */
public class StringContent {

	

	public static String controller = FileUtil.getContent("controller");
	
	public static String service = FileUtil.getContent("service");
	
	public static String mapper = FileUtil.getContent("mapper2");
	
	public static String dao = FileUtil.getContent("dao");
	
	public static  String daoNote = FileUtil.getContent("daoNote");
	
	public static  String daoNoteEntity = FileUtil.getContent("daoNoteEntity");
	
	public static String entity = FileUtil.getContent("entity2");
	
	public static String doc = FileUtil.getContent("doc");
	
	public static String mapperNote = FileUtil.getContent("mapperNote");
	
	public static String mapperNoteEntity = FileUtil.getContent("mapperNoteEntity");
	
	public static void main(String[] args) {
		System.out.println(StringContent.mapper);
	}
	
}
