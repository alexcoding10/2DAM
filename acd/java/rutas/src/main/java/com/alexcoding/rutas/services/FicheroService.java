package com.alexcoding.rutas.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import com.alexcoding.rutas.entities.Fichero;
import com.alexcoding.rutas.entities.Folder;
import com.alexcoding.rutas.entities.Mifile;
import com.alexcoding.rutas.entities.Response;
import com.alexcoding.rutas.entities.ResponseFile;
import com.alexcoding.rutas.entities.ResponseFolder;
import com.alexcoding.rutas.entities.ResponseMsg;


import com.alexcoding.rutas.entities.Response.Status;

@Service
public class FicheroService {

    public Response getResponseFicheroByRoute(String route) {
        /*
         * Este metodo se va a encargar en convertir route en un fichero
         * y comprobar si existe o no esta ruta en nuestro servidor
         * dependiendo del resultado se enviara a un metodo u otro
         * que gestionara lo necesario para dar una respuesta.
         */
        File fichero = new File(route);
        return fichero.exists() ? fileExit(fichero) : fileNotExit(fichero);
    }

    private Response fileNotExit(File fichero) {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fileNotExit'");
    }

    private Response fileExit(File fichero) {
        /*
         * Este metodo debe de comprobar si el archivo es un FOLDER or FIlE,
         * dependiendo de la respuesta se llamara al servicio de cada tipo de archivo
         * para obtener el resultado esperado.
         * En este caso si es un Folder -> se mostrara su contenido de la carpeta
         * En caso de FILE -> Se comprobara si es Binario o no.
         */

        boolean isDir = fichero.isDirectory();

        if(isDir){

           return readContentFolder(fichero);
        }

        return readContentFile(fichero);

    }

    private Response readContentFile(File fichero){

        return new ResponseFile(Status.ok,getContentFile(fichero));
    }

    private String getContentFile(File file){
        // Aqui debo guardar todo el contenido en un String
        try {
            /*
             * Debo ver si el archivo es binario o no.
             * si es binario devuelve el contenido y se crea una carpeta tmp con la copia de este archivo.
             */
           return  isBinario(file) ? getContentBin(file) : Files.readString(Paths.get(file.getAbsolutePath()));
        
        } catch (IOException e) {
            e.printStackTrace();
            return "El contenido del archivo '" + file.getName() + "' esta vacio o no puede ser leido.";
        }
    }

    private Response readContentFolder(File fichero) {
        // necesito el contenido del Folder que es un array de Folders
        List<Fichero> data = getContentFolder(fichero);

        if (data == null) {
            return new ResponseMsg(Status.ok, "La Carpeta " + fichero.getName() + " esta vacia.");
        }

        return new ResponseFolder(Status.ok, data);
    }

    private String getContentBin(File fichero){
        /*
        Todo llamar a una funcion que cree una carpeta temporal en el proyecto con el timestamp en el nombre.
        */
        generateTmp(fichero);

        try (FileInputStream inputStream = new FileInputStream(fichero))
        {
            byte[] buffer = new byte[(int)fichero.length()];
            inputStream.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);

        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al leer el archivo binario";
        }

    }

    private void generateTmp(File file) {
        Path tempDirectory = Paths.get(System.getProperty("user.dir"), ".tmp");
        try {
            Files.createDirectories(tempDirectory);
            // Generar un nombre único utilizando un timestamp
            String newFileName = generateUniqueDateTimeString() + "-" + file.getName();
            // creo el pad de destino
            Path destination = tempDirectory.resolve(newFileName);

            /*
            la opcion StandardCopyOption.REPLACE_EXISTING
            indica que si el archivo de destino ya existe, se debe sobrescribir con el contenido del archivo de origen
            */
            Files.copy(file.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private String generateUniqueDateTimeString() {
        // Usamos la clase Local para obtener la fecha y el formato
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"); 
        return now.format(formatter);
    }

    private List<Fichero> getContentFolder(File file){
        File[] archivos = file.listFiles();
        List<Fichero> data = new ArrayList<>();
        if (archivos != null) {
            for(File archivo : archivos){
                //TODO: controlar si es archs es null 
                if (archivo.isDirectory()) {
                    List<String> subContent  = new ArrayList<>();
                    File[] archs = archivo.listFiles();
                    for (File arch : archs){
                        subContent.add(arch.getName());
                    }
                    data.add(new Folder(archivo.getName(),subContent));
                }else{
                    data.add(new Mifile(archivo.getName(),getContentFile(archivo)));
                }
            }
            
        }else{
            return null;
        }
        return data;
    }

    private boolean isBinario(File file) {
    try (InputStream inputStream = Files.newInputStream(file.toPath())) {
        byte[] buffer = new byte[1024]; // Buffer para leer los bytes del archivo
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) { // Lee el archivo por bloques
            for (int i = 0; i < bytesRead; i++) {
                if (buffer[i] < 0) { // Si encuentra un byte negativo, es probable que sea binario
                    return true;
                }
            }
        }

        // Si no se encontraron bytes negativos, se asume que es un archivo de texto
        return false;
    } catch (IOException e) {
        // Maneja la excepción en caso de que ocurra un error al leer el archivo
        e.printStackTrace();
        return false; // O maneja la excepción de otra manera
    }
}

}
