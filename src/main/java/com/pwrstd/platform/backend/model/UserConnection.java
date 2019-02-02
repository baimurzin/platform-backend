package com.pwrstd.platform.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pwrstd.platform.backend.model.key.UserConnectionId;

import javax.persistence.*;

@Entity
@Table(name = "userconnection") //default table fro social
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
    private int expireTime;

    public UserConnectionId getUserConnectionId() {
        return userConnectionId;
    }

    public void setUserConnectionId(UserConnectionId userConnectionId) {
        this.userConnectionId = userConnectionId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }
}
