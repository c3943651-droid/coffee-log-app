package coffeelog.dao;

import coffeelog.model.Cafe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CafeDAO {

    // CREATE - Guardar nuevo café
    public void save(Cafe cafe) {
        String sql = "INSERT INTO Cafes (nombre, pais_origen, metodo_preparacion, nivel_tueste, puntuacion, notas_sabor, es_favorito) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cafe.getNombre());
            pstmt.setString(2, cafe.getPaisOrigen());
            pstmt.setString(3, cafe.getMetodoPreparacion());
            pstmt.setInt(4, cafe.getNivelTueste());
            pstmt.setInt(5, cafe.getPuntuacion());
            pstmt.setString(6, cafe.getNotasSabor());
            pstmt.setBoolean(7, cafe.isEsFavorito());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cafe.setId(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Café guardado: " + cafe.getNombre());
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar café: " + e.getMessage());
        }
    }

    // READ - Obtener todos los cafés
    public List<Cafe> getAll() {
        List<Cafe> cafes = new ArrayList<>();
        String sql = "SELECT * FROM Cafes ORDER BY id";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cafes.add(mapResultSetToCafe(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cafés: " + e.getMessage());
        }

        return cafes;
    }

    // READ - Obtener café por ID
    public Cafe getById(int id) {
        String sql = "SELECT * FROM Cafes WHERE id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToCafe(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener café por ID: " + e.getMessage());
        }

        return null;
    }

    // UPDATE - Actualizar café existente
    public void update(Cafe cafe) {
        String sql = "UPDATE Cafes SET nombre=?, pais_origen=?, metodo_preparacion=?, " +
                "nivel_tueste=?, puntuacion=?, notas_sabor=?, es_favorito=? WHERE id=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cafe.getNombre());
            pstmt.setString(2, cafe.getPaisOrigen());
            pstmt.setString(3, cafe.getMetodoPreparacion());
            pstmt.setInt(4, cafe.getNivelTueste());
            pstmt.setInt(5, cafe.getPuntuacion());
            pstmt.setString(6, cafe.getNotasSabor());
            pstmt.setBoolean(7, cafe.isEsFavorito());
            pstmt.setInt(8, cafe.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Café actualizado: " + cafe.getNombre());
            } else {
                System.out.println("No se encontró café con ID: " + cafe.getId());
            }

        } catch (SQLException e) {
            System.err.println("Error al actualizar café: " + e.getMessage());
        }
    }

    // DELETE - Eliminar café por ID
    public void delete(int id) {
        String sql = "DELETE FROM Cafes WHERE id=?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Café eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró café con ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error al eliminar café: " + e.getMessage());
        }
    }

    // DELETE - Eliminar todos los cafés (útil para pruebas)
    public void deleteAll() {
        String sql = "DELETE FROM Cafes";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {

            int affectedRows = stmt.executeUpdate(sql);
            System.out.println("Se eliminaron " + affectedRows + " cafés");

        } catch (SQLException e) {
            System.err.println("Error al eliminar todos los cafés: " + e.getMessage());
        }
    }

    // Método auxiliar para mapear ResultSet a objeto Cafe
    private Cafe mapResultSetToCafe(ResultSet rs) throws SQLException {
        Cafe cafe = new Cafe();
        cafe.setId(rs.getInt("id"));
        cafe.setNombre(rs.getString("nombre"));
        cafe.setPaisOrigen(rs.getString("pais_origen"));
        cafe.setMetodoPreparacion(rs.getString("metodo_preparacion"));
        cafe.setNivelTueste(rs.getInt("nivel_tueste"));
        cafe.setPuntuacion(rs.getInt("puntuacion"));
        cafe.setNotasSabor(rs.getString("notas_sabor"));
        cafe.setEsFavorito(rs.getBoolean("es_favorito"));
        return cafe;
    }

    // Método para obtener solo cafés favoritos
    public List<Cafe> getFavoritos() {
        List<Cafe> favoritos = new ArrayList<>();
        String sql = "SELECT * FROM Cafes WHERE es_favorito = 1 ORDER BY id";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                favoritos.add(mapResultSetToCafe(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cafés favoritos: " + e.getMessage());
        }

        return favoritos;
    }

    // Método para obtener el promedio de puntuaciones
    public double getAveragePuntuacion() {
        String sql = "SELECT AVG(puntuacion) as promedio FROM Cafes";

        try (Connection conn = ConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble("promedio");
            }

        } catch (SQLException e) {
            System.err.println("Error al calcular promedio: " + e.getMessage());
        }

        return 0.0;
    }
}