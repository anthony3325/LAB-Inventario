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
public class Licencia extends Activo {
 
    private static final BigDecimal PORCENTAJE_RENOVACION = new BigDecimal("0.02");
 
    private String fechaExpiracion;
    private int numeroPuestos;
 
    public Licencia(String nombre, String marca, String modelo, String fechaAdquisicion,
                     BigDecimal costoBase, String estado, String fechaExpiracion, int numeroPuestos) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        Validador.validarFecha(fechaExpiracion, "fecha de expiracion");
        Validador.validarEnteroPositivo(numeroPuestos, "numero de puestos");
        this.fechaExpiracion = fechaExpiracion;
        this.numeroPuestos = numeroPuestos;
    }
 
    public String getFechaExpiracion() { return fechaExpiracion; }
    public int getNumeroPuestos() { return numeroPuestos; }
 
    @Override
    public String getTipo() { return "Licencia"; }
 
    @Override
    public BigDecimal calcularCostoMantenimiento() {
        return costoBase.multiply(PORCENTAJE_RENOVACION)
                .multiply(BigDecimal.valueOf(numeroPuestos))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
