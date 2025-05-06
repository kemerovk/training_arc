package me.project.training_arc.controller;

import me.project.training_arc.service.ClientService;
import me.project.training_arc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public String successPage(){
        return "success";
    }

}
