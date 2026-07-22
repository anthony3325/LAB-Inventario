package Controlador;
 
public record ResultadoOperacion(boolean exito, String mensaje) {
    public static ResultadoOperacion ok(String mensaje) {
        return new ResultadoOperacion(true, mensaje);
    }
}
