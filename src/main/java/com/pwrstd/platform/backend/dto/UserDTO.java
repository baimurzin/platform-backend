package com.pwrstd.platform.backend.dto;


import org.springframework.social.connect.UserProfile;

public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String timezone;

    private String langKey;

    public UserDTO() {
    }

    public static UserDTO fromSocialUserProfile(UserProfile userProfile) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userProfile.getEmail());
        return userDTO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
