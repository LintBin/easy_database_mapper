package com.wecan.generation.spell;

import com.wecan.data.Column;
import com.wecan.data.Table;
import com.wecan.exception.TypeNotRecognizedException;
import com.wecan.generation.DataTypeHelper;
import com.wecan.generation.spell.callback.JavaClassNameCallBack;
import com.wecan.generation.spell.callback.JavaPropertyNameCallback;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * java类生成的拼写规则
 * @author  BintLin
 * @data:  2015年1月8日 1 23:09:51
 * @version:  V1.0
 */
public class JavaSpeller extends BaseSpell{
	private static final String NEW_LINE = "\n";
	private static JavaClassNameCallBack javaClassNameCallBack = new JavaClassNameCallBack();
	private static JavaPropertyNameCallback javaPropertyNameCallback = new JavaPropertyNameCallback();
	private static JavaMethodSpeller javaMethodSpeller = new JavaMethodSpeller();
	
	/*private static String getTableName (Table table){
		return getAapitalizeFomat(table.getName());
	}*/




	public static String getJavaClassName(Table table){
		String tableName = table.getName();

		String newName = javaClassNameCallBack.execute(tableName);

		return newName;
	}

	/**
	 * 从表中的字段转换成JavaBean里面的内容
	 * @param table
	 * @return String
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String getTableContent(Table table) throws FileNotFoundException, IOException{
		System.out.println("table: " + table.getName() );
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(getAnnotation(table));
		stringBuffer.append("public class ");
		//得到javabean里面的内容
		stringBuffer.append(StringUtils.capitalize(getJavaClassName(table)));
		stringBuffer.append(SPACE + "{");
		stringBuffer.append(NEW_LINE);
		stringBuffer.append(getProperty(table));
		stringBuffer.append(getMethod(table));
		stringBuffer.append(NEW_LINE);
		stringBuffer.append("}");
		return stringBuffer.toString();
	}
	
	/**
	 * 得到属性,例如
	 * 		private String name;
	 * 		private int age;
	 * @param table
	 * @return StringBuffer
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private StringBuffer getProperty(Table table) throws FileNotFoundException, IOException{
		StringBuffer stringBuffer = new StringBuffer();
		for (Column column : table.getList()){
			stringBuffer.append(ONE_TAB);
			stringBuffer.append("private ");
			
			try {
				stringBuffer.append(DataTypeHelper.judgeDataType(column.getType()));
			} catch (TypeNotRecognizedException e) {
				e.printStackTrace();
				stringBuffer.append(e.getType());
			}
			stringBuffer.append(" ");
			stringBuffer.append(this.getProperty(column.getName()));
			stringBuffer.append(";");
			stringBuffer.append(NEW_LINE);
		}
		stringBuffer.append(NEW_LINE);
		return stringBuffer;
	}


	/**
	 * 
	 * 方法
	 * @param table
	 * @return String
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws TypeNotRecognizedException 
	 */
	private static String getMethod(Table table) throws IOException {
		List<Column> columns = table.getList();
		StringBuffer stringBuffer = new StringBuffer();
		for (Column column : columns){
			String generationSet = javaMethodSpeller.generationSet(column);
			stringBuffer.append(generationSet);
			stringBuffer.append(NEW_LINE);

			String generationGet = javaMethodSpeller.generationGet(column);
			stringBuffer.append(generationGet);
			stringBuffer.append(NEW_LINE);


			/*************************set 方法****************************/
			/*stringBuffer.append(ONE_TAB);
			stringBuffer.append("public ");
			stringBuffer.append(camelCase);
			stringBuffer.append(" " + "set");
			stringBuffer.append(getAapitalizeFomat(columnName));
			stringBuffer.append("(String ");
			stringBuffer.append(propertyFomat(column.getName()));
			stringBuffer.append(")" + SPACE + "{" + NEW_LINE);
			stringBuffer.append(TWO_TAB+"this." + propertyFomat(columnName) + " = " +propertyFomat(columnName));
			stringBuffer.append(";" + NEW_LINE + ONE_TAB + "}" + NEW_LINE);
			stringBuffer.append(NEW_LINE);*/
			
			/*********************get 方法 ******************************/
			/*stringBuffer.append(ONE_TAB);
			stringBuffer.append("public ");
			stringBuffer.append(getAapitalizeFomat(column.getName()));
			stringBuffer.append("get");
			stringBuffer.append(getAapitalizeFomat(column.getName()));
			stringBuffer.append("()");
			stringBuffer.append(SPACE + "{" + NEW_LINE);
			stringBuffer.append(TWO_TAB);
			stringBuffer.append("return ");
			stringBuffer.append("this.");
			stringBuffer.append(propertyFomat(column.getName()));
			stringBuffer.append(";" + NEW_LINE);
			stringBuffer.append(ONE_TAB +"}" + NEW_LINE);
			stringBuffer.append(NEW_LINE);*/
		}
		return stringBuffer.toString();
	}

	
	/**
	 * 表名的注解
	 *  	//表名_table_name
	 * @return String
	 * @author linhongbin
	 */
	private static String getAnnotation(Table table){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("//表名");
		stringBuffer.append(table.getName());
		stringBuffer.append(NEW_LINE);
		return stringBuffer.toString();
	}


	public String getProperty(String columnName){

		return javaPropertyNameCallback.execute(columnName);
	}
}
 