package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwrstd.platform.backend.model.key.UserConnectionId;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "userconnection") //default table fro social
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class UserConnection {

    @Column(name = "userid")
    private String userid;

    @EmbeddedId
    private UserConnectionId userConnectionId;

    private int rank;

    @Column(name = "displayname")
    private String displayName;

    @Column(name = "profileurl")
    private String profileUrl;

    @Column(name = "imageurl")
    private String imageUrl;

    @Column(name = "accesstoken")
    private String accessToken;

    @Column(name = "secret")
    private String secret;

    @Column(name = "refreshtoken")
    private String refreshToken;

    @Column(name = "expiretime")
    private Integer expireTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
