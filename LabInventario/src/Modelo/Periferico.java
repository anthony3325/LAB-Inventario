/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author ashle
 */
public class Periferico extends Activo implements IAsignable {
 
    private static final BigDecimal PORCENTAJE_MANTENIMIENTO = new BigDecimal("0.05");
 
    private String tipoPeriferico;
    private String responsableAsignado;
 
    public Periferico(String nombre, String marca, String modelo, String fechaAdquisicion,
                       BigDecimal costoBase, String estado, String tipoPeriferico) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        Validador.validarTexto(tipoPeriferico, "tipo de periferico");
        this.tipoPeriferico = tipoPeriferico;
        this.responsableAsignado = null;
    }
 
    public String getTipoPeriferico() { return tipoPeriferico; }
 
    public void setResponsableAsignado(String responsableAsignado) { this.responsableAsignado = responsableAsignado; }
 
    @Override
    public String getTipo() { return "Periferico"; }
 
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