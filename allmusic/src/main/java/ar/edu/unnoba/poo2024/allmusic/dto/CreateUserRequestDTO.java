package ar.edu.unnoba.poo2024.allmusic.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    private String username;
    private String password;
}
