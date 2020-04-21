package com.wecan.generation;

import com.wecan.exception.TypeNotRecognizedException;
import org.apache.commons.lang.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 数据库里面的类型对应的java的类型
 * @author BintLin
 *
 */
public class DataTypeHelper {
	static Properties prop ;
	static {
		prop = new Properties();
		try {
			prop.load(new FileInputStream("src/type.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成的java的属性对应的数据库中的类型
	 * @param type
	 * @return Java的类型
	 * 		data      ---  Data
	 *  	varchar   ---  String
	 *  	varchar2  ---  String
	 *  	bigint    ---  long
	 *  	int		  ---  int
	 * 		number    ---  long
	 * 	 	clob 	  ---  byte[]
	 *      blob      ---  byte[]
	 *      text      ---  String
	 * @throws TypeNotRecognizedException
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static String judgeDataType(String type) throws TypeNotRecognizedException, FileNotFoundException, IOException{
		String lowResult = StringUtils.lowerCase(type);

		Enumeration<Object> es = prop.keys();
		//读取Properties的数据
		while(es.hasMoreElements()){
			String lowTypeStr = (String) es.nextElement();
			if(StringUtils.equals(lowTypeStr,lowResult)){
				return prop.getProperty(lowTypeStr);
			}
		}
		return null;
	}
}
