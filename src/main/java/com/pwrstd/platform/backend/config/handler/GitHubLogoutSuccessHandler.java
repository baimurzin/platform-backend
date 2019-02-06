package com.pwrstd.platform.backend.config.handler;

import com.pwrstd.platform.backend.repository.UserSocialConnectionRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class GitHubLogoutSuccessHandler extends
        SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private final UserSocialConnectionRepository usersSocialConnectionRepository;

    public GitHubLogoutSuccessHandler(UserSocialConnectionRepository usersSocialConnectionRepository) {
        this.usersSocialConnectionRepository = usersSocialConnectionRepository;
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //todo
        super.onLogoutSuccess(request, response, authentication);
    }
}
