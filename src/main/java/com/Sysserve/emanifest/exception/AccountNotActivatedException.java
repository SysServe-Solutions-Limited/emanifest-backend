package com.Sysserve.emanifest.exception;

public class AccountNotActivatedException extends RuntimeException{
    public AccountNotActivatedException(String message){
        super(message);
    }
}
