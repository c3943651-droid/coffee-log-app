package coffeelog.model;

public class Cafe {
    private int id;
    private String name;
    private String origin;
    private String roastType;
    private String flavorNotes;
    private int rating;

    public Cafe() {}

    public Cafe(String name, String origin, String roastType, String flavorNotes, int rating) {
        this.name = name;
        this.origin = origin;
        this.roastType = roastType;
        this.flavorNotes = flavorNotes;
        this.rating = rating;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return name; }
    public void setNombre(String name) { this.name = name; }

    public String getOrigen() { return origin; }
    public void setOrigen(String origin) { this.origin = origin; }

    public String getTipoTueste() { return roastType; }
    public void setTipoTueste(String roastType) { this.roastType = roastType; }

    public String getNotasSabor() { return flavorNotes; }
    public void setNotasSabor(String flavorNotes) { this.flavorNotes = flavorNotes; }

    public int getPuntuacion() { return rating; }
    public void setPuntuacion(int rating) { this.rating = rating; }
}