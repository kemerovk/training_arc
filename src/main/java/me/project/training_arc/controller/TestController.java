package me.project.training_arc.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.project.training_arc.dao.ClientDAO;
import me.project.training_arc.exceptions.custom_exception.UserNotFoundException;
import me.project.training_arc.model.Client;
import me.project.training_arc.service.service_impl.ClientServiceImpl;
import me.project.training_arc.service.service_impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("clients")
@RestController
public class TestController {

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
    public ResponseEntity<List<Client>> getAllClients(Model model) {
        List<Client> clients = clientService.getClients();
        for (Client client : clients) {
            System.out.println(client.getLogin());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientDAO> getClient(@PathVariable int id){
        Client client = clientService.getClientById(id);
        ClientDAO dao = new ClientDAO(client.getLogin(), client.getAge());
        return ResponseEntity.status(HttpStatus.OK).body(dao);
    }


    @PostMapping("add")
    public ResponseEntity<Client> addClient(@RequestBody ClientDAO client) {
        Client newClient = new Client();
        newClient.setLogin(client.login());
        newClient.setAge(client.age());

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(newClient));
    }


    @PatchMapping("edit/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody ClientDAO client) {
        Client client1 = clientService.getClientById(id);
        client1.setLogin(client.login());
        client1.setAge(client.age());
        clientService.saveClient(client1);
        return ResponseEntity.status(HttpStatus.OK).body(client1);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
