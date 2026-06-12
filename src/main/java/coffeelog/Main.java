package coffeelog;

import coffeelog.model.Cafe;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- COFFEE LOG APP STARTED ---");

        Cafe c = new Cafe();
        c.setNombre("Caturra");
        c.setPaisOrigen("Colombia");  // ← Corregido
        c.setPuntuacion(9);

        System.out.println("Coffee: " + c.getNombre());
    }
}