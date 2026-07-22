package Modelo;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
 

public final class Validador {
 
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
    private Validador() {
    }
 
    public static void validarTexto(String valor, String nombreCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' no puede estar vacio.");
        }
    }
 
    public static void validarMonto(BigDecimal valor, String nombreCampo) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser mayor a 0.");
        }
    }
 
    public static void validarEnteroPositivo(int valor, String nombreCampo) {
        if (valor <= 0) {
            throw new IllegalArgumentException("El campo '" + nombreCampo + "' debe ser mayor a 0.");
        }
    }
 
    public static void validarFecha(String valor, String nombreCampo) {
        validarTexto(valor, nombreCampo);
        try {
            LocalDate.parse(valor, FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "El campo '" + nombreCampo + "' debe tener formato dd/MM/yyyy.");
        }
    }
}