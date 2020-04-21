package com.wecan.generation.spell;

import com.wecan.data.Column;
import com.wecan.data.Table;
import com.wecan.generation.JdbcTypeHelper;
import com.wecan.generation.spell.callback.JavaClassNameCallBack;
import com.wecan.generation.spell.callback.JavaPropertyNameCallback;
import com.wecan.generation.spell.callback.MapperNameCallBack;

import java.util.List;

/**
 *
 */
public class MapperXmlSpell extends BaseSpell implements IMapperXmlSpell{

    private MapperNameCallBack mapperNameCallBack = new MapperNameCallBack();
    private JavaPropertyNameCallback javaPropertyNameCallback = new JavaPropertyNameCallback();
    private JavaClassNameCallBack javaClassNameCallBack = new JavaClassNameCallBack();


    @Override
    public String getMapperXmlName(Table table) {
        String tableName = mapperNameCallBack.execute(table.getName());

        String xmlName = tableName + "Mapper.xml";
        return xmlName;
    }

    @Override
    public String getMapperContent(Table table) {

        MapperTemplateSpell mapperTemplateSpell = new MapperTemplateSpell();

        String template = mapperTemplateSpell.getMapperXmlTemplate();

        String namespace = mapperTemplateSpell.getNamespace(javaClassNameCallBack, table);
        template = template.replace("#{namespace}", namespace);

        String parameterType = mapperTemplateSpell.getParameterType(javaClassNameCallBack, table);
        template = template.replace("#{parameterType}", parameterType);

        String sqlDetail = mapperTemplateSpell.getSqlDetail(table);
        template = template.replace("#{sqlDetail}", sqlDetail);

        String resultMapDetail = getResultMapDetail(mapperTemplateSpell ,table);
        template = template.replace("#{resultMapDetail}", resultMapDetail);


        String baseResultType = mapperTemplateSpell.getBaseResultType(javaClassNameCallBack, table);
        template = template.replace("#{baseResultType}", baseResultType);


        String tableName = mapperTemplateSpell.getTableName(table);
        template = template.replace("#{tableName}", tableName);

        String columnNameList = mapperTemplateSpell.getColumnNameList(table);
        template = template.replace("#{columnNameList}", columnNameList);

        String propertyList = mapperTemplateSpell.getPropertyList(table);
        template = template.replace("#{propertyList}", propertyList);


        return template;
    }

    private String getResultMapDetail(MapperTemplateSpell mapperTemplateSpell ,Table table){
        String resultMapDetail = mapperTemplateSpell.getResultMapDetail();

        String totalString = TWO_TAB;
        List<Column> columnList = table.getList();
        for(int i=0; i<columnList.size() ; i++){
            Column column = columnList.get(i);
            String columnName = column.getName();

            String item = new String(resultMapDetail);

            String javaPropertyName = javaPropertyNameCallback.execute(columnName);

            item = item.replace("#{columnName}" ,columnName);
            item = item.replace("#{property}" ,javaPropertyName);

            String type = column.getType();
            String jdbcType = JdbcTypeHelper.getMySQLJdbcType(type);
            if(jdbcType == null){
                System.out.println("没有" + type + "类型");
                continue;
            }

            item = item.replace("#{jdbcType}", jdbcType);
            totalString = totalString + item;
            if(i != columnList.size()-1){
                totalString = totalString + NEW_LINE + TWO_TAB;
            }
        }




        return totalString;

    }





}