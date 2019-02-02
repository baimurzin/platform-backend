package com.pwrstd.platform.backend.model.key;

import org.springframework.social.connect.ConnectionKey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserConnectionId implements Serializable {

    @Column(name = "providerid")
    private final String providerId;

    @Column(name = "provideruserid")
    private final String providerUserId;

    /**
     * Creates a new {@link ConnectionKey}.
     * @param providerId the id of the provider e.g. facebook
     * @param providerUserId id of the provider user account e.g. '125660'
     */
    public UserConnectionId(String providerId, String providerUserId) {
        this.providerId = providerId;
        this.providerUserId = providerUserId;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }
}
