package me.project.training_arc.controller;

import jakarta.servlet.http.HttpServletRequest;
import me.project.training_arc.dao.ClientDAO;
import me.project.training_arc.model.Client;
import me.project.training_arc.service_impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("clients")
@RestController
public class TestController {

    @Autowired
    private ClientServiceImpl clientService;

    @RequestMapping("test")
    public String test() {
        return "test";
    }


    @CrossOrigin(origins = "http:localhost:5173")
    @GetMapping()
    public ResponseEntity<List<Client>> getAllClients(Model model) {
        List<Client> clients = clientService.getClients();
        for (Client client : clients) {
            System.out.println(client.getLogin());
        }
        model.addAttribute("clients", clients);
//        System.out.println("АLL");
//        return "list";
        return ResponseEntity.status(HttpStatus.CREATED).body(clients);
    }

    @GetMapping("{id}")
    public ResponseEntity<ClientDAO> getClient(@PathVariable int id) {
        Client client = clientService.getClientById(id);
        ClientDAO dao = new ClientDAO(client.getLogin(), client.getAge());
        return ResponseEntity.status(HttpStatus.OK).body(dao);
    }

    @GetMapping("add")
    private String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        System.out.println("Add");
        return "add";
    }

    @PostMapping("add")
    public ResponseEntity<Client> addClient(@RequestBody ClientDAO client, HttpServletRequest req) {
        Client newClient = new Client();
        newClient.setLogin(client.login());
        newClient.setAge(client.age());

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(newClient));
    }

    @GetMapping("edit/{id}")
    private String showEditForm(@PathVariable int id, Model model) {
        Client client = clientService.getClientById(id);
        model.addAttribute("client", client);
        System.out.println("Edit");
        return "edit";
    }

    @PatchMapping("edit/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable int id, @RequestBody ClientDAO client) {
        Client client1 = clientService.getClientById(id);
        client1.setLogin(client.login());
        client1.setAge(client.age());
        clientService.saveClient(client1);
        return ResponseEntity.status(HttpStatus.OK).body(client1);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable int id) {
        clientService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
