package com.wecan.exception;

public class TypeNotRecognizedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	
	private String type ;
	public TypeNotRecognizedException(String type){
		super("不识别数据库的类型:"  + type);
		this.type = type;
	}
	
	public String getType(){
		return type;
	}
}
