package com.pwrstd.platform.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.social.connect.UserProfile;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String timezone;

    private String langKey;

    public static UserDTO fromSocialUserProfile(UserProfile userProfile) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userProfile.getEmail());
        return userDTO;
    }
}
