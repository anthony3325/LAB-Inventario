package Controlador;

import Excepciones.PersistenciaException;
import Modelo.*;
import Util.ConexionesSQLite;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActivoDAO implements IActivoRepository {

    @Override
    public void guardar(Activo activo) {
        String sql = "INSERT INTO activo (tipo, nombre, marca, modelo, fechaAdquisicion, "
                + "costoBase, estado, especificaciones, tipoPeriferico, fechaExpiracion, "
                + "numeroPuestos, responsableAsignado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, activo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al registrar el activo: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Activo activo) {
        String sql = "UPDATE activo SET tipo=?, nombre=?, marca=?, modelo=?, fechaAdquisicion=?, "
                + "costoBase=?, estado=?, especificaciones=?, tipoPeriferico=?, fechaExpiracion=?, "
                + "numeroPuestos=?, responsableAsignado=? WHERE id=?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            cargarParametros(pstmt, activo);
            pstmt.setInt(13, activo.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al actualizar el activo: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM activo WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Error al eliminar el activo: " + e.getMessage(), e);
        }
    }

    @Override
    public Activo buscarPorId(int id) {
        String sql = "SELECT * FROM activo WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? mapearActivo(rs) : null;
        } catch (SQLException e) {
            throw new PersistenciaException("Error al buscar el activo: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Activo> listarTodos() {
        List<Activo> lista = new ArrayList<>();
        String sql = "SELECT * FROM activo";
        try (Connection conn = ConexionSQLite.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapearActivo(rs));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Error al listar los activos: " + e.getMessage(), e);
        }
        return lista;
    }

    private void cargarParametros(PreparedStatement pstmt, Activo activo) throws SQLException {
        pstmt.setString(1, activo.getTipo());
        pstmt.setString(2, activo.getNombre());
        pstmt.setString(3, activo.getMarca());
        pstmt.setString(4, activo.getModelo());
        pstmt.setString(5, activo.getFechaAdquisicion());
        pstmt.setDouble(6, activo.getCostoBase().doubleValue());
        pstmt.setString(7, activo.getEstado());

        if (activo instanceof Hardware hw) {
            pstmt.setString(8, hw.getEspecificaciones());
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setNull(10, Types.VARCHAR);
            pstmt.setNull(11, Types.INTEGER);
            pstmt.setString(12, hw.getResponsable());
        } else if (activo instanceof Periferico pe) {
            pstmt.setNull(8, Types.VARCHAR);
            pstmt.setString(9, pe.getTipoPeriferico());
            pstmt.setNull(10, Types.VARCHAR);
            pstmt.setNull(11, Types.INTEGER);
            pstmt.setString(12, pe.getResponsable());
        } else if (activo instanceof Licencia li) {
            pstmt.setNull(8, Types.VARCHAR);
            pstmt.setNull(9, Types.VARCHAR);
            pstmt.setString(10, li.getFechaExpiracion());
            pstmt.setInt(11, li.getNumeroPuestos());
            pstmt.setNull(12, Types.VARCHAR);
        }
    }

    private Activo mapearActivo(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        BigDecimal costo = BigDecimal.valueOf(rs.getDouble("costoBase"));
        Activo activo;

        switch (tipo) {
            case "Hardware" -> {
                Hardware hw = new Hardware(
                        rs.getString("nombre"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getString("fechaAdquisicion"), costo,
                        rs.getString("estado"), rs.getString("especificaciones"));
                hw.setResponsableAsignado(rs.getString("responsableAsignado"));
                activo = hw;
            }
            case "Periferico" -> {
                Periferico pe = new Periferico(
                        rs.getString("nombre"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getString("fechaAdquisicion"), costo,
                        rs.getString("estado"), rs.getString("tipoPeriferico"));
                pe.setResponsableAsignado(rs.getString("responsableAsignado"));
                activo = pe;
            }
            case "Licencia" ->
                activo = new Licencia(
                        rs.getString("nombre"), rs.getString("marca"), rs.getString("modelo"),
                        rs.getString("fechaAdquisicion"), costo,
                        rs.getString("estado"), rs.getString("fechaExpiracion"), rs.getInt("numeroPuestos"));
            default ->
                throw new SQLException("Tipo de activo desconocido en la base de datos: " + tipo);
        }
        activo.setId(rs.getInt("id"));
        return activo;
    }
}
