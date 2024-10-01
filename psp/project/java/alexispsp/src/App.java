import procesos.Procesos;

public class App {
    public static void main(String[] args) throws Exception {
        Procesos procesos = new Procesos();
        // procesos
        procesos.runPingGoogle();
        procesos.runVideoNavegador("www.youtube.com/watch?v=6kxZrVGf_iE");
    }
}
