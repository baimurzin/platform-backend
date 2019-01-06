package com.pwrstd.platform.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public enum  Role {
    USER("ROLE_USER"),
    ANONYMOUS("ROLE_ANONYMOUS"),
    ADMIN("ROLE_ADMIN"),
    UNCONFIRMED("ROLE_UNCONFIRMED")
    ;

    Role(String roleName) {

    }
}
