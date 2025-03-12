package me.project.training_arc.service_impl;

import me.project.training_arc.model.Credentials;
import me.project.training_arc.repository.CredentialsRepository;
import me.project.training_arc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {


    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Credentials register(String login, String password) {
        Credentials credentials = new Credentials();
        credentials.setLogin(login);
        credentials.setPassword(passwordEncoder.encode(password));
        credentialsRepository.save(credentials);
        return credentials;
    }

    @Override
    public Credentials register(Credentials credentials) {
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return credentialsRepository.save(credentials);
    }
}
