package Vista;

import Controlador.MantenimientoService;
import Controlador.ResultadoOperacion;
import Excepciones.ActivoNoEncontradoException;
import Excepciones.OperacionNoPermitidaException;
import Excepciones.PersistenciaException;
import Modelo.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class VistaInventario {

    private final MantenimientoService servicio;
    private final Scanner scanner;

    public VistaInventario(MantenimientoService servicio) {
        this.servicio = servicio;
        this.scanner = new Scanner(System.in);
    }

    public void menu() {
        int opcion;
        do {
            System.out.println("\n===== Lab-Inventario =====");
            System.out.println("1. Registrar Hardware");
            System.out.println("2. Registrar Periferico");
            System.out.println("3. Registrar Licencia");
            System.out.println("4. Listar todos los activos");
            System.out.println("5. Asignar activo a un responsable");
            System.out.println("6. Liberar activo");
            System.out.println("7. Eliminar activo");
            System.out.println("8. Calcular costo total de mantenimiento");
            System.out.println("9. Reporte por tipo");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 ->
                        registrarHardware();
                    case 2 ->
                        registrarPeriferico();
                    case 3 ->
                        registrarLicencia();
                    case 4 ->
                        listarActivos();
                    case 5 ->
                        asignarActivo();
                    case 6 ->
                        liberarActivo();
                    case 7 ->
                        eliminarActivo();
                    case 8 ->
                        calcularCostoTotal();
                    case 9 ->
                        reportePorTipo();
                    case 0 ->
                        System.out.println("Hasta luego.");
                    default ->
                        System.out.println("Opcion invalida.");
                }
            } catch (IllegalArgumentException | ActivoNoEncontradoException
                    | OperacionNoPermitidaException | PersistenciaException e) {
                System.out.println("No se pudo completar la operacion: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void registrarHardware() {
        String nombre = leerTexto("Nombre: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        String fecha = leerTexto("Fecha adquisicion (dd/MM/yyyy): ");
        BigDecimal costo = leerMonto("Costo base: ");
        String estado = leerTexto("Estado (Operativo/Dañado/Baja): ");
        String specs = leerTexto("Especificaciones (RAM, CPU, etc.): ");

        ResultadoOperacion r = servicio.registrarActivo(
                new Hardware(nombre, marca, modelo, fecha, costo, estado, specs));
        System.out.println(r.mensaje());
    }

    private void registrarPeriferico() {
        String nombre = leerTexto("Nombre: ");
        String marca = leerTexto("Marca: ");
        String modelo = leerTexto("Modelo: ");
        String fecha = leerTexto("Fecha adquisicion (dd/MM/yyyy): ");
        BigDecimal costo = leerMonto("Costo base: ");
        String estado = leerTexto("Estado (Operativo/Dañado/Baja): ");
        String tipoP = leerTexto("Tipo de periferico (Mouse/Teclado/Monitor): ");

        ResultadoOperacion r = servicio.registrarActivo(
                new Periferico(nombre, marca, modelo, fecha, costo, estado, tipoP));
        System.out.println(r.mensaje());
    }

    private void registrarLicencia() {
        String nombre = leerTexto("Nombre: ");
        String marca = leerTexto("Marca (Editor/Proveedor): ");
        String modelo = leerTexto("Version: ");
        String fecha = leerTexto("Fecha adquisicion (dd/MM/yyyy): ");
        BigDecimal costo = leerMonto("Costo base: ");
        String estado = leerTexto("Estado (Activa/Vencida): ");
        String expira = leerTexto("Fecha de expiracion (dd/MM/yyyy): ");
        int puestos = leerEntero("Numero de puestos: ");

        ResultadoOperacion r = servicio.registrarActivo(
                new Licencia(nombre, marca, modelo, fecha, costo, estado, expira, puestos));
        System.out.println(r.mensaje());
    }

    private void listarActivos() {
        List<Activo> lista = servicio.listarActivos();
        System.out.println("\n--- Activos registrados ---");
        if (lista.isEmpty()) {
            System.out.println("No hay activos registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void asignarActivo() {
        listarActivos();
        int id = leerEntero("ID del activo a asignar: ");
        String responsable = leerTexto("Nombre del responsable: ");
        ResultadoOperacion r = servicio.asignarActivo(id, responsable);
        System.out.println(r.mensaje());
    }

    private void liberarActivo() {
        listarActivos();
        int id = leerEntero("ID del activo a liberar: ");
        ResultadoOperacion r = servicio.liberarActivo(id);
        System.out.println(r.mensaje());
    }

    private void eliminarActivo() {
        listarActivos();
        int id = leerEntero("ID del activo a eliminar: ");
        if (!confirmar("¿Seguro que deseas eliminar el activo " + id + "?")) {
            System.out.println("Operacion cancelada.");
            return;
        }
        ResultadoOperacion r = servicio.eliminarActivo(id);
        System.out.println(r.mensaje());
    }

    private void calcularCostoTotal() {
        BigDecimal total = servicio.calcularCostoTotalMantenimiento();
        System.out.println("\nCosto total estimado de mantenimiento preventivo: $" + total);
    }

    private void reportePorTipo() {
        String tipo = leerTexto("Tipo (Hardware/Periferico/Licencia): ");
        List<Activo> lista = servicio.generarReportePorTipo(tipo);
        System.out.println("\n--- Reporte: " + tipo + " ---");
        if (lista.isEmpty()) {
            System.out.println("No hay activos de ese tipo.");
        } else {
            lista.forEach(a -> System.out.println(a + " | Costo mant.: $" + a.calcularCostoMantenimiento()));
        }
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un numero entero valido.");
            }
        }
    }

    private BigDecimal leerMonto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                BigDecimal valor = new BigDecimal(entrada);
                if (valor.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("El monto debe ser mayor a 0.");
                    continue;
                }
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un monto valido (ejemplo: 1500.50).");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (S/N): ");
        String r = scanner.nextLine().trim();
        return r.equalsIgnoreCase("S");
    }
}
