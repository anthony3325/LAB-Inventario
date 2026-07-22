/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ashle
 */
public class Hardware extends Activo implements IAsignable {
 
    private String especificaciones;
    private String responsableAsignado;
 
    public Hardware(String nombre, String marca, String modelo, String fechaAdquisicion,
                     double costoBase, String estado, String especificaciones) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        this.especificaciones = especificaciones;
        this.responsableAsignado = null;
    }
 
    public String getEspecificaciones() { return especificaciones; }
    public void setEspecificaciones(String especificaciones) { this.especificaciones = especificaciones; }
 
    public String getResponsableAsignado() { return responsableAsignado; }
    public void setResponsableAsignado(String responsableAsignado) { this.responsableAsignado = responsableAsignado; }
 
    @Override
    public String getTipo() { return "Hardware"; }
 
    @Override
    public double calcularCostoMantenimiento() {
        return costoBase * 0.10;
    }
 
    @Override
    public void asignar(String responsable) { this.responsableAsignado = responsable; }
 
    @Override
    public void liberar() { this.responsableAsignado = null; }
 
    @Override
    public boolean estaAsignado() {
        return responsableAsignado != null && !responsableAsignado.isEmpty();
    }
}
