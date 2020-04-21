package com.wecan.generation.spell.callback;

import org.apache.commons.lang.StringUtils;

public class JavaPropertyNameCallback implements SpellNameCallBack{

    @Override
    public String execute(String oldName) {
        return propertyFormat(oldName);
    }

    /**
     * 规范属性名
     *  FORM_CODE -- formCode
     * @param property
     * @return String
     */
    private String propertyFormat(String property){

        //如果没有下划线
        if(property.indexOf("_") < 0){
            //如果全部为大写
            String upperCase = property.toUpperCase();
            if(upperCase.equals(property)){
                return property.toLowerCase();
            }
        }

        property = property.replaceFirst(property.substring(0, 1), property.substring(0, 1).toLowerCase());

        //如果不存在"_"的字符，则证明该数据库中不是以"_"来命名,而是以驼峰写法来命名
        if(property.indexOf("_") < 0){
            //将首字母变为小写
            return property;
        }else{
            property = StringUtils.lowerCase(property);
            StringBuffer stringBuffer = new StringBuffer();
            String[] strings ;
            strings = StringUtils.split(property, "_" );
            for(int i = 0 ; i<strings.length ; i++){
                if( i > 0){
                    stringBuffer.append(StringUtils.capitalize(strings[i])) ;
                }else{
                    stringBuffer.append(strings[0]);
                }
            }
            return stringBuffer.toString();
        }
    }

}
