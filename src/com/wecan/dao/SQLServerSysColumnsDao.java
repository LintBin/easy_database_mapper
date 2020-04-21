package com.wecan.dao;

import com.wecan.config.DbConfig;
import com.wecan.dao.entity.SysColumns;
import com.wecan.data.Column;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLServerSysColumnsDao {
    private DbConfig dbConfig;

    public SQLServerSysColumnsDao(DbConfig dbConfig){
        this.dbConfig = dbConfig;
    }

    public List<SysColumns> getAllColumnByTableName(String tableName , Connection connection) throws SQLException {
        List<SysColumns> result = new ArrayList<>();
        String sql = "select name ,xtype from syscolumns where id = object_id(?) ;";
        System.out.println("sql");
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, tableName);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            SysColumns sysColumns = new SysColumns();
            sysColumns.setColumnName(rs.getString(1));

            int typeCode = rs.getInt(2);
            sysColumns.setTypeCode(typeCode);

            result.add(sysColumns);
        }
        pstmt.close();
        return result;
    }
}
