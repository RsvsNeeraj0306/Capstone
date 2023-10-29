package com.dto;

import org.junit.jupiter.api.Test;

import com.first.capstone.dto.JWTResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JWTResponseTest {

    @Test
    void testJWTResponseConstructor() {
        // Arrange
        Long id = 1L;
        String token = "exampleToken";
        String username = "exampleUsername";

        // Act
        JWTResponse jwtResponse = new JWTResponse(id, token, username);

        // Assert
        assertEquals(id, jwtResponse.getId());
        assertEquals(token, jwtResponse.getToken());
        assertEquals(username, jwtResponse.getUsername());
    }

    @Test
    void testSetId() {
        JWTResponse jwtResponse = new JWTResponse(null, null, null);
        Long id = 1L;
        jwtResponse.setId(id);
        assertEquals(id, jwtResponse.getId());
    }

    @Test
    void testSetToken() {
        JWTResponse jwtResponse = new JWTResponse(null, null, null);
        String token = "myAccessToken";
        jwtResponse.setToken(token);
        assertEquals(token, jwtResponse.getToken());
    }

    @Test
    void testSetUsername() {
        JWTResponse jwtResponse = new JWTResponse(null, null, null);
        String username = "john.doe";
        jwtResponse.setUsername(username);
        assertEquals(username, jwtResponse.getUsername());
    }
}
