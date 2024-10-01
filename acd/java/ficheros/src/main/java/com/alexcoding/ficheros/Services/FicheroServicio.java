package com.alexcoding.ficheros.Services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.alexcoding.ficheros.Entities.Jugador;

@Service
public class FicheroServicio {
    public List<Jugador> obtenerJugadores(){
        Jugador j1 = new Jugador("pepe",20);
        Jugador j2 = new Jugador("Manuel",12);
        List<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(j1);
        listaJugadores.add(j2);
        return listaJugadores;
    }
}
