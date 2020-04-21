package com.wecan.generation;



import com.wecan.data.Table;
import com.wecan.generation.spell.JavaSpeller;
import com.wecan.generation.spell.MapperXmlSpell;

import java.io.*;
import java.util.List;

/**
 * 控制写入文件的类
 * @author  linhongbin
 * @data:  2015年2月12日 11:00:42
 * @version:  V1.0
 */
public class TableWriter {
	List<Table> tableList;

	JavaSpeller javaSpeller = new JavaSpeller();
	MapperXmlSpell mapperXmlSpell = new MapperXmlSpell();

	public TableWriter(List<Table> tableList ){
		this.tableList = tableList;
	}
	
	/**
	 * 生成javabean
	 * @throws IOException
	 */
	public void creatJavaBean() throws IOException{


		File dir = new File("javabean");
		if(dir.exists()){

			File[] files = dir.listFiles();

			for(int i=0;i<files.length;i++){
				File file = files[i];
				file.delete();
			}

		}
		dir.mkdir();


		for(Table table : tableList){
			//生成文件
			String filePath = "javabean/" + javaSpeller.getJavaClassName(table) + ".java";
			File file = new File(filePath);
			Boolean exists = file.exists();

			file.createNewFile();
			FileWriter fileWritter = new FileWriter(filePath, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(javaSpeller.getTableContent(table));
			bufferWritter.close();
		}
		for(Table table : tableList){
			System.out.println(table.toString());
		}
	}

	public void createMapperXml() throws IOException {
		String dirName = "mybatis-xml";
		File dir = new File(dirName);

		if(dir.exists()){

			File[] files = dir.listFiles();

			for(int i=0;i<files.length;i++){
				File file = files[i];
				file.delete();
			}
		}
		dir.mkdir();

		for(Table table : tableList) {

			String filePath = dirName + "/" + mapperXmlSpell.getMapperXmlName(table);
			//生成文件
			File file = new File(filePath);
			file.createNewFile();

			FileWriter fileWritter = new FileWriter(filePath, true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			bufferWritter.write(mapperXmlSpell.getMapperContent(table));
			bufferWritter.close();
		}


	}

}
