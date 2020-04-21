package com.wecan.dao;

import com.wecan.config.DbConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerSysObjectsDao {
    private DbConfig dbConfig;

    public SQLServerSysObjectsDao( DbConfig dbConfig){
        this.dbConfig = dbConfig;
    }

    public List<String> getAllTableName(Connection connection) throws SQLException {
        String sql = "select name from sysobjects where xtype='U';";

        List<String> tableNameList = new ArrayList<>();

        PreparedStatement pstmt = connection.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String tableName = rs.getString(1);
            tableNameList.add(tableName);
        }
        pstmt.close();
        return tableNameList;
    }

}
