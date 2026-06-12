package coffeelog.util;

import coffeelog.dao.CafeDAO;
import coffeelog.model.Cafe;
import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class CSVExporter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void exportarFavoritos(List<Cafe> cafes, java.awt.Component parent) {
        // Filtrar solo los favoritos (RF06)
        List<Cafe> favoritos = cafes.stream()
                .filter(Cafe::isEsFavorito)
                .toList();

        if (favoritos.isEmpty()) {
            JOptionPane.showMessageDialog(parent,
                    "No hay cafés marcados como favoritos para exportar.",
                    "Exportar CSV",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Diálogo para guardar archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo CSV");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        fileChooser.setSelectedFile(new File("cafes_favoritos_" + timestamp + ".csv"));

        if (fileChooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
            return; // Usuario canceló
        }

        File archivo = fileChooser.getSelectedFile();
        if (!archivo.getName().toLowerCase().endsWith(".csv")) {
            archivo = new File(archivo.getAbsolutePath() + ".csv");
        }

        // Escribir el archivo CSV
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivo), "UTF-8"))) {

            // Cabecera
            writer.println("ID,Nombre,País Origen,Método Preparación,Nivel Tueste,Puntuación,Notas de Sabor,Favorito,Fecha Exportación");

            // Datos de cada café favorito
            String fechaExportacion = LocalDateTime.now().format(DATE_FORMATTER);
            for (Cafe cafe : favoritos) {
                writer.printf("\"%d\",\"%s\",\"%s\",\"%s\",%d,%d,\"%s\",\"%s\",\"%s\"%n",
                        cafe.getId(),
                        escapeCSV(cafe.getNombre()),
                        escapeCSV(cafe.getPaisOrigen()),
                        escapeCSV(cafe.getMetodoPreparacion()),
                        cafe.getNivelTueste(),
                        cafe.getPuntuacion(),
                        escapeCSV(cafe.getNotasSabor()),
                        cafe.isEsFavorito() ? "Sí" : "No",
                        fechaExportacion
                );
            }

            JOptionPane.showMessageDialog(parent,
                    String.format("Exportación exitosa!\n\n%d cafés favoritos exportados a:\n%s",
                            favoritos.size(), archivo.getAbsolutePath()),
                    "Exportar CSV",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(parent,
                    "Error al exportar CSV:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Versión alternativa: exporta favoritos directamente desde el DAO
     */
    public static void exportarFavoritosDesdeDAO(CafeDAO dao, java.awt.Component parent) {
        List<Cafe> todos = dao.getAll();
        exportarFavoritos(todos, parent);
    }

    /**
     * Escapa caracteres especiales para CSV (comillas, saltos de línea)
     */
    private static String escapeCSV(String texto) {
        if (texto == null || texto.isEmpty()) return "";

        // Reemplazar saltos de línea por espacios
        String escapado = texto.replace("\n", " ").replace("\r", " ");

        // Escapar comillas dobles
        if (escapado.contains("\"")) {
            escapado = escapado.replace("\"", "\"\"");
        }

        return escapado;
    }
}