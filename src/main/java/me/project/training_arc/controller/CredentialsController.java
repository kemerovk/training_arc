package me.project.training_arc.controller;


import me.project.training_arc.dto.SignUpRequest;
import me.project.training_arc.model.Client;
import me.project.training_arc.model.Credentials;
import me.project.training_arc.service.ClientServiceImpl;
import me.project.training_arc.service.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credentials")
public class CredentialsController {

    @Autowired
    private RegistrationServiceImpl service;

    @Autowired
    private ClientServiceImpl client;

    @PostMapping("register")
    public ResponseEntity<Integer> register(@RequestBody SignUpRequest request){
        Credentials credentials = service.register(request.login(), request.password());
        Client client1 = client.saveClient(new Client(request));

        if (credentials != null && client1 != null) {

        }

        return ResponseEntity.ok();
    }
//
//    @DeleteMapping
//    public ResponseEntity.BodyBuilder deleteUser(@RequestParam String login)  {
//        service.delete(login);
//        client.deleteByLogin(login);
//        return ResponseEntity.ok();
//    }



}
