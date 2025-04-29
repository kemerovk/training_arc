package me.project.training_arc.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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




    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable
                                               @Positive int id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
