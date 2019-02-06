package com.pwrstd.platform.backend.service;


import com.pwrstd.platform.backend.controller.errors.BadRequestAlertException;
import com.pwrstd.platform.backend.model.User;
import com.pwrstd.platform.backend.model.UserConnection;
import com.pwrstd.platform.backend.repository.UserRepository;
import com.pwrstd.platform.backend.security.SecurityUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GitService {

    private final String PROVIDER_ID = "github";
    private final UserRepository userRepository;

    @Autowired
    public GitService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<GHRepository> getAllRepositories() throws IOException {
        return getClient().listAllPublicRepositories().asList();
    }


    public GHRepository getRepositoryByName(String name) throws IOException {
        return getClient().getRepository(name);
    }


    public GHRepository addNewRepository(String name) throws IOException {
        return getClient().createRepository(name)
                .autoInit(true)
                .private_(false)
                .wiki(true)
                .issues(true)
                .create();
    }

    private GitHub getClient() throws IOException {
        User currentUser = SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByEmailIgnoreCase)
                .orElseThrow(() -> new BadRequestAlertException("User not found", "user", "user_not_found"));
        UserConnection userConnection = currentUser.getConnections()
                .stream()
                .filter(con -> con.getUserConnectionId().getProviderId().equals(PROVIDER_ID))
                .findAny()
                .orElseThrow(() -> new BadRequestAlertException("Connection not found", "user", "connection_not_found"));
        return GitHub.connect(userConnection.getUserid(), userConnection.getAccessToken());
    }
}
