package com.wecan.exception;

public class NotDealtException extends RuntimeException{
	private static final long serialVersionUID = -2016099611924168847L;

	public NotDealtException(){
		super("不支持此选项");
	}
}