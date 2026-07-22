
package Util;
 
import Excepciones.PersistenciaException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class ConexionesSQLite {
    private static final String URL = "jdbc:sqlite:db/inventario.db";
    private static boolean tablasCreadas = false;
 
    public static Connection conectar() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection(URL);
            if (!tablasCreadas) {
                crearTablas(conn);
                tablasCreadas = true;
            }
            return conn;
        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenciaException(
                    "No se pudo conectar a la base de datos SQLite: " + e.getMessage(), e);
        }
    }
 
    private static void crearTablas(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS activo (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "marca TEXT," +
                "modelo TEXT," +
                "fechaAdquisicion TEXT," +
                "costoBase REAL," +
                "estado TEXT," +
                "especificaciones TEXT," +
                "tipoPeriferico TEXT," +
                "fechaExpiracion TEXT," +
                "numeroPuestos INTEGER," +
                "responsableAsignado TEXT)";
 
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
   

