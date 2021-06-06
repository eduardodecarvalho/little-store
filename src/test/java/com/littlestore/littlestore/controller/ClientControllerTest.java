package com.littlestore.littlestore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.littlestore.littlestore.config.SpringBootIntegrationTest;
import com.littlestore.littlestore.domain.dto.ClientDTO;
import com.littlestore.littlestore.repository.ClientRepository;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findById() throws Exception {
        Integer id = 1;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/clients/" + id, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "{\"name\":\"TED\",\"id\":1,\"birthDate\":\"1985-03-26T00:00:00.000+00:00\"}";
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

        String expected = "[" +
                "   {" +
                "      \"id\":5," +
                "      \"birthDate\":\"1982-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"BARNEY\"" +
                "   }," +
                "   {" +
                "      \"id\":7," +
                "      \"birthDate\":\"1980-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"CARL\"" +
                "   }," +
                "   {" +
                "      \"id\":3," +
                "      \"birthDate\":\"1986-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"LILY\"" +
                "   }," +
                "   {" +
                "      \"id\":4," +
                "      \"birthDate\":\"1983-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"MARSHAL\"" +
                "   }," +
                "   {" +
                "      \"id\":2," +
                "      \"birthDate\":\"1989-08-10T00:00:00.000+00:00\"," +
                "      \"name\":\"ROBIN\"" +
                "   }," +
                "   {" +
                "      \"id\":8," +
                "      \"birthDate\":\"1990-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"STEPHANIE\"" +
                "   }," +
                "   {" +
                "      \"id\":1," +
                "      \"birthDate\":\"1985-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"TED\"" +
                "   }," +
                "   {" +
                "      \"id\":6," +
                "      \"birthDate\":\"1988-03-26T00:00:00.000+00:00\"," +
                "      \"name\":\"VICTORIA\"" +
                "   }" +
                "]";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), true);
    }

    @Test
    public void editName() throws Exception {
        Integer id = 1;
        String dtoString = "{" +
                "    \"birthDate\":\"1990-03-26\", " +
                "    \"name\": \"Ted Mosby\"" +
                "}";
        ClientDTO dto = new ObjectMapper().readValue(dtoString, ClientDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/clients/" + 1, HttpMethod.PUT, new HttpEntity<>(dto),
                String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String actual = new ObjectMapper().writeValueAsString(new ClientDTO(clientRepository.findById(id).get()));
        String expected = "{\"id\":1,\"birthDate\":638323200000,\"name\":\"Ted Mosby\"}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void saveNew() throws Exception {
        String dtoString = "{" +
                "    \"birthDate\":\"1988-03-26\", " +
                "    \"name\": \"name\"" +
                "}";
        ClientDTO dto = new ObjectMapper().readValue(dtoString, ClientDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/v1/clients", dto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Integer createdId = JsonPath.read(responseEntity.getBody(), "$.id");
        String actual = new ObjectMapper().writeValueAsString(new ClientDTO(clientRepository.findById(createdId).get()));
        String expected = "{\"id\":9,\"birthDate\":575251200000,\"name\":\"name\"}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void deleteWithInvalidId() throws Exception {
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("http://localhost:" + port + "/v1/clients/" + 99, HttpMethod.DELETE, null,
                String.class).getStatusCode());
    }

    @Test
    public void deleteById() throws Exception {
        Integer id = 1;
        final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/clients/" + id, HttpMethod.DELETE, null,
                String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Integer deleted = jdbcTemplate.queryForObject("SELECT DELETED FROM CLIENT WHERE id = " + id, Integer.class);
        Assert.assertEquals(id, deleted, 0);
    }
}
