package ar.edu.unnoba.poo2024.allmusic.util;

import com.password4j.Password;

public class PasswordEncoder {

    public String encode(String rawPassword) {
        return Password.hash(rawPassword).withBcrypt().getResult();
    }

    public boolean verify(String rawPassword, String encodedPassword) {
        return Password.check(rawPassword, encodedPassword).withBcrypt();
    }

}
