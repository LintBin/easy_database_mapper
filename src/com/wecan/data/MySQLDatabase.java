package com.wecan.data;

import com.wecan.config.DbConfig;
import com.wecan.dao.MySQLColumnDao;
import com.wecan.factory.DatabaseConnectionFactory;
import com.wecan.util.DbConfigXMLUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabase extends Database{

    private final static String urlBase = "jdbc:mysql://#{host}:#{port}/#{dbName}?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&serverTimezone=Asia/Shanghai";

    public MySQLDatabase(DbConfig dbConfig) {
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
    public String getDriver(){
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public List<Table> getTableList() throws SQLException {
        List<Table> tables = new ArrayList<>() ;

        try ( Connection connection = getConnection();){
            MySQLColumnDao mySQLColumnDao = new MySQLColumnDao(dbConfig);
            List<String> tableNameList = mySQLColumnDao.getAllTableName(connection);

            for(String tableName : tableNameList){
                Table table = new Table();
                table.setName(tableName);
                table.setList(mySQLColumnDao.getAllColumnByTableName(tableName ,connection));
                tables.add(table);
                System.out.println("table:" + table.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }



    private Connection getConnection() throws SQLException {
        return DatabaseConnectionFactory.getConnection(this);
    }

}
