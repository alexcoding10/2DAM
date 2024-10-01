package com.alexcoding.rutas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alexcoding.rutas.entities.Response;
import com.alexcoding.rutas.services.FicheroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ControllerFichero {
    
    @Autowired
    public FicheroService fs;

    @GetMapping("ruta")
    @ResponseBody
    public Response getFileRoute(@RequestParam (required = true) String route) {
        return fs.getResponseFicheroByRoute(route);
    }

    

}
