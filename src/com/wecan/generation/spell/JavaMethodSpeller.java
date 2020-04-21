package com.wecan.generation.spell;

import com.wecan.data.Column;
import com.wecan.generation.DataTypeHelper;
import com.wecan.generation.spell.callback.JavaPropertyNameCallback;

import java.io.IOException;

public class JavaMethodSpeller extends BaseSpell{

    private static JavaPropertyNameCallback javaPropertyNameCallback = new JavaPropertyNameCallback();

    public static String generationGet(Column column) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(ONE_TAB);

        String publicLine = "public #{type} get#{cap}(){" + NEW_LINE;
        String javaPropertyName = javaPropertyNameCallback.execute(column.getName());
        //把第一个字母大写
        String javaPropertyNameFirstCap = javaPropertyName.replaceFirst(javaPropertyName.substring(0, 1), javaPropertyName.substring(0, 1).toUpperCase());
        publicLine = publicLine.replace("#{cap}", javaPropertyNameFirstCap);

        String columnType = column.getType();
        String javaType = DataTypeHelper.judgeDataType(columnType);

        if(javaType != null){
            publicLine = publicLine.replace("#{type}", javaType);
        }

        stringBuffer.append(publicLine);

        stringBuffer.append(TWO_TAB + "return " + javaPropertyName + ";" + NEW_LINE);

        stringBuffer.append(ONE_TAB + "}");

        return stringBuffer.toString();
    }

    public static String generationSet(Column column) throws IOException {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(ONE_TAB);

        String publicLine = "public void set#{cap}(#{type} #{name}){" + NEW_LINE;
        String javaPropertyName = javaPropertyNameCallback.execute(column.getName());
        //把第一个字母大写
        String javaPropertyNameFirstCap = javaPropertyName.replaceFirst(javaPropertyName.substring(0, 1), javaPropertyName.substring(0, 1).toUpperCase());
        publicLine = publicLine.replace("#{cap}", javaPropertyNameFirstCap);


        String columnType = column.getType();
        String javaType = DataTypeHelper.judgeDataType(columnType);

        if(javaType != null){
            publicLine = publicLine.replace("#{type}", javaType);
        }

        publicLine = publicLine.replace("#{name}", javaPropertyName);

        stringBuffer.append(publicLine);

        stringBuffer.append(TWO_TAB + "this." + javaPropertyName + " = " + javaPropertyName + ";" +  NEW_LINE);

        stringBuffer.append(ONE_TAB + "}");


        return stringBuffer.toString();
    }
}
