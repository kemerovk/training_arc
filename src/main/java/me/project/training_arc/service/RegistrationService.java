package me.project.training_arc.service;

import me.project.training_arc.dto.SignUpRequest;
import me.project.training_arc.dto.SignUpResponse;
import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.model.Client;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.repository.ClientRepository;
import me.project.training_arc.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

@Service
public class RegistrationService {


    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public SignUpResponse register(SignUpRequest signUpRequest){
        if (clientRepository.findByLogin(signUpRequest.login()).isPresent()){
            throw new RuntimeException("Пользователь с таким логином уже зарегистрирован");
        }
        if (clientRepository.findByEmail(signUpRequest.email()).isPresent()){
            throw new RuntimeException("Пользователь с такой почтой уже зарегистрирован");
        }

        Calendar calendar = new GregorianCalendar();


        Client client = new Client(signUpRequest);
        client.setCreationTime(calendar.getTime());
        clientRepository.save(client);

        Credentials credentials = new Credentials(signUpRequest.login(), signUpRequest.email(), passwordEncoder.encode(signUpRequest.password()));
        credentials.setId(client.getId());
        credentialsRepository.save(credentials);

        return new SignUpResponse(credentials.getId(), signUpRequest.login(), signUpRequest.email());
    }


    @Transactional
    public void delete(int id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Credentials> cred = credentialsRepository.findById(id);
        if (cred.isEmpty()) throw new UserNotFoundException("Невозможно найти пользователя");
        Credentials cred1 = cred.get();
        if (!cred1.getUsername().equals(auth.getName())){
            throw new RuntimeException("Вам не разрешено удалять других пользователей");
        }
        credentialsRepository.delete(cred1);

        Optional<Client> client = clientRepository.findById(id);
        clientRepository.delete(client.get());
    }

    @Transactional
    public Credentials update(int id, Credentials newCred) {
        Optional<Credentials> cred = credentialsRepository.findById(id);
        if (cred.isEmpty()) {
            throw new RuntimeException("Не удалось найти пользователя");
        }
        Credentials credentials = cred.get();

        if (credentials.getPassword().equals(newCred.getPassword())) {
            Client client = clientRepository.findById(id).get();
            client.setLogin(newCred.getLogin());
            clientRepository.save(client);
        }
        credentials.setLogin(newCred.getLogin());
        credentials.setPassword(newCred.getPassword());
        credentialsRepository.save(credentials);
        return credentials;
    }
}
