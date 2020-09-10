package com.littlestore.littlestore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.littlestore.littlestore.config.SpringBootIntegrationTest;
import com.littlestore.littlestore.domain.dto.OrderDTO;
import com.littlestore.littlestore.repository.OrderRepository;

@ActiveProfiles(profiles = "test")
public class OrderControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findById() throws Exception {
        Integer id = 1;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/orders/" + id, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "{" +
                "   \"id\":1," +
                "   \"client\":{" +
                "      \"id\":1" +
                "   }," +
                "   \"orderedItems\":[" +
                "      {" +
                "         \"id\":1," +
                "         \"product\":{" +
                "            \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\"," +
                "            \"id\":1," +
                "            \"quantity\":60," +
                "            \"sku\":\"DELLNOTEI5\"," +
                "            \"price\":300," +
                "            \"name\":\"Inspiron 15 3000 Laptop\"" +
                "         }," +
                "         \"totalPrice\":20700," +
                "         \"quantity\":69" +
                "      }" +
                "   ]," +
                "   \"purchaseValue\":3000" +
                "}";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void findByInvalidId_shouldReturnError() throws Exception {
        Integer id = 99;
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.getForEntity("http://localhost:" + port + "/v1/orders/" + id, String.class).getStatusCode());
    }

    @Test
    public void findAll() throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/orders", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "[\n" +
                "   {\n" +
                "      \"id\":1,\n" +
                "      \"client\":{\n" +
                "         \"id\":1\n" +
                "      },\n" +
                "      \"orderedItems\":[\n" +
                "         {\n" +
                "            \"id\":1,\n" +
                "            \"product\":{\n" +
                "               \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\",\n" +
                "               \"id\":1,\n" +
                "               \"sku\":\"DELLNOTEI5\",\n" +
                "               \"price\":300,\n" +
                "               \"name\":\"Inspiron 15 3000 Laptop\"\n" +
                "            },\n" +
                "            \"totalPrice\":20700\n" +
                "         }\n" +
                "      ]\n" +
                "   },\n" +
                "   {\n" +
                "      \"id\":2,\n" +
                "      \"client\":{\n" +
                "         \"id\":2,\n" +
                "         \"name\":\"ROBIN\"\n" +
                "      },\n" +
                "      \"orderedItems\":[\n" +
                "         {\n" +
                "            \"id\":2,\n" +
                "            \"product\":{\n" +
                "               \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\",\n" +
                "               \"id\":1,\n" +
                "               \"sku\":\"DELLNOTEI5\",\n" +
                "               \"price\":300,\n" +
                "               \"name\":\"Inspiron 15 3000 Laptop\"\n" +
                "            },\n" +
                "            \"totalPrice\":300,\n" +
                "            \"quantity\":1\n" +
                "         },\n" +
                "         {\n" +
                "            \"id\":3,\n" +
                "            \"product\":{\n" +
                "               \"description\":\"With a resolution of 2560-by-1600 for over 4 million pixels, the results are positively jaw dropping. Images take on a new level of detail and realism. Text is sharp and clear. And True Tone technology automatically adjusts the white point of the display to match the color temperature of your environment — making web pages and email look as natural as the printed page. With millions of colors, everything you see is rich and vibrant.\",\n" +
                "               \"id\":2,\n" +
                "               \"sku\":\"APPLENOTEI5\",\n" +
                "               \"price\":900,\n" +
                "               \"name\":\"MacBook Air\"\n" +
                "            },\n" +
                "            \"totalPrice\":1800,\n" +
                "            \"quantity\":2\n" +
                "         }\n" +
                "      ]\n" +
                "   }\n" +
                "]";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void editOrder() throws Exception {
        Integer id = 1;
        String dtoString = "{" +
                "   \"id\":1," +
                "   \"client\":{" +
                "      \"name\":\"TED\"," +
                "      \"id\":1," +
                "      \"birthDate\":\"1985-03-26T00:00:00.000+00:00\"" +
                "   }," +
                "   \"orderedItems\":[" +
                "      {" +
                "         \"product\":{" +
                "            \"name\":\"Inspiron 15 3000 Laptop\"," +
                "            \"id\":1," +
                "            \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\"," +
                "            \"quantity\":70," +
                "            \"sku\":\"DELLNOTEI5\"," +
                "            \"price\":300" +
                "         }," +
                "         \"id\":1," +
                "         \"quantity\":10" +
                "      }" +
                "   ]" +
                "}";
        OrderDTO dto = new ObjectMapper().readValue(dtoString, OrderDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/orders/" + 1, HttpMethod.PUT, new HttpEntity<>(dto),
                String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String actual = new ObjectMapper().writeValueAsString(new OrderDTO(orderRepository.findById(id).get()));
        String expected = "{" +
                "   \"id\":1," +
                "   \"client\":{" +
                "      \"id\":1" +
                "   }," +
                "   \"purchaseValue\":3000," +
                "   \"orderedItems\":[" +
                "      {" +
                "         \"id\":1," +
                "         \"totalPrice\":20700," +
                "         \"quantity\":69," +
                "         \"product\":{" +
                "            \"name\":\"Inspiron 15 3000 Laptop\"," +
                "            \"id\":1," +
                "            \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\"," +
                "            \"quantity\":60," +
                "            \"price\":300," +
                "            \"sku\":\"DELLNOTEI5\"" +
                "         }" +
                "      }" +
                "   ]" +
                "}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void deleteWithInvalidId() throws Exception {
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("http://localhost:" + port + "/v1/orders/" + 99, HttpMethod.DELETE, null,
                String.class).getStatusCode());
    }

    @Test
    public void deleteById() throws Exception {
        Integer id = 1;
        final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/orders/" + id, HttpMethod.DELETE, null,
                String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Integer deleted = jdbcTemplate.queryForObject("SELECT DELETED FROM CLIENT_ORDER WHERE id = " + id, Integer.class);
        Assert.assertEquals(id, deleted, 0);
    }

}
