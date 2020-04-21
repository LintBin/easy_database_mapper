import com.wecan.data.Database;
import com.wecan.data.Table;
import com.wecan.factory.DatabaseFactory;
import com.wecan.generation.TableWriter;
import com.wecan.generation.spell.callback.JavaClassNameCallBack;
import com.wecan.util.DbConfigXMLUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        //获取数据库实例
        Database dataBaseInstance = DatabaseFactory.getDatabaseInstance();

        //从实例中获取所欲的表
        List<Table> tableList = dataBaseInstance.getTableList();

        TableWriter writer = new TableWriter(tableList);

        try {
            writer.creatJavaBean();
            writer.createMapperXml();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
