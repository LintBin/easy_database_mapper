package com.wecan.factory;

import com.wecan.config.DbConfig;
import com.wecan.config.DbType;
import com.wecan.data.MySQLDatabase;
import com.wecan.data.SQLServerDatabase;
import com.wecan.exception.NotSupportDataBaseException;
import com.wecan.data.Database;
public class DatabaseFactory {

    public static Database getDatabaseInstance() throws NotSupportDataBaseException {
        return getDataBase();
    }
    /**
     * 得到数据库
     * @return DataBase
     * @author linhongbin
     * @throws NotSupportDataBaseException
     */
    private static Database getDataBase() throws NotSupportDataBaseException{
        DbConfig dbConfig = new DbConfig();

        DbType type = dbConfig.getType();

        switch (type){
            case MySQL:
                return new MySQLDatabase(dbConfig);
            case SQLServer:
                return new SQLServerDatabase(dbConfig);
        }

        throw new NotSupportDataBaseException();
    }

}

