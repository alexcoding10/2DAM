package com.alexcoding.ficheros.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alexcoding.ficheros.Entities.Jugador;
import com.alexcoding.ficheros.Services.FicheroServicio;



@Controller
public class FicherosController {
    @Autowired
    FicheroServicio fs = new FicheroServicio();

    @GetMapping("holaMundo")
    @ResponseBody    
    public String holaMundo(){
        return "";
    }

    @GetMapping("getJugadores")
    public List<Jugador> getJugadores() {
        return fs.obtenerJugadores();
    }
    
}
