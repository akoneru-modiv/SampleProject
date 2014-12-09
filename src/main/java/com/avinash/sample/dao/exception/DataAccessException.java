package com.avinash.sample.dao.exception;

import java.sql.SQLException;

/**
 * 10/14/2014
 *
 * @author avachev
 */
public class DataAccessException extends RuntimeException {
    
	Throwable rootCause;

    public DataAccessException() {
    }

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
        this.rootCause=cause;
    }

    public DataAccessException(Throwable cause) {
        super(cause);
        this.rootCause=cause;
    }
}
