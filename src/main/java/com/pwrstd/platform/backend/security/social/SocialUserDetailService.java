package com.pwrstd.platform.backend.security.social;

import com.pwrstd.platform.backend.security.DomainUserDetailsService;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SocialUserDetailService implements SocialUserDetailsService {

    private final DomainUserDetailsService userDetailsService;

    public SocialUserDetailService(DomainUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException, DataAccessException {
        return userDetailsService.loadUserByUsername(s);
    }
}
