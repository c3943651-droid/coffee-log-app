package coffeelog.model;

public class Cafe {
    private int id;
    private String nombre;
    private String paisOrigen;
    private String metodoPreparacion;
    private int nivelTueste;
    private int puntuacion;
    private String notasSabor;
    private boolean esFavorito;

    // Constructor vacío
    public Cafe() {}

    // Constructor con parámetros
    public Cafe(String nombre, String paisOrigen, String metodoPreparacion,
                int nivelTueste, int puntuacion, String notasSabor, boolean esFavorito) {
        this.nombre = nombre;
        this.paisOrigen = paisOrigen;
        this.metodoPreparacion = metodoPreparacion;
        this.nivelTueste = nivelTueste;
        this.puntuacion = puntuacion;
        this.notasSabor = notasSabor;
        this.esFavorito = esFavorito;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }

    public String getMetodoPreparacion() { return metodoPreparacion; }
    public void setMetodoPreparacion(String metodoPreparacion) { this.metodoPreparacion = metodoPreparacion; }

    public int getNivelTueste() { return nivelTueste; }
    public void setNivelTueste(int nivelTueste) { this.nivelTueste = nivelTueste; }

    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }

    public String getNotasSabor() { return notasSabor; }
    public void setNotasSabor(String notasSabor) { this.notasSabor = notasSabor; }

    public boolean isEsFavorito() { return esFavorito; }
    public void setEsFavorito(boolean esFavorito) { this.esFavorito = esFavorito; }
}