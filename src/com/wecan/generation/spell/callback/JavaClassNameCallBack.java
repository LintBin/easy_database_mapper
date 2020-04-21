package com.wecan.generation.spell.callback;

import org.apache.commons.lang.StringUtils;

public class JavaClassNameCallBack implements SpellNameCallBack{

    @Override
    public String execute(String oldName) {

        String newName = "";
        if(oldName.startsWith("e_")){
            newName = oldName.replaceFirst("e_", "");
        }else {
            newName = oldName;
        }

        newName = getAapitalizeFomat(newName);
        return newName;
    }

    /**
     * 得到首字母为大写的的驼峰写法
     * @param string
     * @return
     */
    private static String getAapitalizeFomat(String string){
        StringBuffer stringBuffer = new StringBuffer();
        String[] strings ;
        strings = StringUtils.split(string, "_" );
        for(int i = 0 ; i<strings.length ; i++){
            String item = StringUtils.capitalize(strings[i]);
            stringBuffer.append(item) ;
        }
        return stringBuffer.toString();
    }


}
