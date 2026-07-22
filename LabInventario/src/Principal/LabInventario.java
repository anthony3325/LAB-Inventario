package Principal;

import Controlador.ActivoDAO;
import Controlador.IActivoRepository;
import Controlador.MantenimientoService;
import Vista.VistaInventario;

public class LabInventario {

    public static void main(String[] args) {
        IActivoRepository repositorio = new ActivoDAO();
        MantenimientoService servicio = new MantenimientoService(repositorio);
        new VistaInventario(servicio).menu();
    }
}
