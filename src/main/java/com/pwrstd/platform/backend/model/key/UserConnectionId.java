package com.pwrstd.platform.backend.model.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.ConnectionKey;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Builder
@Data
public class UserConnectionId implements Serializable {

    @Column(name = "providerid")
    private String providerId;

    @Column(name = "provideruserid")
    private String providerUserId;

    /**
     * Creates a new {@link ConnectionKey}.
     * @param providerId the id of the provider e.g. facebook
     * @param providerUserId id of the provider user account e.g. '125660'
     */
    public UserConnectionId(String providerId, String providerUserId) {
        this.providerId = providerId;
        this.providerUserId = providerUserId;
    }

    public UserConnectionId() {
    }

    public UserConnectionId(ConnectionKey key) {
        this.providerId = key.getProviderId();
        this.providerUserId = key.getProviderUserId();
    }

    public String getProviderId() {
        return providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }
}
