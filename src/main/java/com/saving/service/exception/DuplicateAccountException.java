package com.saving.service.exception;

public class DuplicateAccountException extends RuntimeException{
            public DuplicateAccountException(
                    String message
    ){
        super(message);
    }

}
