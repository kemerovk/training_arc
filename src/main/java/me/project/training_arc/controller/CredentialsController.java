package me.project.training_arc.controller;


import me.project.training_arc.dto.CredentialsDto;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.service.service_impl.ClientServiceImpl;
import me.project.training_arc.service.service_impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credentials")
public class CredentialsController {

    @Autowired
    private RegistrationServiceImpl service;

    @Autowired
    private ClientServiceImpl client;

    @PostMapping
    public ResponseEntity<Credentials> register(@RequestBody CredentialsDto cred,
                                                @RequestParam int age) {
        client.saveClient(cred.login(), age);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(cred));
    }

    @DeleteMapping
    public ResponseEntity.BodyBuilder deleteUser(@RequestParam String login)  {
        service.delete(login);
        client.deleteByLogin(login);
        return ResponseEntity.ok();
    }



}
