/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author ashle
 */
public abstract class Activo implements IMantenimientoCalculable {
 
    protected int id;
    protected String nombre;
    protected String marca;
    protected String modelo;
    protected String fechaAdquisicion;
    protected double costoBase;
    protected String estado;
 
    public Activo(String nombre, String marca, String modelo,
                   String fechaAdquisicion, double costoBase, String estado) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaAdquisicion = fechaAdquisicion;
        this.costoBase = costoBase;
        this.estado = estado;
    }
 
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
 
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
 
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
 
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
 
    public String getFechaAdquisicion() { return fechaAdquisicion; }
    public void setFechaAdquisicion(String fechaAdquisicion) { this.fechaAdquisicion = fechaAdquisicion; }
 
    public double getCostoBase() { return costoBase; }
    public void setCostoBase(double costoBase) { this.costoBase = costoBase; }
 
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
 
    public abstract String getTipo();
 
   
    @Override
    public abstract double calcularCostoMantenimiento();
 
    @Override
    public String toString() {
        return "ID: " + id + " | " + getTipo() + " | " + nombre + " (" + marca + " " + modelo +
                ") | Estado: " + estado + " | Costo base: $" + costoBase;
    }
}
    

