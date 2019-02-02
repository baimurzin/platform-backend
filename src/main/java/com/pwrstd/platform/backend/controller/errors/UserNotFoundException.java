package com.pwrstd.platform.backend.controller.errors;

public class UserNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, "User not found!", "userManagement", "usernotexists");
    }
}
