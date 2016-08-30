package com.demo.wms.exeptions;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by toxa on 8/26/2016.
 */
public class LoginAlreadyExistsException extends Throwable {

    public LoginAlreadyExistsException(DataIntegrityViolationException dve) {
        super(dve);
    }
}
