package com.pwrstd.platform.backend.controller.errors;

public class EmailAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(ErrorConstants.DEFAULT_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }
}
