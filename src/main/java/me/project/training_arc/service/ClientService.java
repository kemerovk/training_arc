package me.project.training_arc.service;

import me.project.training_arc.dto.ClientDto;
import me.project.training_arc.model.Client;

import java.util.List;



public interface ClientService {

    public Client findByLogin(String login);
    public Client getClientById(int id);
    public List<Client> getClients();

    public Client saveClient(Client client);

    public Client saveClient(String login, int age);

    public void deleteById(int id);
    public void deleteByLogin(String login);

    public Client updateClient(ClientDto client, int id);
}
