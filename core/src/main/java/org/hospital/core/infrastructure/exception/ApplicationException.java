package org.hospital.core.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {

    private final transient Object errorCause;

    private final HttpStatus httpStatus;

    public ApplicationException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCause = null;
    }

    public ApplicationException(String message, Object errorCause, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCause = errorCause;
    }

    public ApplicationException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorCause = null;
    }
}
