package com.example.shopshoesspring.error;

public class CannotAddToBucketError extends Error{
    public CannotAddToBucketError() {
    }

    public CannotAddToBucketError(String message) {
        super(message);
    }

    public CannotAddToBucketError(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotAddToBucketError(Throwable cause) {
        super(cause);
    }

    public CannotAddToBucketError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
