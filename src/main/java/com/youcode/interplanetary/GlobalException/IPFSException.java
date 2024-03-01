package com.youcode.interplanetary.GlobalException;


import lombok.ToString;

@ToString
public class IPFSException extends RuntimeException{

    public  IPFSException(String message) {
        super(message);
    }

}
