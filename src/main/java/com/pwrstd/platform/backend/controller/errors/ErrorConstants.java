package com.pwrstd.platform.backend.controller.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String PROBLEM_BASE_URL = "https://www.itlabel.org/problem";
    public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/message");

    private ErrorConstants() {}
}
