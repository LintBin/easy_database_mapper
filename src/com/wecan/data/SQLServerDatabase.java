package com.wecan.data;

import com.wecan.config.DbConfig;
import com.wecan.dao.SQLServerSysColumnsDao;
import com.wecan.dao.SQLServerSysObjectsDao;
import com.wecan.dao.entity.SysColumns;
import com.wecan.factory.DatabaseConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerDatabase extends Database {

    private final static String urlBase = "jdbc:sqlserver://#{host}:#{port};databaseName=#{dbName}";

    public SQLServerDatabase(DbConfig dbConfig) {
        super(dbConfig);
    }

    @Override
    public String getUrl() {
        String host = dbConfig.getDbConfigXMLUtil().getDbHost();
        String url = urlBase.replace("#{host}",host);

        String port = dbConfig.getDbConfigXMLUtil().getDbport();
        url = url.replace("#{port}",port);

        String dbName = dbConfig.getDbConfigXMLUtil().getDbName();
        url = url.replace("#{dbName}",dbName);

        return url;

    }

    @Override
    public String getDriver() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    @Override
    public List<Table> getTableList() throws SQLException {

        List<Table> tableList = new ArrayList<>() ;
        try ( Connection connection = getConnection()){
            SQLServerSysObjectsDao sqlServerSysObjectsDao = new SQLServerSysObjectsDao(dbConfig);

            List<String> tableNameList = sqlServerSysObjectsDao.getAllTableName(connection);

            SQLServerSysColumnsDao sqlServerSysColumnsDao = new SQLServerSysColumnsDao(dbConfig);

            for(String tableName : tableNameList){
                Table table = new Table();
                table.setName(tableName);

                List<SysColumns> sysColumnsList = sqlServerSysColumnsDao.getAllColumnByTableName(tableName, connection);
                List<Column> columnList = new ArrayList<>();
                for(SysColumns sysColumns : sysColumnsList){
                    Column column = new Column(tableName);
                    column.setName(sysColumns.getColumnName());
                    column.setType(SQLServerColumnType.getName(sysColumns.getTypeCode()));
                    columnList.add(column);
                }

                table.setList(columnList);
                tableList.add(table);
                System.out.println("table:" + table.getName());
            }

        }



        return tableList;
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnectionFactory.getConnection(this);
    }


    //sql server里面对应的类型 枚举类型
    public enum SQLServerColumnType {

        IMAGE("image",34),
        TEXT("text",35),
        TINY_INT("tinyint",48),
        SMALL_INT("smallint",52),
        INT("int",56),
        SMALL_DATE_TIME("smalldatetime",58),
        REAK("real",59),
        MONEY("money",60),
        DATE_TIME("datetime",61),
        FLOAT("float",62),
        N_TEXT("ntext",99),
        BIT("bit",104),
        DECIMAL("decimal",106),
        NUMERIC("numeric",108),
        SMALL_MONEY("smallmoney",122 ),
        BIG_INT("bigint",127),
        VAR_BINARY("varbinary",165),
        VAR_CHAR("varchar",167 ),
        BINARY("binary",173),
        CHAR("char",175),
        TIMESAMP("timestamp",189),
        N_VAR_CHAR("nvarchar",231),
        SYS_NAME("sysname",231),
        N_CHAR("nchar",239),
        XML("xml",241);


        // 构造方法
        SQLServerColumnType(String type, int index) {
            this.type = type;
            this.index = index;
        }

        private String type;
        private int index;

        public String getType(){
            return this.type;
        }

        public int getIndex(){
            return this.index;
        }

        /**
         * 根据索引,获得对应类型
         * @param index
         * @return
         */
        public static String getName(int index){
            for (SQLServerColumnType e : SQLServerColumnType.values()) {
                if (e.getIndex() == index) {
                    return e.type;
                }
            }
            return null;
        }

    }
}
