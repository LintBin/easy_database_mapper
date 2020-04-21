package com.wecan.dao;

import com.wecan.config.DbConfig;
import com.wecan.data.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLColumnDao {

    private DbConfig dbConfig;

    public MySQLColumnDao( DbConfig dbConfig){
        this.dbConfig = dbConfig;
    }

    public List<String> getAllTableName(Connection connection) throws SQLException {
        String sql = "select TABLE_NAME from information_schema.columns where table_schema = ? GROUP BY TABLE_NAME;";

        List<String> tableNameList = new ArrayList<>();

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, dbConfig.getDbConfigXMLUtil().getDbName());
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String tableName = rs.getString(1);
            tableNameList.add(tableName);
        }
        pstmt.close();
        return tableNameList;
    }

    public List<Column> getAllColumnByTableName(String tableName ,Connection connection)
            throws SQLException {
        List<Column> result = new ArrayList<>();
        String sql = "select column_name , data_type from information_schema.columns where table_schema = ? and TABLE_NAME = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, dbConfig.getDbConfigXMLUtil().getDbName());
        pstmt.setString(2, tableName);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            Column column = new Column(tableName);
            column.setName(rs.getString(1));
            column.setType(rs.getString(2));
            result.add(column);
        }
        pstmt.close();
        return result;
    }

}
