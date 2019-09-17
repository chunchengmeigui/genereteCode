package com.dabao.databaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dabao.databaseUtil.content.StringContent;
import com.dabao.databaseUtil.entity.Project;
import com.dabao.databaseUtil.entity.Table;
import com.dabao.databaseUtil.entity.TableMsg;
import com.dabao.databaseUtil.util.DateUtil;
import com.dabao.databaseUtil.util.FileUtil;
import com.dabao.databaseUtil.util.StrUtils;

public class Create {

	static String downUrl = "";

	/**
	 * @author WRF
	 * @param url        存放生成文件的路径（如：D:/）
	 * @param pakegeName 生成文件的包名，用于controller，dao，service，mybatisXml 文件的包名或映射路径
	 * @param tableMap   表名 和 对应 的 类名
	 */
	public static void start(String url, String pakegeName, Map<String, String> tableMap) {
		for (String tableName : tableMap.keySet()) {
			String[] infomation_arr = tableMap.get(tableName).split(",");
			String className = infomation_arr[0];
			String TableChinessName = "";
			if (infomation_arr.length > 1) {
				TableChinessName = infomation_arr[1];
			}
			TableMsg table = new TableMsg(tableName, className, pakegeName, TableChinessName);
			if (Project.getController()) {
				// 生成路径
				String controllerUrl = url + File.separator + table.getControllerUrl();
				// 创建目录
				FileUtil.createDir(controllerUrl);
				// 替换需要变动的地方
				String controller = replaceText(StringContent.controller, table);
				// 生成代码
				FileUtil.createFile(controllerUrl + File.separator + table.getControllerFlieName(), controller);

				Project.setDao(true);

				Project.setService(true);
			}
			if (Project.getService()) {
				String serviceUrl = url + File.separator + table.getServiceUrl();
				FileUtil.createDir(serviceUrl);
				String service = replaceText(StringContent.service, table);
				FileUtil.createFile(serviceUrl + File.separator + table.getServiceFlieName(), service);
			}

			if (Project.getEntity()) {
				String entityUrl = url + File.separator + table.getEntityUrl();
				FileUtil.createDir(entityUrl);
				String entity = replaceText(StringContent.entity, table);
				FileUtil.createFile(entityUrl + File.separator + table.getEntityFlieName(), entity);
			}
			if (Project.getDoc()) {
				String docUrl = url + File.separator + "doc";
				FileUtil.createDir(docUrl);
				String doc = replaceText(StringContent.doc, table);
				FileUtil.createFile(docUrl + File.separator + table.getDocFlieName(), doc);
			}
			if (Project.getNoteDevelop()) {// 注解开发

				if (Project.isEntityDevelop()) {// 实体类
					if (Project.getDao()) {

						String daoUrl = url + File.separator + table.getDaoUrl();
						FileUtil.createDir(daoUrl);
						String dao = replaceText(StringContent.daoNoteEntity, table);
						FileUtil.createFile(daoUrl + File.separator + table.getDaoFlieName(), dao);

					}
					if (Project.getMybatis()) {
						String mapperProviderUrl = url + File.separator + table.getMapperProviderUrl();
						FileUtil.createDir(mapperProviderUrl);
						String mapperNote = replaceTextEntity(StringContent.mapperNoteEntity, table);
						FileUtil.createFile(mapperProviderUrl + File.separator + table.getMapperProviderFlieName(),
								mapperNote);
					}
				} else {

					if (Project.getDao()) {

						String daoUrl = url + File.separator + table.getDaoUrl();
						FileUtil.createDir(daoUrl);
						String dao = replaceText(StringContent.daoNote, table);
						FileUtil.createFile(daoUrl + File.separator + table.getDaoFlieName(), dao);

					}
					if (Project.getMybatis()) {
						String mapperProviderUrl = url + File.separator + table.getMapperProviderUrl();
						FileUtil.createDir(mapperProviderUrl);
						String mapperNote = replaceTextNote(StringContent.mapperNote, table);
						FileUtil.createFile(mapperProviderUrl + File.separator + table.getMapperProviderFlieName(),
								mapperNote);
					}
				}

			} else {
				if (Project.getDao()) {
					String daoUrl = url + File.separator + table.getDaoUrl();
					FileUtil.createDir(daoUrl);
					String dao = replaceText(StringContent.dao, table);
					FileUtil.createFile(daoUrl + File.separator + table.getDaoFlieName(), dao);
				}
				if (Project.getMybatis()) {
					String mybatisUrl = url + File.separator + table.getMybatisUrl();
					FileUtil.createDir(mybatisUrl);
					String mybatis = replaceText(StringContent.mapper, table);
					FileUtil.createFile(mybatisUrl + File.separator + table.getMybatisFlieName(), mybatis);
				}

			}

		}
	}

