package com.dabao.databaseUtil.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileUtil {
//	通过文件名称创建文件
	public static void createFile(String fileName,String content){
		String path = fileName;
		String sourceString = content;   //待写入字符串  
		byte[] sourceByte = sourceString.getBytes();  
		if(null != sourceByte){  
		    try {  
		        File file = new File(path);     //文件路径（路径+文件名）  
		        if (!file.exists()) {   //文件不存在则创建文件，先创建目录  
		            File dir = new File(file.getParent());  
//		            dir.mkdirs();  
		            file.createNewFile();  
		            FileOutputStream outStream = new FileOutputStream(file);    //文件输出流用于将数据写入文件  
		            outStream.write(sourceByte);  
		            outStream.close();  //关闭文件输出流  
		        }else{
		        	System.out.println("文件"+fileName+"已存在，不再创建！");
		        }  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		} 
	}
//	创建文件夹
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }  
	
    /*
     * 读取配置文件
     */
    private static String readFile(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);
            String tmpContent = null;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
                builder.append("\r\n");
            }
            bfReader.close();
        } catch (UnsupportedEncodingException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    
    /**
     * 读取文件路径
     *
     * @param filePath
     * @return
     */
    public static String getContent(String filePath) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("templates/" + filePath + ".txt");
        return readFile(inputStream);
    }


}
