package com.alexcoding.rutas.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * Esta clase respresenta todos los servicios que puede hacer los fichero.
 * 
 * @author Alexis.
 */

@Service
public class FicheroService {

    /**
     * Este metodo se va a encargar en convertir route en un fichero
     * y comprobar si existe o no esta ruta en nuestro servidor
     * dependiendo del resultado se enviara a un metodo u otro
     * que gestionara lo necesario para dar una respuesta.
     * 
     * @param route ruta a la que quieres acceder.
     * 
     * @return devuelve una clase Response creada por nosotros para devolver una
     *         respuesta.
     */
    public Response getResponseFicheroByRoute(String route) {

        File fichero = new File(route);
        return fichero.exists() ? fileExit(fichero) : fileNotExit(fichero);
    }

    /**
     * Este metodo se ejecurara cuando el fichero no exista
     * en este caso se debe crear todas los directorios que no existan
     * y si la ultima palabra tiene .txt asumismos que es un archivo y se crea el
     * archivo
     * 
     * @param fichero -> fichero que se obtiene de la ruta.
     * @return devuelve una clase Response creada por nosotros para devolver una
     *         respuesta.
     */
    private Response fileNotExit(File fichero) {
        String[] rutas = getAllRoutesOfFile(fichero);
        // Inicializamos un objeto File con la raíz del sistema de archivos
        File archivoActual = new File("/");
        try {
            for (String ruta : rutas) {
                archivoActual = new File(archivoActual, ruta); // Construimos la ruta completa del directorio actual

                // Si el directorio no existe y no es el último elemento de la ruta (es decir,
                // no es el archivo)
                if (!archivoActual.exists() && !ruta.equals(rutas[rutas.length - 1])) {
                    if (!archivoActual.mkdirs()) { // Intentamos crear el directorio y sus subdirectorios
                        return new ResponseMsg(Status.fail,"No se pudo crear el directorio: " + archivoActual.getAbsolutePath());
                    }
                }
            }

            // Crear el archivo si no existe
            if (!archivoActual.createNewFile()) {
                return new ResponseMsg(Status.fail,"No se pudo crear el archivo: " + archivoActual.getAbsolutePath());
            } else {
                return new ResponseMsg(Status.ok,"Archivo creado en: " + archivoActual.getAbsolutePath());

            }
        } catch (IOException e) {
            return new ResponseMsg(Status.fail,"Error de E/S al crear el archivo: " + e.getMessage());

        }
    }

    /**
     * Metodo para obtener un array de todas las carpetas de la ruta en caso de que  haga falta,
     * se elimina el directorio root del sistema para evitar problemas.
     * @param fichero
     * @return
     */
    private String[] getAllRoutesOfFile(File fichero){
        // Obtengo la ruta con '/' para evitar problemas
        String rutaAbsoluta = fichero.getAbsolutePath().replace('\\', '/');
        // Dividimos la ruta en un arreglo de subrutas (directorios)
        String[] rutas = rutaAbsoluta.split("/");
        // Crear un nuevo array a partir del segundo directorio y que este es el root en adelante
        String[] newRutas = Arrays.copyOfRange(rutas, 1, rutas.length);
        return newRutas;
    }

    /**
     * Este metodo debe de comprobar si el archivo es un FOLDER or FIlE,
     * dependiendo de la respuesta se llamara al servicio de cada tipo de archivo
     * para obtener el resultado esperado.
     * En este caso si es un Folder -> se mostrara su contenido de la carpeta
     * En caso de FILE -> Se comprobara si es Binario o no.
     * 
     * @param fichero -> fichero que se obtiene de la ruta.
     * @return devuelve una clase Response creada por nosotros para devolver una
     *         respuesta.
     */
    private Response fileExit(File fichero) {

        boolean isDir = fichero.isDirectory();

        if (isDir) {

            return readContentFolder(fichero);
        }

        return readContentFile(fichero);
    }

    /**
     * Lee el contendio de un fichero en caso de que sea
     * un archivo.
     * 
     * @param fichero -> fichero que se obtiene de la ruta.
     * @return devuelve una clase Response creada por nosotros para devolver una
     *         respuesta.
     */
    private Response readContentFile(File fichero) {

        return new ResponseFile(Status.ok, getContentFile(fichero, true));
    }

