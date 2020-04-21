package com.wecan.factory;

import com.wecan.config.DbType;
import com.wecan.data.Database;
import com.wecan.exception.NotSupportDataBaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {

    public static Connection getConnection(Database database) throws SQLException {

        DbType type = database.getDbConfig().getType();
        switch (type){
            case MySQL:{
               return DriverManager.getConnection(database.getUrl(),database.getUsername(),database.getPassword());
            }
            case SQLServer:
                return DriverManager.getConnection(database.getUrl(),database.getUsername(),database.getPassword());
            default: throw new NotSupportDataBaseException();
        }

    }
}
