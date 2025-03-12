package me.project.training_arc.service_impl;


import me.project.training_arc.model.Credentials;
import me.project.training_arc.model.UsersPrincipal;
import me.project.training_arc.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private CredentialsRepository credentialsRepository;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Credentials cred = credentialsRepository.findByLogin(login);
        if (cred == null) {
            System.out.println("not found");
            throw new UsernameNotFoundException(login);
        }

        return new UsersPrincipal(cred);
    }
}
