package me.project.training_arc.service_impl;

import me.project.training_arc.model.Client;
import me.project.training_arc.repository.ClientRepository;
import me.project.training_arc.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client getClientById(int id){
        return clientRepository.getReferenceById(id);
    }

    @Override
    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    @Override
    public Client saveClient(Client client){
        return clientRepository.save(client);
    }

    @Override
    public Client saveClient(String login, int age) {
        Client client = new Client(login, age);
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(int id){
        clientRepository.deleteById(id);
    }

    @Override
    public Client updateClient(Client client, int id) {
        return clientRepository.save(client);
    }

}
