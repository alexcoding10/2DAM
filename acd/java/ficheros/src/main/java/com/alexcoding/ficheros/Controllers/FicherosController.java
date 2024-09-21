package com.alexcoding.ficheros.Controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alexcoding.ficheros.Entities.Jugador;
import com.alexcoding.ficheros.Services.FicheroServicio;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class FicherosController {
    @Autowired
    FicheroServicio fs;

    @GetMapping("/holaMundo")
    @ResponseBody    
    public String holaMundo(){
        return "Hola mundo";
    }

    @GetMapping("getJugadores")
    @ResponseBody
    public List<Jugador> getJugadores() {
        return fs.obtenerJugadores();
    }
    

}
