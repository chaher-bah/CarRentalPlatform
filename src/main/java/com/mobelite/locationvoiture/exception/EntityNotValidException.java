package com.mobelite.locationvoiture.exception;

import lombok.Getter;

import java.util.List;
@Getter
public class EntityNotValidException extends RuntimeException {
    private ErrorCodes errorCode;
    private List<String> errorCodes;

    public EntityNotValidException (String message) {
        super(message);
    }

    public EntityNotValidException (String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotValidException(String message,ErrorCodes errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public EntityNotValidException(String message,ErrorCodes errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public EntityNotValidException(String message,ErrorCodes errorCode,List<String> errorCodes) {
        super(message);
        this.errorCode = errorCode;
        this.errorCodes = errorCodes;
    }
}
