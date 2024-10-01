package procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Procesos {
    
    //Ejercico 1
    public void runPingGoogle() {
        String comando = "powershell ping -n 3 google.com";
        ProcessBuilder builder = new ProcessBuilder(comando.split(" "));

        try {
            // ejecutar el comando
            Process out = builder.start();
            // leer el resultado en la terminal
            BufferedReader bf = new BufferedReader(new InputStreamReader(out.getInputStream()));
            String linea;
            while ((linea = bf.readLine()) != null) {
                System.out.println(linea);
            }
            int exitValue = out.waitFor();
            System.out.println("Proceso terminado con el codigo: " + exitValue);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Ejercico 2
    public void runVideoNavegador(String ruta){
        // comando 
        String comando = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe " + ruta;
        ProcessBuilder pb = new ProcessBuilder(comando.split(" "));
        try {
            // abrir el navegador
            pb.start();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
