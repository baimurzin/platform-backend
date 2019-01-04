package com.pwrstd.platform.backend.controller.errors;

public class LoginAlreadyUsedException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(ErrorConstants.DEFAULT_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
