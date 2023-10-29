package com.first.capstone.entity;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();
        user.setId(1L);
        user.setName("sampleUser");
        user.setPassword("password123");

        assertEquals(1L, user.getId());
        assertEquals("sampleUser", user.getName());
        assertEquals("password123", user.getPassword());
    }
}
