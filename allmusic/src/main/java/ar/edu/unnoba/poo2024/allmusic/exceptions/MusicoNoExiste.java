package ar.edu.unnoba.poo2024.allmusic.exceptions;

public class MusicoNoExiste extends Exception{
    
    public String getMessage(){
        return "El músico no fue encontrado";
    }
    
}
