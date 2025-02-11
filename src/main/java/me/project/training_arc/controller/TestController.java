package me.project.training_arc.controller;

import me.project.training_arc.model.Client;
import me.project.training_arc.service_impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private ClientServiceImpl clientService;

    @RequestMapping("test")
    public String test() {
        return "test";
    }


    @GetMapping()
    public String getAllClients(Model model) {
        List<Client> clients = clientService.getClients();
        model.addAttribute("clients", clients);
        return "list";
    }

    @GetMapping("add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "add";
    }

    @PostMapping("add")
    public String addClient(@ModelAttribute Client client) {
        clientService.saveClient(client);
        return "redirect:";
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String updateClient(@PathVariable int id, @ModelAttribute Client client) {
        clientService.updateClient(client, id);
        return "redirect:";
    }

    @GetMapping("delete/{id}")
    public String deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
        return "redirect:";
    }

}
