package me.project.training_arc.service.service_impl;

import me.project.training_arc.dao.ClientDAO;
import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.exceptions.custom_exception.UsernameAlreadyExistsException;
import me.project.training_arc.model.Client;
import me.project.training_arc.repository.ClientRepository;
import me.project.training_arc.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client getClientById(int id){
        Optional<Client> client = clientRepository.findById(id);
        if (client.isEmpty()) {
            System.out.println("вот ты и попался");
            throw new UserNotFoundException("Не удалось найти пользователя");
        }
        return clientRepository.getReferenceById(id);
    }

    @Override
    public List<Client> getClients(){
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) throw new UserNotFoundException("Не получилось найти пользователей");
        return clientRepository.findAll();
    }

    @Override
    public Client saveClient(Client client){
        if (clientRepository.findByLogin(client.getLogin()) != null) throw new UsernameAlreadyExistsException("Логин занят");
        return clientRepository.save(client);
    }

    @Override
    public Client saveClient(String login, int age) {
        if (clientRepository.findByLogin(login) != null)
            throw new UsernameAlreadyExistsException("Логин уже занят");
        Client client = new Client(login, age);
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(int id){
        Client client = clientRepository.getReferenceById(id);
        if (client == null) throw new UserNotFoundException("Пользователя с таким id не существует");
        clientRepository.deleteById(id);
    }

    @Override
    public Client updateClient(ClientDAO client, int id) {
        Client cl = clientRepository.getReferenceById(id);
        if (!cl.getLogin().equals(client.login())) {
            if (clientRepository.findByLogin(client.login()) != null) throw new UsernameAlreadyExistsException("Логин занят");
        }
        return clientRepository.save(cl);
    }

    @Override
    public Client findByLogin(String login){
        Client client = clientRepository.findByLogin(login);
        if (client == null) throw new UserNotFoundException("Не получилось найти пользователя");
        return client;
    }

    @Override
    public void deleteByLogin(String login) {
        clientRepository.delete(clientRepository.findByLogin(login));
    }

}
