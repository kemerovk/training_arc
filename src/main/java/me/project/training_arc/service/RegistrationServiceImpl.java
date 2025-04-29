package me.project.training_arc.service;

import me.project.training_arc.dto.SignUpRequest;
import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.exceptions.custom_exception.UsernameAlreadyExistsException;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.repository.ClientRepository;
import me.project.training_arc.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl  {


    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//
//    public Credentials register(SignUpRequest request) {
//        if (credentialsRepository.findByLogin(login) != null) throw new UsernameAlreadyExistsException("Пользователь с таким логином уже существует");
//        Credentials credentials = new Credentials();
//        credentials.setLogin(login);
//        credentials.setPassword(passwordEncoder.encode(password));
//        credentialsRepository.save(credentials);
//        return credentials;
//    }


    public void delete(String login){
        Credentials cred = credentialsRepository.findByLogin(login);
        if (cred == null) throw new UserNotFoundException("Невозмодно найти пользователя с таким логином");
        credentialsRepository.delete(cred);
    }

    public Credentials update(String originLogin, String loginToSet) {
        Credentials newCred = credentialsRepository.findByLogin(originLogin);
        if (credentialsRepository.findByLogin(loginToSet) != null) throw new UsernameAlreadyExistsException("Пользователь с таким логином уже существует");
        newCred.setLogin(loginToSet);
        return credentialsRepository.save(newCred);
    }
}
