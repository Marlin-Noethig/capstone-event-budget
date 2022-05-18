package de.neuefische.backend.controller;

import de.neuefische.backend.repository.PositionsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PositionsControllerTest {

    @LocalServerPort
    private  int port;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PositionsRepo positionsRepo;

    @BeforeEach
    public void setUp(){
        positionsRepo.deleteAll();
    }

    @Test
    void getPositions() {
        //will be written, when post-method is implemented to controller
        assertTrue(true);
    }
}