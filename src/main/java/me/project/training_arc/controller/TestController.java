package me.project.training_arc.controller;

import me.project.training_arc.model.Client;
import me.project.training_arc.service_impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ClientServiceImpl clientService;

    @RequestMapping("test")
    public String test() {
        return "test";
    }


    @RequestMapping("clients")
    public @ResponseBody List<Client> clientList(){
        return clientService.getClients();
    }

}
