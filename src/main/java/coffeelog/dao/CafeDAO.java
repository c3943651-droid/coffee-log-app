package coffeelog.dao;

import coffeelog.model.Cafe;
import java.util.ArrayList;
import java.util.List;

public class CafeDAO {
    // Simulación temporal en memoria antes de meter las consultas SQL de inserción
    private List<Cafe> localDatabaseSimulation = new ArrayList<>();

    public void save(Cafe cafe) {
        localDatabaseSimulation.add(cafe);
        System.out.println("Data successfully passed to DAO for: " + cafe.getNombre());
    }

    public List<Cafe> getAll() {
        return localDatabaseSimulation;
    }
}