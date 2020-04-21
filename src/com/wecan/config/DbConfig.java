package com.wecan.config;

import com.wecan.exception.NotSupportDataBaseException;
import com.wecan.util.DbConfigXMLUtil;

public class DbConfig {
    private DbConfigXMLUtil dbConfigXMLUtil = new DbConfigXMLUtil();

    public DbType getType(){
        String result = dbConfigXMLUtil.getDbType().toLowerCase();

        if ("mysql".equals(result)){
            return DbType.MySQL;
        }

        if("sqlserver".equals(result)){
            return DbType.SQLServer;
        }

        throw new NotSupportDataBaseException();
    }

    public DbConfigXMLUtil getDbConfigXMLUtil() {
        return dbConfigXMLUtil;
    }

    public void setDbConfigXMLUtil(DbConfigXMLUtil dbConfigXMLUtil) {
        this.dbConfigXMLUtil = dbConfigXMLUtil;
    }
}
