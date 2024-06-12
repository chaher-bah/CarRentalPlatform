package com.mobelite.locationvoiture.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCodes {

    CAR_NOT_FOUND(1000),
    CAR_NOT_VALID(1001),

    RESERVATION_NOT_FOUND(2000),
    RESERVATION_NOT_VALID(2001),

    CLIENT_NOT_FOUND(3000),
    CLIENT_NOT_VALID(3001),

    NOTIFICATION_NOT_FOUND(4000),
    NOTIFICATION_NOT_VALID(4001);

    private final int code;


}
