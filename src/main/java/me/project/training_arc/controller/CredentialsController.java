package me.project.training_arc.controller;


import me.project.training_arc.model.Client;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.service_impl.ClientServiceImpl;
import me.project.training_arc.service_impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class CredentialsController {

    @Autowired
    private RegistrationServiceImpl service;

    @Autowired
    private ClientServiceImpl client;

    @PostMapping
    public ResponseEntity<Credentials> register(@RequestBody Credentials cred,
                                                @RequestParam int age) {
        System.out.println("Hey я зашел в контроллер с креденшиалами)))");
        client.saveClient(cred.getLogin(), age);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(cred));
    }
}
