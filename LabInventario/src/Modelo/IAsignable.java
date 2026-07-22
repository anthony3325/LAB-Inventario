package Modelo;

public interface IAsignable {
    void asignar(String responsable);
    void liberar();
    boolean estaAsignado();
    String getResponsable();
}
