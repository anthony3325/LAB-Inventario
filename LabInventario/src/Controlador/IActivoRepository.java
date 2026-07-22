package Controlador;
 
import Modelo.Activo;
import java.util.List;
 
public interface IActivoRepository {
    void guardar(Activo activo);
    void actualizar(Activo activo);
    void eliminar(int id);
    Activo buscarPorId(int id);
    List<Activo> listarTodos();
}
