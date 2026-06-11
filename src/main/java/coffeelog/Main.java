package coffeelog;

import coffeelog.model.Cafe;
import coffeelog.dao.ConnectionManager;
import coffeelog.dao.CafeDAO;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- COFFEE LOG APP STARTED ---");

        // 1. Probar conexión física
        ConnectionManager.getConnection();

        // 2. Probar Instancia del Modelo
        Cafe c = new Cafe();
        c.setNombre("Caturra");
        // Nota: Asegurarse si en Cafe.java se usó setOrigen o setPaisOrigen
        c.setOrigen("Colombia");
        c.setPuntuacion(9);

        // 3. Probar persistencia a través del DAO
        CafeDAO dao = new CafeDAO();
        dao.save(c);

        // 4. Cerrar conexión de forma segura
        ConnectionManager.closeConnection();
    }
}