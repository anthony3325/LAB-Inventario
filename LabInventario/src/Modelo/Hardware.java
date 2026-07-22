package Modelo;
 
import java.math.BigDecimal;
import java.math.RoundingMode;
 
public class Hardware extends Activo implements IAsignable {
 
    private static final BigDecimal PORCENTAJE_MANTENIMIENTO = new BigDecimal("0.10");
 
    private String especificaciones;
    private String responsableAsignado;
 
    public Hardware(String nombre, String marca, String modelo, String fechaAdquisicion,
                     BigDecimal costoBase, String estado, String especificaciones) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        Validador.validarTexto(especificaciones, "especificaciones");
        this.especificaciones = especificaciones;
        this.responsableAsignado = null;
    }
 
    public String getEspecificaciones() { return especificaciones; }
 
    public void setResponsableAsignado(String responsableAsignado) { this.responsableAsignado = responsableAsignado; }
 
    @Override
    public String getTipo() { return "Hardware"; }
 
    @Override
    public BigDecimal calcularCostoMantenimiento() {
        return costoBase.multiply(PORCENTAJE_MANTENIMIENTO).setScale(2, RoundingMode.HALF_UP);
    }
 
    @Override
    public void asignar(String responsable) { this.responsableAsignado = responsable; }
 
    @Override
    public void liberar() { this.responsableAsignado = null; }
 
    @Override
    public boolean estaAsignado() {
        return responsableAsignado != null && !responsableAsignado.isBlank();
    }
 
    @Override
    public String getResponsable() { return responsableAsignado; }
}
