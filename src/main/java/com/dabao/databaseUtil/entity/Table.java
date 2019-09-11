package com.dabao.databaseUtil.entity;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data
public class Table {

	/**
	 * ===> 表的基础信息
	 */

	String author = "WRF";// 作者

	String tableName;// 表名

	String className;// 类名（首字母大写）

	String classMinName;// 类名（首字母小写）

	String TableChinessName;// 中文表名

	int fieldMostLength;// 字段的最长值

	String pakageName;// 包名（com.baidu.demo）

	/**
	 * ===> 路径(用于创建文件或文件夹处理的信息)
	 * 
	 */
	String classUrl;// 路径名（./com/baidu/demo）

	String controllerUrl;// controller文件路径

	String serviceUrl;// service文件路径

	String daoUrl;// mapper文件路径（dao）
	
//	String mapperUrl;// mapper文件路径（dao）
//	String daoNoteUrl;// mapper文件路径（dao）
	
	String mapperProviderUrl;// mapper注解文件路径（Provider）

	String mybatisUrl;// mybatis文件路径（xml）

	String entityUrl;// 实体类文件路径（xml）

	// 创建的文件名
	String controllerFlieName;

	String serviceFlieName;

	String daoFlieName;
	
//	String daoNoteFlieName;

	String entityFlieName;

	String mybatisFlieName;

	String docFlieName;

	String mapperProviderFlieName;

	/**
	 * ===> 字段信息
	 */

	List<String> fieldNameList;// 字段集合

	List<String> fieldTypeList;// 字段类型集合

	List<String> fieldCommentsList;// 字段含义（注释）集合

	String keyName;// 主键

	String delName;// 假删操作键

	/**
	 * ===> 字段处理信息
	 */

	// mybatis 替换模板

//	String Base_Column_List;// 查询条件

	String fieldStrings = "";// 字段列表

	String fieldStringsa = "";// 字段列表（前面带别名 a）

	String insertCondition = "";// 新增字段列表

	String insertValueCondition = "";// 新增字段值列表

	String selectCondition = "";// baseQueryXXX

	String selectCondition_a = "";// baseQueryAXXX

	String updateCondition = "";// updateXXX

	/**
	 * 替换的模板
	 */

	String insert = "\t\t\t`fieldXXX` ," + "\r\n";

	String insertValue = "\t\t\t#{ fieldXXX },\r\n";

	String insertSingle = "			fieldXXX\r\n";

	String insertSingleValue = "			#{fieldXXX},\r\n";

	String insertList = "			#{item.fieldXXX},\r\n";

	String select = "\n\t\t\t<if test=\"fieldXXX != null and fieldXXX != '' \" >\n"
			+ "\t\t\t\tAND\t`fieldXXX` = #{ fieldXXX }\n" + "\t\t\t</if>";

	String select_a = "\n\t\t\t<if test=\"fieldXXX != null and fieldXXX != '' \" >\n"
			+ "\t\t\t\tAND\t`a`.`fieldXXX` = #{ fieldXXX }\n" + "\t\t\t</if>";

	String update = "			<if test=\"fieldXXX != null and fieldXXX != '' \" >" + " `fieldXXX` = #{ fieldXXX },"
			+ " </if>\r\n";

	/**
	 * 实体类的相关字符串
	 */
	// get、set方法的模板
	String setAndGet = "\r\n\tpublic TypyXXX getFieldUpXXX() {\n" + "\t\treturn FieldXXX;\n" + "\t}\n"
			+ "\tpublic void setFieldUpXXX(TypyXXX FieldXXX) {\n" + "\t\tthis.FieldXXX = FieldXXX;\n" + "\t}\r\n";

	String setAndGetText = "";
	// 实体类中字段变量
	String privateFieldText = "";

	/**
	 * 文档中的相关字符串
	 */
	// 文档相关参数
	String docTitle = "\t\t\t\t字段\t\t\t\t类型\t\t\t\t备注\t\t\t\r\n";
	String docDemo = "\t\t\ttypeXXX\tfieldsXXX; //commentsXXX\t\t\r\n";
	String doc = "";
	String doc_doc = "";
	
