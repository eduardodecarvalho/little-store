package com.littlestore.littlestore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.littlestore.littlestore.config.SpringBootIntegrationTest;
import com.littlestore.littlestore.domain.dto.ClientDTO;
import com.littlestore.littlestore.repository.ClientRepository;

@ActiveProfiles(profiles = "test")
public class ClientControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void findById() throws Exception {
        Integer id = 1;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/clients/" + id, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "{\"id\":1,\"birthDate\":null,\"name\":\"TED\"}";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void findByInvalidId_shouldReturnError() throws Exception {
        Integer id = 99;
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.getForEntity("http://localhost:" + port + "/v1/clients/" + id, String.class).getStatusCode());
    }

    @Test
    public void findAll() throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/clients", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "[{\"id\":1,\"birthDate\":null,\"name\":\"TED\"},{\"id\":2,\"birthDate\":null,\"name\":\"ROBIN\"},{\"id\":3,\"birthDate\":null,\"name\":\"LILY\"},{\"id\":4,\"birthDate\":null,\"name\":\"MARSHAL\"},{\"id\":5,\"birthDate\":null,\"name\":\"BARNEY\"},{\"id\":6,\"birthDate\":null,\"name\":\"VICTORIA\"},{\"id\":7,\"birthDate\":null,\"name\":\"CARL\"},{\"id\":8,\"birthDate\":null,\"name\":\"STEPHANIE\"}]";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void saveNew() throws Exception {
        String dtoString = "{" +
                "    \"name\": \"name\"" +
                "}";
        ClientDTO dto = new ObjectMapper().readValue(dtoString, ClientDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/v1/clients", dto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Integer createdId = JsonPath.read(responseEntity.getBody(), "$.id");
        String actual = new ObjectMapper().writeValueAsString(new ClientDTO(clientRepository.findById(createdId).get()));
        String expected = "{\"name\":\"name\",\"id\":9,\"birthDate\":null}";
        JSONAssert.assertEquals(expected, actual, false);
    }
}
