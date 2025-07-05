package me.project.training_arc.controller;

import me.project.training_arc.model.Client;
import me.project.training_arc.service.ClientService;
import me.project.training_arc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("clients")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RegistrationService service;


    @GetMapping("me")
    public ResponseEntity<Client> getCurrentClient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(clientService.findByLogin(username));

    }

}
