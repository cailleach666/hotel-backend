package org.example.backend.test.controller.client;

import org.example.backend.test.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerTest extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createClient() {
    }

    @Test
    void getClients() {
    }

    @Test
    void getClient() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteClient() {
    }
}