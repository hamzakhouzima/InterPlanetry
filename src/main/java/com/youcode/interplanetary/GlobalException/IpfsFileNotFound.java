package com.youcode.interplanetary.GlobalException;

public class IpfsFileNotFound extends RuntimeException {
    public IpfsFileNotFound(String message) {
        super(message);
    }
}
