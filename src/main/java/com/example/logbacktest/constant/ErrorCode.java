package com.example.logbacktest.constant;

import java.util.Optional;
import java.util.function.Predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ok case
    OK(200, ErrorCategory.NORMAL, "Ok"),

    // 4xx error case
    BAD_REQUEST(400, ErrorCategory.CLIENT_SIDE, "Bad request"),

    // 5xx error case
    INTERNAL_ERROR(500, ErrorCategory.SERVER_SIDE, "Internal error"),

    ;

    private final Integer code; // error code
    private final ErrorCategory errorCategory; // error category
    private final String message; // error message

    public String getMessage(Throwable e) {
        return this.getMessage(e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    // client side error?
    public boolean isClientSideError() {
        return this.getErrorCategory() == ErrorCategory.CLIENT_SIDE;
    }

    // server side error?
    public boolean isServerSideError() {
        return this.getErrorCategory() == ErrorCategory.SERVER_SIDE;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }

    // error category enum
    public enum ErrorCategory {
        NORMAL, CLIENT_SIDE, SERVER_SIDE
    }
}