    /**
     * Este metodo va a comprobar si un file es binario o no
     * si es binario va a crear una carpta tmp con la copia de ese archivo
     * y si no lo es devuelve el contenido.
     * 
     * @param file String que hace referencia al contenido del archivo
     * @param copy
     * @return String con el contenido
     */
    private String getContentFile(File file, boolean copy) {
        try {
            /*
             * Debo ver si el archivo es binario o no.
             * si es binario devuelve el contenido y se crea una carpeta tmp con la copia de
             * este archivo.
             */
            return isBinario(file) ? getContentBin(file, copy) : Files.readString(Paths.get(file.getAbsolutePath()));

        } catch (IOException e) {
            e.printStackTrace();
            return "El contenido del archivo '" + file.getName() + "' esta vacio o no puede ser leido.";
        }
    }

    /**
     * Comprueba si un File es binario o no
     * 
     * @param file
     * @return Boolean
     */
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

    /**
     * Funcion para obtener el contenido de un archivo binario,
     * ademas crea una carpeta tmp y copia esos archivos en esa carpta
     * 
     * @param fichero
     * @param copy
     * @return
     */
    private String getContentBin(File fichero, boolean copy) {
        /*
         * Todo llamar a una funcion que cree una carpeta temporal en el proyecto con el
         * timestamp en el nombre.
         */
        if (copy) {
            generateTmp(fichero);
        }

        try (FileInputStream inputStream = new FileInputStream(fichero)) {
            byte[] buffer = new byte[(int) fichero.length()];
            inputStream.read(buffer);
            return Base64.getEncoder().encodeToString(buffer);

        } catch (Exception e) {
            e.printStackTrace();
            return "Fallo al leer el archivo binario";
        }

    }

    /**
     * Es metodo lee el contenido de una carpeta
     * 
     * @param fichero
     * @return Response
     */
    private Response readContentFolder(File fichero) {
        // necesito el contenido del Folder que es un array de Folders
        List<Fichero> data = getContentFolder(fichero);

        if (data == null) {
            return new ResponseMsg(Status.ok, "La Carpeta " + fichero.getName() + " esta vacia.");
        }

        return new ResponseFolder(Status.ok, data);
    }

    /**
     * Genera la carpeta tmp y copia a esta el file
     * que se le pasa por parametros.
     * 
     * @param file
     */
    private void generateTmp(File file) {
        Path tempDirectory = Paths.get(System.getProperty("user.dir"), ".tmp");
        try {
            Files.createDirectories(tempDirectory);
            // Generar un nombre único utilizando un timestamp
            String newFileName = generateUniqueDateTimeString() + "-" + file.getName();
            // creo el pad de destino
            Path destination = tempDirectory.resolve(newFileName);

            /*
             * la opcion StandardCopyOption.REPLACE_EXISTING
             * indica que si el archivo de destino ya existe, se debe sobrescribir con el
             * contenido del archivo de origen
             */
            Files.copy(file.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Genera un String que se devuelve con el timestamp del momento que se ejecuta
     * 
     * @return String
     */
    private String generateUniqueDateTimeString() {
        // Usamos la clase Local para obtener la fecha y el formato
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return now.format(formatter);
    }

    /**
     * Obtener el contenido de la carpeta
     * es decir tener el nombre de todas las carpetas
     * 
     * @param file
     * @return
     */
    private List<Fichero> getContentFolder(File file) {
        // lita de los archivos del file
        File[] archivos = file.listFiles();
        List<Fichero> data = new ArrayList<>();
        if (archivos != null) {
            for (File archivo : archivos) {
                if (archivo.isDirectory()) {
                    // si es una carpeta pude tener cosas dentro
                    List<String> subContent = new ArrayList<>();
                    File[] subContentFile = archivo.listFiles();
                    for (File arch : subContentFile) {
                        subContent.add(arch.getName());
                    }
                    data.add(new Folder(archivo.getName(), subContent));
                } else {
                    data.add(new Mifile(archivo.getName(), getContentFile(archivo, false)));
                }
            }

        }
        return data;
    }

}