	public Table () {
		
	}
	

	public Table(String tableName, String className, String pakageName, String TableChinessName) {
		this.pakageName = pakageName;
		this.classUrl = this.pakageName.replace(".", File.separator);// 路径
		// 文件路径
		this.controllerUrl = classUrl + File.separator + "controller";// controller文件路径

		this.serviceUrl = classUrl + File.separator + "service";// service文件路径

		this.daoUrl = classUrl + File.separator + "mapper";// mapper文件路径（dao）
		
		this.mapperProviderUrl = classUrl + File.separator + "mapper"+ File.separator+"provider";// mapper文件路径（dao）

		this.mybatisUrl = "mybatis";// mybatis文件路径（xml）

		this.entityUrl = classUrl + File.separator + "entity";// mapper文件路径（dao）

		this.controllerFlieName = className + "Controller.java";

		this.serviceFlieName = className + "Service.java";

		this.daoFlieName = className + "Mapper.java";

		this.entityFlieName = className + ".java";

		this.mybatisFlieName = className + "Mapper.xml";

		this.docFlieName = TableChinessName + "-" + className + ".txt";
		
		this.mapperProviderFlieName = className+"Provider" + ".java";

		this.tableName = tableName;
		this.className = className;
		this.TableChinessName = TableChinessName;
		this.classMinName = toLowerCaseFirstOne(className);
		this.fieldNameList = DataConnect.getColumnNames(tableName);// 字段名
		this.fieldTypeList = DataConnect.getColumnTypes(tableName);// 字段类型
		this.fieldCommentsList = DataConnect.getColumnComments(tableName);// 注释
		// 主键
		this.keyName = DataConnect.getPrimaryKey(tableName)[0];// 如果有多个主键请将主键排序，默认去第一个主键
		// 假删建
		for (int i = 0; i < fieldCommentsList.size(); i++) {
			String comment = fieldCommentsList.get(i);
			// 如果注解中包含这个字符串，则为假删的标志
			if (comment.contains("del")) {
				this.delName = fieldNameList.get(i);
			}
		}
		// Mapper文件中的字段部分字符串拼接
		onloadContent();
		// 文档
		onloadDoc();
		// 实体类的get、set方法 ， 目前可以用注解@Data代替
		onSetAndGet();
	}

	String conditionMybatisA = "";
	
	
	String isNotEmptyMethod  = "";

	/**
	 * mybatis注解开发
	 */
	public String mybatisAnnection() {

//		String selectTitle = 
//				"/t/tSELECT(\"*\");\r\n" + 
//				"/t/tFROM(\"tablenameXXX\");\r\n";
//		
//		String keyCondition = "/t/tWHERE(\"keyXXX = #{ keyXXX }\");\r\n";

		String template = "\t\t\tif (isNotEmpty(param.get(\"fieldXXX\"))) {"
				+ "\tWHERE(\"fieldXXX = #{ fieldXXX }\");\t}\r\n";
		
//		String template = "\t\tif (param.get(\"fieldXXX\") != null && !param.get(\"fieldXXX\").equals(\"\")) {\r\n"
//				+ "\t\t    WHERE(\"fieldXXX = #{ fieldXXX }\");\r\n" + "\t\t}\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}

//		selectTitle = selectTitle.replace("tablenameXXX", "tableName");
//		
//		if (iskey) {
//			selectTitle += keyCondition.replace("keyXXX", keyName);
//		}

		return resultMybatis;
	}

	public String insertNote() {

		String insertTitle = "\t\t\tINSERT_INTO(\"tablenameXXX\");\r\n";
		String template = "\t\t\tVALUES(\"`fieldXXX`\", \"#{ fieldXXX }\");\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}
		insertTitle = insertTitle.replace("tablenameXXX", tableName);
		return insertTitle + resultMybatis;
	}

