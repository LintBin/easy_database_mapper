package com.wecan.exception;

public class NotSupportDataBaseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NotSupportDataBaseException(){
		super("此数据库暂不支持");
	}
	
}
