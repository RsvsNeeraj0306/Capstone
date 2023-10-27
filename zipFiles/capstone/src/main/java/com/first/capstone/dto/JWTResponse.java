package com.first.capstone.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTResponse {
    private Long id;
    private String token;
    private String username;

    public JWTResponse(Long i,String accessToken,String username) {
        this.id = i;
        this.token = accessToken;
        this.username = username;
      }
}