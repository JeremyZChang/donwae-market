package com.donwae.market.activiti.entity;

import lombok.Data;

@Data
public class Response {

	private boolean success = false;
	private Integer code;
	private String message;
	private Object data;

	private Response(Throwable e) {
		this.success = false;
		this.message = e.toString();
		this.code = StatusEnum.FAIL.value();
	}

	private Response(StatusEnum statusEnum, boolean success,Object data) {
		this.success = success;
		this.code = statusEnum.value();
		this.message = statusEnum.getMessage();
		this.data = data;
	}

	private Response(String errorMsg) {
		this.success = false;
		this.message = errorMsg;
		this.code = StatusEnum.FAIL.value();
	}

	public static Response success() {
		return new Response(StatusEnum.SUCCESS,true,null);
	}

	public  static Response success(Object data) {
		return new Response(StatusEnum.SUCCESS,true,data);
	}


	public static Response fail(String errorMsg) {
		return new Response(errorMsg);
	}

	public static Response fail(StatusEnum statusEnum) {
		return new Response(statusEnum,false,null);
	}

	public static Response fail(Throwable exception) {
		return new Response(exception);
	}

}