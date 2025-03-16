package me.project.training_arc.service;

import me.project.training_arc.model.Credentials;

public interface RegistrationService {
    Credentials register(String username, String password);

    Credentials register(Credentials credentials);

    void delete(String login);

    Credentials update(String oldLogin, String newLogin);
}
