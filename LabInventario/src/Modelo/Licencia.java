/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ashle
 */
public class Licencia extends Activo {
 
    private String fechaExpiracion;
    private int numeroPuestos;
 
    public Licencia(String nombre, String marca, String modelo, String fechaAdquisicion,
                     double costoBase, String estado, String fechaExpiracion, int numeroPuestos) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        this.fechaExpiracion = fechaExpiracion;
        this.numeroPuestos = numeroPuestos;
    }
 
    public String getFechaExpiracion() { return fechaExpiracion; }
    public void setFechaExpiracion(String fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; }
 
    public int getNumeroPuestos() { return numeroPuestos; }
    public void setNumeroPuestos(int numeroPuestos) { this.numeroPuestos = numeroPuestos; }
 
    @Override
    public String getTipo() { return "Licencia"; }
 
    @Override
    public double calcularCostoMantenimiento() {
        return costoBase * 0.02 * numeroPuestos;
    }
}
