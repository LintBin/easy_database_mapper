package com.wecan.generation.spell;

import com.wecan.data.Column;
import com.wecan.data.Table;
import com.wecan.generation.encode.Constants;
import com.wecan.generation.spell.callback.JavaClassNameCallBack;
import com.wecan.generation.spell.callback.JavaPropertyNameCallback;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MapperTemplateSpell extends BaseSpell {
    static Properties prop = new Properties();
    static {
        try {
            prop.load(new FileInputStream("src/path-generate.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 得到mapper的模版
     * @return
     */
    public String getMapperXmlTemplate(){
        File mapperXmlTemplateFile = new File("src/com/wecan/template/resultListDetailTemplate");

        List<String> stringList = new ArrayList<>();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(new FileInputStream(mapperXmlTemplateFile), Constants.FILE_ENCODING);

            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = "";
            line = br.readLine();
            while (line != null) {
                line = br.readLine(); // 一次读入一行数据

                if(line != null){
                    stringList.add(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();

        for(String string : stringList){
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    /**
     * 得到命名空间
     * 如:namespace="com.wecan.chepai.mapper.PriceForecast"
     * @param javaClassNameCallBack
     * @param table
     * @return
     */
    public String getNamespace(JavaClassNameCallBack javaClassNameCallBack ,Table table){
        String tableName = table.getName();
        String javaClassName =  javaClassNameCallBack.execute(tableName);

        String namespace = getXmlNamespace() + "." + javaClassName;

        return namespace;
    }

    /**
     * 得到参数
     * type="com.wecan.chepai.mapper.PriceForecast"
     * @param javaClassNameCallBack
     * @param table
     * @return
     */
    public String getParameterType(JavaClassNameCallBack javaClassNameCallBack ,Table table){
        String tableName = table.getName();
        String javaClassName =  javaClassNameCallBack.execute(tableName);

        String namespace = getXmlParameterType() + "." + javaClassName;
        return namespace;
    }

    public String getBaseResultType(JavaClassNameCallBack javaClassNameCallBack,Table table){
        return getParameterType(javaClassNameCallBack, table);
    }

    public String getXmlParameterType(){
        return prop.getProperty("xml.insert.parameterType");
    }


    /**
     * 获取XML的命名空间
     * TODO 不应该每次都读取文件
     * @return
     */
    private String getXmlNamespace(){
        return prop.getProperty("xml.namespace");
    }

    /**
     * 获取sqlDetail
     * @return
     */
    public String getSqlDetail(Table table){
        List<Column> columnList = table.getList();
        StringBuilder stringBuilder = new StringBuilder();

        for(Column column : columnList){

            String string = "`" + column.getName() + "` , ";
            stringBuilder.append(string);
        }

        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }

    public String getResultMapDetail(){

        String result = "";

        File mapperXmlTemplateFile = new File("src/com/wecan/template/ResultListTemplate");
        InputStreamReader reader = null;
        String line = "";
        try {
            reader = new InputStreamReader(new FileInputStream(mapperXmlTemplateFile), Constants.FILE_ENCODING);
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            line = br.readLine();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }

    public String getTableName(Table table){
        return table.getName();
    }

    public String getColumnNameList(Table table){
        String sqlDetail = getSqlDetail(table);
        return sqlDetail;
    }

    public String getPropertyList(Table table){
        String propertyListStr = "";
        List<Column> columnList = table.getList();
        for(Column column : columnList){
            String columnName = column.getName();

            JavaPropertyNameCallback javaPropertyNameCallback = new JavaPropertyNameCallback();
            String javaPropertyName = javaPropertyNameCallback.execute(columnName);

            propertyListStr = propertyListStr + "#{"+ javaPropertyName +"} ,";
        }
        Integer length = propertyListStr.length();
        propertyListStr = propertyListStr.substring(0, length - 1);

        return propertyListStr;
    }
}