	private static String replaceText(String template, TableMsg table) {
		// 公共的数据
		template = template.replace("comXXX", table.getPakageName());
		template = template.replace("classXXX", table.getClassName());
		template = template.replace("classMinXXX", table.getClassMinName());
		template = template.replace("keyXXX", table.getKeyName());
		template = template.replace("keyValueXXX", StrUtils.underline2Camel(table.getKeyName()));
		template = template.replace("delXXX", table.getDelName());
		template = template.replace("TableChinessNameXXX", table.getTableChinessName());
		template = template.replace("authorXXX", table.getAuthor());
		template = template.replace("DateXXX", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm"));
		// xml的数据
		template = template.replace("tablenameXXX", table.getTableName());
		template = template.replace("fieldsXXX", table.getFieldStrings());
		template = template.replace("fieldsAXXX", table.getFieldStringsa());

		template = template.replace("BaseResultMapXXX", table.baseResultMapxml());
		template = template.replace("baseQueryXXX", table.xmlBaseQuery());
		template = template.replace("BaseResultMapXXX", table.baseResultMapxml());
		template = template.replace("baseQueryUpdateXXX", table.baseQueryUpdate());

		template = template.replace("insertXmlXXX", table.insertXml());
		template = template.replace("insertListXmlXXX", table.insertListXml());

		template = template.replace("updateXmlXXX", table.updateXml());

		template = template.replace("entityXXX", table.entityGetSet());

//        template = template.replace("insertXXX", table.getInsertCondition());
//        template = template.replace("insertvalueXXX", table.getInsertValueCondition());
//        template = template.replace("insertSingleXXX", table.getInsertSingle());
//        template = template.replace("insertListXXX", table.getInsertList());
//        template = template.replace("updateXXX", table.getUpdateCondition());
//        template = template.replace("baseQueryXXX", table.getSelectCondition());
		template = template.replace("字段XXX", table.getDoc());
//         //实体类
//        template = template.replace("privateXXX", table.getPrivateFieldText());
//        template = template.replace("publicXXX", table.getSetAndGetText());

		return template;
	}

	private static String replaceText2(String template, TableMsg table) {
		// 公共的数据
		template = template.replace("comXXX", table.getPakageName());
		template = template.replace("classXXX", table.getClassName());
		template = template.replace("classMinXXX", table.getClassMinName());
		template = template.replace("keyXXX", table.getKeyName());
		template = template.replace("delXXX", table.getDelName());
		template = template.replace("TableChinessNameXXX", table.getTableChinessName());
		template = template.replace("authorXXX", table.getAuthor());
		template = template.replace("DateXXX", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm"));
		// xml的数据
		template = template.replace("tablenameXXX", table.getTableName());
		template = template.replace("fieldsXXX", table.getFieldStrings());
		template = template.replace("fieldsAXXX", table.getFieldStringsa());
		template = template.replace("insertXXX", table.getInsertCondition());
		template = template.replace("insertvalueXXX", table.getInsertValueCondition());
		template = template.replace("insertSingleXXX", table.getInsertSingle());
		template = template.replace("insertListXXX", table.getInsertList());
		template = template.replace("updateXXX", table.getUpdateCondition());
		template = template.replace("baseQueryXXX", table.getSelectCondition());
		template = template.replace("字段XXX", table.getDoc());
		// 实体类
		template = template.replace("privateXXX", table.getPrivateFieldText());
		template = template.replace("publicXXX", table.getSetAndGetText());

		return template;
	}
	
	private static String replaceTextEntity(String template, TableMsg table) {
		// 公共的数据
		template = template.replace("comXXX", table.getPakageName());
		template = template.replace("classXXX", table.getClassName());
		template = template.replace("classMinXXX", table.getClassMinName());
		template = template.replace("keyXXX", table.getKeyName());
		template = template.replace("delXXX", table.getDelName());
		template = template.replace("TableChinessNameXXX", table.getTableChinessName());
		template = template.replace("authorXXX", table.getAuthor());
		template = template.replace("DateXXX", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm"));

		template = template.replace("tablenameXXX", table.getTableName());
		template = template.replace("BaseQueryNoteXXX", table.mybatisAnnectionEntity());
		template = template.replace("insertNoteXXX", table.insertNote());
		template = template.replace("insertListNoteXXX", table.insertListNote());
		template = template.replace("insertListValueNoteXXX", table.insertListVauleNote());
		template = template.replace("updateByPrimaryKeyNoteXXX", table.updateNotePandanEntity());
		template = template.replace("updateByConditionXXX", table.updateConditionNoteEntity());
		template = template.replace("updateListNoteXXX", table.updateNoteList());
		
		return template;
	}

	private static String replaceTextNote(String template, TableMsg table) {
		// 公共的数据
		template = template.replace("comXXX", table.getPakageName());
		template = template.replace("classXXX", table.getClassName());
		template = template.replace("classMinXXX", table.getClassMinName());
		template = template.replace("keyXXX", table.getKeyName());
		template = template.replace("delXXX", table.getDelName());
		template = template.replace("TableChinessNameXXX", table.getTableChinessName());
		template = template.replace("authorXXX", table.getAuthor());
		template = template.replace("DateXXX", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm"));

		template = template.replace("tablenameXXX", table.getTableName());
		template = template.replace("BaseQueryNoteXXX", table.mybatisAnnection());
		template = template.replace("insertNoteXXX", table.insertNote());
		template = template.replace("insertListNoteXXX", table.insertListNote());
		template = template.replace("insertListValueNoteXXX", table.insertListVauleNote());
		template = template.replace("updateByPrimaryKeyNoteXXX", table.updateNotePandan());
		template = template.replace("updateByConditionXXX", table.updateConditionNote());
		template = template.replace("updateListNoteXXX", table.updateNoteList());

		template = template.replace("privateXXX", table.getPrivateFieldText());
		template = template.replace("publicXXX", table.getSetAndGetText());

		return template;
	}

}
