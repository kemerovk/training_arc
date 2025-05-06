package me.project.training_arc.service;


import me.project.training_arc.model.Credentials;
import me.project.training_arc.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Credentials> credentials = credentialsRepository.findByLogin(login);
        if (credentials.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return credentials.get();
    }
}
