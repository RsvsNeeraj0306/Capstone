package com.dto;

import org.junit.jupiter.api.Test;

import com.first.capstone.dto.LoginBody;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class LoginBodyTest {

    @Test
     void testLoginBodyGettersAndSetters() {
        // Arrange
        LoginBody loginBody = new LoginBody();
        String username = "john_doe";
        String password = "secret_password";

        // Act
        loginBody.setUsername(username);
        loginBody.setPassword(password);

        // Assert
        assertEquals(username, loginBody.getUsername());
        assertEquals(password, loginBody.getPassword());
    }
}
