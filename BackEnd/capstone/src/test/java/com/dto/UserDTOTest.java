package com.dto;

import org.junit.jupiter.api.Test;

import com.first.capstone.dto.userDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
     void testUserDTOGettersAndSetters() {
        userDTO userDTO = new userDTO();
        Long id = 1L;
        String name = "John Doe";

        // Act
        userDTO.setId(id);
        userDTO.setName(name);

        // Assert
        assertEquals(id, userDTO.getId());
        assertEquals(name, userDTO.getName());
    }
}