	public String insertListNote() {
		String aString = " sb.append(\"fileNameThreeXXX\");";

		StringBuffer stringBuffer = new StringBuffer();

		String fileNameThree = "";
		for (int i = 0, j = fieldNameList.size(); i < j; i++) {

			String fieldName = fieldNameList.get(i).trim();
			fileNameThree += "`" + fieldName + "`";
			if (i != j - 1) {
				fileNameThree += " , " + disposeFieldTwo("`" + fieldName + "`", 2);
				
				if ((i + 1) % 3 == 0) {
					String replace = aString.replace("fileNameThreeXXX", fileNameThree);

					stringBuffer.append("\t\t\t");
					stringBuffer.append(replace);
					stringBuffer.append("\r\n");
					fileNameThree = "";
				}
			} 
			else {// 处理最后1、2个
				String replace = aString.replace("fileNameThreeXXX", fileNameThree);
				stringBuffer.append("\t\t\t");
				stringBuffer.append(replace);
				stringBuffer.append("\r\n");
			}

		}
		return stringBuffer.toString();
	}
	
	public String insertListVauleNote() {
		String aString = " listSBuilder.append(\"fileNameThreeListXXX\");";
		String list_template = "#'{'list[{0}].fileNameXXX}";
		
		StringBuffer stringBuffer = new StringBuffer();
		
		String fileNameThree = "";
		for (int i = 0, j = fieldNameList.size(); i < j; i++) {
			
			String fieldName = fieldNameList.get(i).trim();
			
			fileNameThree += list_template.replace("fileNameXXX", fieldName);
			
			if (i != j - 1) {
				fileNameThree += " , " ;//+ disposeFieldTwo("`" + fieldName + "`", 2);
				if ((i + 1) % 3 == 0) {
					String replace = aString.replace("fileNameThreeListXXX", fileNameThree);
					stringBuffer.append("\t\t\t");
					stringBuffer.append(replace);
					stringBuffer.append("\r\n");
					fileNameThree = "";
				}
			} 
			else {// 处理最后1、2个
				String replace = aString.replace("fileNameThreeListXXX", fileNameThree);
				stringBuffer.append("\t\t\t");
				stringBuffer.append(replace);
				stringBuffer.append("\r\n");
			}
			
		}
		return stringBuffer.toString();
	}
	
	
	public static void main(String[] args) {
		Table table = new Table();
		List<String> fieldNameList2 = new ArrayList<String>();
		fieldNameList2.add("test1u");
		fieldNameList2.add("test2uuuu");
		fieldNameList2.add("test3u");
		fieldNameList2.add("test4uuuuu");
		fieldNameList2.add("test4");
		fieldNameList2.add("test46666666666666");
		fieldNameList2.add("test4");
		fieldNameList2.add("test4");
		fieldNameList2.add("test4");
		fieldNameList2.add("test4");
//		String insertList2 = table.insertListVauleNote(fieldNameList2);
//		System.out.println(insertList2);
	}
	

	//根据key更新updateNoteList
	public String updateNoteList() {

		String updateTitle = "\t\t\tUPDATE(\"tablenameXXX\");\r\n";
		String template = "\t\t\tSET(\"fieldXXX = #'{'list[{0}].fieldXXX }\");\r\n";
		String keyTemplate = "\t\t\t\tWHERE(\"keyXXX = #'{'list[{0}].keyXXX }\");\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}
		updateTitle = updateTitle.replace("tablenameXXX", tableName);
		resultMybatis += keyTemplate.replace("keyXXX", keyName);
		return updateTitle + resultMybatis;
	}
	
