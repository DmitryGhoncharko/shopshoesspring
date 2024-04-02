package com.example.shopshoesspring.error;

public class CannotShowUserProductBucketError extends Error{
    public CannotShowUserProductBucketError() {
    }

    public CannotShowUserProductBucketError(String message) {
        super(message);
    }

    public CannotShowUserProductBucketError(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotShowUserProductBucketError(Throwable cause) {
        super(cause);
    }

    public CannotShowUserProductBucketError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
