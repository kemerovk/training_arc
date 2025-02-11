package me.project.training_arc.controller;

import me.project.training_arc.model.Client;
import me.project.training_arc.service_impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("clients")
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
        for (Client client : clients) {
            System.out.println(client.getName());
        }
        model.addAttribute("clients", clients);
        System.out.println("АLL");
        return "list";
    }

    @GetMapping("add")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        System.out.println("Add");
        return "add";
    }

    @PostMapping("add")
    public String addClient(@ModelAttribute Client client) {
        System.out.println(client.getName() + " " + client.getAge());
        clientService.saveClient(client);
        return "redirect:/clients";
    }

    @GetMapping("edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        System.out.println("Edit");
        return "edit";
    }

    @PostMapping("edit/{id}")
    public String updateClient(@PathVariable int id, @ModelAttribute Client client) {
        clientService.updateClient(client, id);
        return "redirect:/clients";
    }

    @PostMapping("delete/{id}")
    public String deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
        return "redirect:/clients";
    }

}
