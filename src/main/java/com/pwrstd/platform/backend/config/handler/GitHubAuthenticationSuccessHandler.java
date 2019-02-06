package com.pwrstd.platform.backend.config.handler;

import com.pwrstd.platform.backend.dto.UserDTO;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.UserConnection;
import com.pwrstd.platform.backend.model.key.UserConnectionId;
import com.pwrstd.platform.backend.repository.UserSocialConnectionRepository;
import com.pwrstd.platform.backend.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class GitHubAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();
    private final UserService userService;
    private final UserSocialConnectionRepository usersSocialConnectionRepository;

    public GitHubAuthenticationSuccessHandler(UserService userService, UserSocialConnectionRepository usersSocialConnectionRepository) {
        this.userService = userService;
        this.usersSocialConnectionRepository = usersSocialConnectionRepository;
    }

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        OAuth2Authentication auth = (OAuth2Authentication) authentication;
        Map<String, String> details = (Map<String, String>) auth.getUserAuthentication().getDetails();
        if (savedRequest == null || userService.getUserByEmail(details.get("email")).isPresent()) {
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }

        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request
                .getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);

            return;
        }


        User user = userService.registerUser(UserDTO.builder().email(details.get("email")).build());

        OAuth2AuthenticationDetails authDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        usersSocialConnectionRepository.save(UserConnection.builder()
                .accessToken(authDetails.getTokenValue())
                .displayName((String) auth.getPrincipal())
                .userConnectionId(UserConnectionId.builder()
                        .providerId("github")//todo remove
                        .providerUserId(String.valueOf(details.get("id")))
                        .build())
                .user(user)
                .imageUrl(details.get("avatar_url"))
                .profileUrl(details.get("url"))
                .userid(details.get("email"))
                .build());

        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

}
