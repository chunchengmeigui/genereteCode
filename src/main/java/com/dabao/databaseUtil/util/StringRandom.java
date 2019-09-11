package com.dabao.databaseUtil.util;

import java.util.Random;

/**
 * 
 * @author wyy
 * 2017-10-19
 */

public class StringRandom {
	
	public static String getStringRandom(int count,int capital,int small){ 
		
		String val="";
		
		Random random =new Random();
		for(int i=0;i<count;i++){
			//随机生成一个数字或者字母
			String charOrNum = random.nextInt(2) % 2==0?"char":"num";
			if(charOrNum.equals("char")){
				  //生成一个大写或者小写
				  int temp =random.nextInt(2) % 2 ==0?capital:small; 
				  //生成字母
				  val+=(char)(random.nextInt(26)+temp);
				  
			}else{
			     val+=String.valueOf(random.nextInt(10));
			}
		}
		
		return val;
	} 

}
