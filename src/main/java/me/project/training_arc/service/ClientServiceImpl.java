package me.project.training_arc.service;

import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.exceptions.custom_exception.UsernameAlreadyExistsException;
import me.project.training_arc.model.Client;
import me.project.training_arc.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl {

    @Autowired
    private ClientRepository clientRepository;


    public Client getClientById(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            System.out.println("вот ты и попался");
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
        if (clientRepository.findByLogin(client.getLogin()) != null) throw new UsernameAlreadyExistsException("Логин занят");
        return clientRepository.save(client);
    }


    public void deleteById(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) throw new UserNotFoundException("Пользователя с таким id не существует");
        clientRepository.deleteById(id);
    }



    public Client findByLogin(String login){
        Client client = clientRepository.findByLogin(login);
        if (client == null) throw new UserNotFoundException("Не получилось найти пользователя");
        return client;
    }

}
