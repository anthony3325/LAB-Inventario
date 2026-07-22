package Controlador;

import Excepciones.ActivoNoEncontradoException;
import Excepciones.OperacionNoPermitidaException;
import Modelo.Activo;
import Modelo.IAsignable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MantenimientoService {

    private final IActivoRepository repositorio;

    public MantenimientoService(IActivoRepository repositorio) {
        this.repositorio = repositorio;
    }

    public ResultadoOperacion registrarActivo(Activo activo) {
        repositorio.guardar(activo);
        return ResultadoOperacion.ok("Activo registrado correctamente.");
    }

    public List<Activo> listarActivos() {
        return repositorio.listarTodos();
    }

    public ResultadoOperacion eliminarActivo(int id) {
        obtenerOFallar(id);
        repositorio.eliminar(id);
        return ResultadoOperacion.ok("Activo eliminado correctamente.");
    }

    // LSP: no importa el tipo concreto, todos responden a calcularCostoMantenimiento().
    public BigDecimal calcularCostoTotalMantenimiento() {
        BigDecimal total = BigDecimal.ZERO;
        for (Activo a : repositorio.listarTodos()) {
            total = total.add(a.calcularCostoMantenimiento());
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public ResultadoOperacion asignarActivo(int id, String responsable) {
        Activo activo = obtenerOFallar(id);
        IAsignable asignable = comoAsignableOFallar(activo);

        String anterior = asignable.getResponsable();
        asignable.asignar(responsable);
        repositorio.actualizar(activo);

        if (anterior != null && !anterior.isBlank()) {
            return ResultadoOperacion.ok("Activo reasignado de " + anterior + " a " + responsable + ".");
        }
        return ResultadoOperacion.ok("Activo asignado a " + responsable + ".");
    }

    public ResultadoOperacion liberarActivo(int id) {
        Activo activo = obtenerOFallar(id);
        IAsignable asignable = comoAsignableOFallar(activo);
        asignable.liberar();
        repositorio.actualizar(activo);
        return ResultadoOperacion.ok("Activo liberado correctamente.");
    }

    public List<Activo> generarReportePorTipo(String tipo) {
        String tipoNormalizado = tipo == null ? "" : tipo.trim();
        List<Activo> resultado = new ArrayList<>();
        for (Activo a : repositorio.listarTodos()) {
            if (a.getTipo().equalsIgnoreCase(tipoNormalizado)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    private Activo obtenerOFallar(int id) {
        Activo activo = repositorio.buscarPorId(id);
        if (activo == null) {
            throw new ActivoNoEncontradoException("No existe un activo con ID " + id + ".");
        }
        return activo;
    }

    private IAsignable comoAsignableOFallar(Activo activo) {
        if (activo instanceof IAsignable asignable) {
            return asignable;
        }
        throw new OperacionNoPermitidaException(
                "El tipo de activo '" + activo.getTipo() + "' no admite asignacion.");
    }
}
