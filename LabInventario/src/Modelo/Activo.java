/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.math.BigDecimal;
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
    protected BigDecimal costoBase;
    protected String estado;
 
    public Activo(String nombre, String marca, String modelo,
                   String fechaAdquisicion, BigDecimal costoBase, String estado) {
        Validador.validarTexto(nombre, "nombre");
        Validador.validarTexto(marca, "marca");
        Validador.validarTexto(modelo, "modelo");
        Validador.validarFecha(fechaAdquisicion, "fecha de adquisicion");
        Validador.validarMonto(costoBase, "costo base");
        Validador.validarTexto(estado, "estado");
 
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
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getFechaAdquisicion() { return fechaAdquisicion; }
    public BigDecimal getCostoBase() { return costoBase; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
 
    public abstract String getTipo();
 
    @Override
    public abstract BigDecimal calcularCostoMantenimiento();
 
    @Override
    public String toString() {
        return "ID: " + id + " | " + getTipo() + " | " + nombre + " (" + marca + " " + modelo +
                ") | Estado: " + estado + " | Costo base: $" + costoBase;
    }
}
