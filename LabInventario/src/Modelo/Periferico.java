/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ashle
 */
public class Periferico extends Activo implements IAsignable {
 
    private String tipoPeriferico; 
    private String responsableAsignado;
 
    public Periferico(String nombre, String marca, String modelo, String fechaAdquisicion,
                       double costoBase, String estado, String tipoPeriferico) {
        super(nombre, marca, modelo, fechaAdquisicion, costoBase, estado);
        this.tipoPeriferico = tipoPeriferico;
        this.responsableAsignado = null;
    }
 
    public String getTipoPeriferico() { return tipoPeriferico; }
    public void setTipoPeriferico(String tipoPeriferico) { this.tipoPeriferico = tipoPeriferico; }
 
    public String getResponsableAsignado() { return responsableAsignado; }
    public void setResponsableAsignado(String responsableAsignado) { this.responsableAsignado = responsableAsignado; }
 
    @Override
    public String getTipo() { return "Periferico"; }
 
    @Override
    public double calcularCostoMantenimiento() {
        return costoBase * 0.05;
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
