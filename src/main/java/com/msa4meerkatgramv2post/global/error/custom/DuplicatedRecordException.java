package com.msa4meerkatgramv2post.global.error.custom;

public class DuplicatedRecordException extends RuntimeException {
    public DuplicatedRecordException(String message) {
        super(message);
    }
}
