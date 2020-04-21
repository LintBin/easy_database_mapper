package com.wecan.data;

import com.wecan.config.DbConfig;

import java.sql.SQLException;
import java.util.List;

public abstract class Database {
    DbConfig dbConfig;

    public Database(DbConfig dbConfig){
        this.dbConfig = dbConfig;

    }

    public abstract String getUrl();

    public String getPassword(){
        return dbConfig.getDbConfigXMLUtil().getDbPassword();
    }

    public String getUsername(){
        return dbConfig.getDbConfigXMLUtil().getDbUsername();
    }

    public abstract String getDriver();

    public abstract List<Table> getTableList() throws SQLException;

    public DbConfig getDbConfig() {
        return dbConfig;
    }

    public void setDbConfig(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}
