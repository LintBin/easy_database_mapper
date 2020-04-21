package com.wecan.generation.spell.callback;

public class MapperNameCallBack implements SpellNameCallBack{

    private JavaClassNameCallBack javaClassNameCallBack = new JavaClassNameCallBack();

    @Override
    public String execute(String oldName) {
        String javaClassName = javaClassNameCallBack.execute(oldName);
        return javaClassName;
    }
}
