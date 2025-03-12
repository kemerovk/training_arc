package me.project.training_arc.controller;


import me.project.training_arc.model.Credentials;
import me.project.training_arc.service_impl.RegistrationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class CredentialsController {

    @Autowired
    private RegistrationServiceImpl service;

    @PostMapping
    public ResponseEntity<Credentials> register(@RequestBody Credentials credentials) {
        System.out.println("Hey я зашел в контроллер с креденшиалами)))");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(credentials));
    }
}