	//根据key更新，中间不做判断
	public String updateNote() {
		
		String updateTitle = "\t\t\tUPDATE(\"tablenameXXX\");\r\n";
		String template = "\t\t\tSET(\"fieldXXX = #{ fieldXXX }\");\r\n";
		String keyTemplate = "\t\t\t\tWHERE(\"keyXXX = #{ keyXXX }\");\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}
		updateTitle = updateTitle.replace("tablenameXXX", tableName);
		resultMybatis += keyTemplate.replace("keyXXX", keyName);
		return updateTitle + resultMybatis;
	}
	
	
	//根据key更新，中间做判断
	public String updateNotePandan() {
		
		String updateTitle = "\t\t\tUPDATE(\"tablenameXXX\");\r\n";
		String template = 
				"\t\t\tif (isNotEmpty(param.get(\"fieldXXX\"))) {" + 
				"\tSET(\"fieldXXX = #{fieldXXX}\");" + 
				"\t}\r\n";
		String keyTemplate = "\t\t\tWHERE(\"keyXXX = #{ keyXXX }\");\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}
		updateTitle = updateTitle.replace("tablenameXXX", tableName);
		resultMybatis += keyTemplate.replace("keyXXX", keyName);
		return updateTitle + resultMybatis;
	}

	
	public String updateConditionNote() {

//		String updateTitle = "\t\tUPDATE(\"tablenameXXX\");\r\n";
//		String template = "\t\tSET(\"fieldXXX = #{ fieldXXX }\");\r\n";
//		String resultMybatis = "";
//		for (int i = 0; i < fieldNameList2.size(); i++) {
//			String fieldName = fieldNameList2.get(i);
//			resultMybatis += template.replace("fieldXXX", fieldName);
//		}
//		updateTitle = updateTitle.replace("tablenameXXX", "tableName");
		String updateTitle = "\t\t\tUPDATE(\"tablenameXXX\");\r\n";
		String template = 
				"\t\t\tif (isNotEmpty(param.get(\"fieldXXX\"))) {" + 
				"\tSET(\"fieldXXX = #{fieldXXX}\");" + 
				"\t}\r\n";
		String keyTemplate = "\t\t\tWHERE(\"keyXXX = #{ keyXXX }\");\r\n";
		String resultMybatis = "";
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			resultMybatis += template.replace("fieldXXX", fieldName);
		}
		updateTitle = updateTitle.replace("tablenameXXX", tableName);
		return updateTitle + resultMybatis + mybatisAnnection();
	}

	/**
	 * 实体类相关字符串拼接
	 */
	private void onSetAndGet() {
		for (int i = 0; i < fieldNameList.size(); i++) {

			String fieldName = fieldNameList.get(i);// 字段

			String TypeName = getType(fieldTypeList.get(i));// 数据类型

			String fieldNameUp = getFieldNameUp(fieldName);// 首字母大写

			String mid = "";

			mid = this.setAndGet.replace("FieldXXX", fieldName);

			mid = mid.replace("TypyXXX", TypeName);

			mid = mid.replace("FieldUpXXX", fieldNameUp);

			this.setAndGetText += mid;
		}
	}

	/**
	 * 让首字母大写
	 *
	 * @param s
	 * @return
	 */

	private String getFieldNameUp(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 首字母转小写
	 *
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0))) {
			return s;
		} else {
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
		}
	}

	/**
	 * 文档中的相关字符串拼接
	 */
	private void onloadDoc() {
		for (int i = 0; i < fieldNameList.size(); i++) {
			String fieldName = fieldNameList.get(i);
			String TypeName = getType(fieldTypeList.get(i));
//			System.out.println("TypeName：" + TypeName + "---" + fieldName + "--" + fieldTypeList.get(i));
			String comments = fieldCommentsList.get(i);
			// 实体类字段
			String privateT = "\r\n\t " + "\n\tprivate " + TypeName + " " + fieldName + ";//" + comments;

			this.privateFieldText += privateT;
			String mid = "";
			mid = this.docDemo.replace("fieldsXXX", fieldName);
			mid = mid.replace("typeXXX", TypeName);
			mid = mid.replace("commentsXXX", comments);
			this.doc += mid;
		}
		this.doc_doc = this.doc;
		this.doc = this.docTitle + this.doc;
		System.out.println(doc_doc);
		System.out.println(doc);
	}

	/**
	 * mysql转java数据类型的方法
	 */
	private String getType(String typeName) {
		if ("INT".equals(typeName)) {
			return "Integer";
		} else if ("VARCHAR".equals(typeName) || "TIMESTAMP".equals(typeName) || "LONGTEXT".equals(typeName)
				|| "TEXT".equals(typeName) || "DATETIME".equals(typeName) || "DATE".equals(typeName)) {
			return "String";
		} else if ("BIGINT".equals(typeName)) {
			return "Integer";
		} else if ("DECIMAL".equals(typeName)) {
			return "BigDecimal";
		} else if ("FLOAT".equals(typeName)) {
			return "Float";
		} else if ("TINYINT".equals(typeName)) {// tinyint timestamp
			return "Integer";
		} else if ("DOUBLDE".equals(typeName)) {// tinyint timestamp
			return "Double";
		} else {
			return "Object";
		}
	}

	/**
	 * Mapper文档中的相关字符串拼接
	 */
	private void onloadContent() {
		
		StringBuffer column_List = new StringBuffer();
		StringBuffer column_List_a = new StringBuffer();
		StringBuffer insertSingle = new StringBuffer();
		StringBuffer insertSingleValue = new StringBuffer();
		StringBuffer insertList = new StringBuffer();
		// 求到字段最大长度
		for (int i = 0, j = fieldNameList.size(); i < j; i++) {
			String fieldName = fieldNameList.get(i).trim();
			this.fieldMostLength = fieldName.length() > this.fieldMostLength ? fieldName.length()
					: this.fieldMostLength;
		}

		for (int i = 0, j = fieldNameList.size(); i < j; i++) {
			String fieldName = fieldNameList.get(i).trim();
			column_List.append("`" + fieldName + "`");
			column_List_a.append("`a`.`" + fieldName + "`");

			if (i != j - 1) {
				column_List.append(" , " + disposeFieldTwo("`" + fieldName + "`", 2));
				column_List_a.append(" , " + disposeFieldTwo("`a`.`" + fieldName + "`", 6));
				if ((i + 1) % 3 == 0) {
					column_List_a.append("\r\n\t\t");
					column_List.append("\r\n\t\t");
				}
			}
			if (fieldName.equals("createDate") || fieldName.equals("modifyDate") || fieldName.equals("delFlag")) {
				continue;
			}
			insertSingle.append(this.insertSingle.replace("fieldXXX", "`" + fieldName + "` ,"));
			insertSingleValue.append(this.insertSingleValue.replace("fieldXXX", fieldName));
			insertList.append(this.insertList.replace("fieldXXX", fieldName));

			this.insertCondition += insert.replace("fieldXXX", fieldName);
			this.insertValueCondition += insertValue.replace("fieldXXX", fieldName);
			this.selectCondition += select.replace("fieldXXX", fieldName);
			this.selectCondition_a += select_a.replace("fieldXXX", fieldName);
			this.updateCondition += update.replace("fieldXXX", fieldName);

		}
		this.fieldStrings = column_List.toString();
		this.fieldStringsa = column_List_a.toString();
		this.insertSingle = insertSingle.toString();
		this.insertSingleValue = insertSingleValue.toString();
		this.insertList = insertList.toString();
//		System.out.println(this.fieldStrings);
//		System.out.println(this.fieldStringsa);
//		System.out.println(this.insertList);
//		System.out.println(this.selectCondition_a);

	}

	/**
	 * 为了排序好看，做成的方法
	 * 
	 * @param string
	 * @param j
	 * @return
	 */
	private String disposeField(String string, int j) {
		int difference = this.fieldMostLength - string.length() + j;

		for (int i = 0; i < difference; i++) {
			string += " ";
		}
		return string;
	}

	/**
	 * 为了排序好看，做成的方法===找字段最长的，让每个字段的长度都等于最长的。
	 * 
	 * @param string
	 * @param j
	 * @return
	 */
	private String disposeFieldTwo(String string, int j) {
		int difference = this.fieldMostLength - string.length() + j;
		String kongString = "";
		for (int i = 0; i < difference; i++) {
			kongString += " ";
		}
		return kongString;
	}


	public String getDelName() {
		if (this.delName != null && this.delName.equals("")) {
			return this.delName;
		}
		return "delFlag";
	}
	
	

}
