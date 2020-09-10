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
import com.jayway.jsonpath.JsonPath;
import com.littlestore.littlestore.config.SpringBootIntegrationTest;
import com.littlestore.littlestore.domain.dto.ProductDTO;
import com.littlestore.littlestore.repository.ProductRepository;

@ActiveProfiles(profiles = "test")
public class ProductControllerTest extends SpringBootIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void findById() throws Exception {
        Integer id = 1;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/products/" + id, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "{}";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void findByInvalidId_shouldReturnError() throws Exception {
        Integer id = 99;
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.getForEntity("http://localhost:" + port + "/v1/products/" + id, String.class).getStatusCode());
    }

    @Test
    public void findAll() throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/v1/products", String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String expected = "[" +
                "   {" +
                "      \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell" +
                " Mobile Connect pairs your iOS or Android smartphone with your laptop.\"," +
                "      \"id\":1," +
                "      \"quantity\":70," +
                "      \"sku\":\"DELLNOTEI5\"," +
                "      \"price\":300," +
                "      \"name\":\"Inspiron 15 3000 Laptop\"" +
                "   }," +
                "   {" +
                "      \"description\":\"With a resolution of 2560-by-1600 for over 4 million pixels, " +
                "the results are positively jaw dropping. Images take on a new level of detail and realism. " +
                "Text is sharp and clear. And True Tone technology automatically adjusts the white point of the display to match the color temperature" +
                " of your environment — making web pages and email look as natural as the printed page. With millions of colors, everything you see is rich" +
                " and vibrant.\"," +
                "      \"id\":2," +
                "      \"quantity\":60," +
                "      \"sku\":\"APPLENOTEI5\"," +
                "      \"price\":900," +
                "      \"name\":\"MacBook Air\"" +
                "   }" +
                "]";
        JSONAssert.assertEquals(expected, responseEntity.getBody(), false);
    }

    @Test
    public void editName() throws Exception {
        Integer id = 1;
        String dtoString = "{" +
                "   \"name\":\"Inspiron 15 3000 Notebook\"," +
                "   \"id\":1," +
                "   \"sku\":\"APPLENOTEI5\"," +
                "   \"description\":\"Seamless PC/smartphone integration: Access multiple devices without dividing your attention—Dell Mobile Connect pairs your iOS or Android smartphone with your laptop.\"," +
                "   \"quantity\":70," +
                "   \"price\":300" +
                "}";
        ProductDTO dto = new ObjectMapper().readValue(dtoString, ProductDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/products/" + 1, HttpMethod.PUT, new HttpEntity<>(dto),
                String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        String actual = new ObjectMapper().writeValueAsString(new ProductDTO(productRepository.findById(id).get()));
        String expected = "{}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void saveNew() throws Exception {
        String dtoString = "{" +
                "   \"name\":\"Swift 7\"," +
                "   \"sku\":\"SAMSUNGNOTESWIFT7\"," +
                "   \"description\":\"Intel Core™ i7 (i7 - 7Y75, 1.30 GHz, 4 MB) - 14 LED - 16:9 Full HD - LCD - CineCrystal - " +
                "Intel® HD Graphics 615 - 8 GB LPDDR3 - No - Weight (Approximate) 2.65 lb - Maximum Battery Run Time 10 Hour" +
                "\"," +
                "   \"quantity\":50," +
                "   \"price\":1600" +
                "}";
        ;
        ProductDTO dto = new ObjectMapper().readValue(dtoString, ProductDTO.class);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/v1/products", dto, String.class);
        Assert.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        Integer createdId = JsonPath.read(responseEntity.getBody(), "$.id");
        String actual = new ObjectMapper().writeValueAsString(new ProductDTO(productRepository.findById(createdId).get()));
        String expected = "{}";
        JSONAssert.assertEquals(expected, actual, false);
    }

    @Test
    public void deleteWithInvalidId() throws Exception {
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("http://localhost:" + port + "/v1/products/" + 99, HttpMethod.DELETE, null,
                String.class).getStatusCode());
    }

    @Test
    public void deleteById() throws Exception {
        Integer id = 1;
        final ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/v1/products/" + id, HttpMethod.DELETE, null,
                String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Integer deleted = jdbcTemplate.queryForObject("SELECT DELETED FROM PRODUCT WHERE id = " + id, Integer.class);
        Assert.assertEquals(id, deleted, 0);
    }

    @Test
    public void deleteProductWithQuantity_shouldReturnError() throws Exception {
        assertEquals(HttpStatus.BAD_REQUEST, restTemplate.exchange("http://localhost:" + port + "/v1/products/" + 2, HttpMethod.DELETE, null,
                String.class).getStatusCode());
    }
}
