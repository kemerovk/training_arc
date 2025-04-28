package me.project.training_arc.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import me.project.training_arc.dto.ClientDto;
import me.project.training_arc.model.Client;
import me.project.training_arc.service.ClientServiceImpl;
import me.project.training_arc.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RequestMapping("clients")
@RestController
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @Autowired
    private RegistrationServiceImpl service;

    @RequestMapping("test")
    public String test() {
        return "test";
    }


    @CrossOrigin(origins = "http:localhost:5173")
    @GetMapping()
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getClients();
        return ResponseEntity.status(HttpStatus.CREATED).body(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable
                                               @Positive(message = "ахахахах лох") int id){
        Client client = clientService.getClientById(id);
        ClientDto dao = new ClientDto(client.getLogin(), client.getAge());
        return ResponseEntity.status(HttpStatus.OK).body(dao);
    }


    @PostMapping("add")
    public ResponseEntity<Client> addClient(@RequestBody
                                            @Valid ClientDto client) {
        Client newClient = new Client();
        newClient.setLogin(client.login());
        newClient.setAge(client.age());

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(newClient));
    }


    @PatchMapping("edit/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable
                                               @Positive int id,
                                               @RequestBody
                                               @Valid ClientDto client) {
        Client client1 = clientService.getClientById(id);
        client1.setLogin(client.login());
        client1.setAge(client.age());
        clientService.saveClient(client1);
        return ResponseEntity.status(HttpStatus.OK).body(client1);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable
                                               @Positive int id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
