package com.example.uploading_files.storage;

public class StorageException extends RuntimeException{
    
    public StorageException(final String message) {
        super(message);
    }
    
    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
