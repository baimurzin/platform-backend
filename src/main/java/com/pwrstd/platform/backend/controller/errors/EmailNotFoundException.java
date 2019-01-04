package com.pwrstd.platform.backend.controller.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EmailNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, "Email address not registered", Status.BAD_REQUEST);
    }
}
