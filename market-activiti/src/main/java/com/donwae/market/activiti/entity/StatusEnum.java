package com.donwae.market.activiti.entity;

public enum StatusEnum {

	/**
	 * 未知异常
	 */
	UNKNOWN(-1, "unknow exception"),
	/**
	 * 成功
	 */
	SUCCESS(1000, "success"),
	/**
	 * 失败
	 */
	FAIL(0, "failed"),
	/**
	 * HTTP状态码
	 */
	CONTINUE(100, "Continue"),
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),
	PROCESSING(102, "Processing"),
	CHECKPOINT(103, "Checkpoint"),
	OK(200, "OK"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	PARTIAL_CONTENT(206, "Partial Content"),
	MULTI_STATUS(207, "Multi-Status"),
	ALREADY_REPORTED(208, "Already Reported"),
	IM_USED(226, "IM Used"),
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	FOUND(302, "Found"),
    /**
     * 业务异常状态码
     */
    PARAM_ERROR(2001, "参数错误");

	private final int value;
	private final String message;

	StatusEnum(int value, String message) {
		this.value = value;
		this.message = message;
	}

	public int value() {
		return value;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return Integer.toString(this.value);
	}

}
