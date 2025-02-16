package me.project.training_arc.service;

import me.project.training_arc.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;



public interface ClientService {

    public Client getClientById(int id);
    public List<Client> getClients();

    public Client saveClient(Client client);


    public void deleteById(int id);
    public Client updateClient(Client client, int id);
}
