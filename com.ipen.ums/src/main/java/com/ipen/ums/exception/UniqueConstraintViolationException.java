package com.ipen.ums.exception;

public class UniqueConstraintViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String fieldName;

    public UniqueConstraintViolationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
