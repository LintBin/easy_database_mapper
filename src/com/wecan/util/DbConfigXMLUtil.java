package com.wecan.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;

/**
 * @author  linhongbin
 * @version:  V1.0
 */
public class DbConfigXMLUtil {
	private static Document doc;
	private static String dbType;
	private static String dbName;
	private static String dbUsername;
	private static String dbPassword;
	private static String dbHost;
	private static String dbport;

	static {
		try {
			File file = new File("src/dbconfig.xml");
			doc = Jsoup.parse(file, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		dbType = doc.select("db-type").text();
		dbName = doc.select("db-dbname").text();
		dbPassword = doc.select("db-password").text();
		dbUsername = doc.select("db-username").text();
		dbHost = doc.select("db-host").text();
		dbport = doc.select("db-port").text();
	}

	public static String getDbType() {
		return dbType;
	}

	public static void setDbType(String dbType) {
		DbConfigXMLUtil.dbType = dbType;
	}

	public static String getDbName() {
		return dbName;
	}

	public static void setDbName(String dbName) {
		DbConfigXMLUtil.dbName = dbName;
	}

	public static String getDbUsername() {
		return dbUsername;
	}

	public static void setDbUsername(String dbUsername) {
		DbConfigXMLUtil.dbUsername = dbUsername;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		DbConfigXMLUtil.dbPassword = dbPassword;
	}

	public static String getDbHost() {
		return dbHost;
	}

	public static void setDbHost(String dbHost) {
		DbConfigXMLUtil.dbHost = dbHost;
	}

	public static Document getDoc() {
		return doc;
	}

	public static void setDoc(Document doc) {
		DbConfigXMLUtil.doc = doc;
	}

	public static String getDbport() {
		return dbport;
	}

	public static void setDbport(String dbport) {
		DbConfigXMLUtil.dbport = dbport;
	}
}
