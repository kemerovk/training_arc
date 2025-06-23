package me.project.training_arc.service;

import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.exceptions.custom_exception.UsernameAlreadyExistsException;
import me.project.training_arc.model.Client;
import me.project.training_arc.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public boolean updateMinioPath(String login, String minioPath){
        int x = clientRepository.updateMinioPath(minioPath, login);
        System.out.println(x + " rows was updated");
        System.out.println("login: " + login + " minioPath: " + minioPath );
        return x > 0;
    }

    public Client getClientById(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            throw new UserNotFoundException("Не удалось найти пользователя");
        }
        return clientRepository.getReferenceById(id);
    }

    public List<Client> getClients(){
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) throw new UserNotFoundException("Не получилось найти пользователей");
        return clientRepository.findAll();
    }

    public Client saveClient(Client client){
        if (clientRepository.findByLogin(client.getLogin()).isPresent()) throw new UsernameAlreadyExistsException("Логин занят");
        return clientRepository.save(client);
    }


    public void deleteById(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) throw new UserNotFoundException("Пользователя с таким id не существует");
        clientRepository.deleteById(id);
    }



    public Client findByLogin(String login){
        Optional<Client> client = clientRepository.findByLogin(login);
        if (client.isEmpty()) throw new UserNotFoundException("Не получилось найти пользователя");
        return client.get();
    }

}
