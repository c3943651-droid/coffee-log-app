package coffeelog.ui;

import coffeelog.dao.CafeDAO;
import coffeelog.model.Cafe;
import coffeelog.util.CSVExporter;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class CoffeeReadingForm extends JDialog {
    private final CafeDAO cafeDAO;
    private JTable tablaCafes;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField txtBuscar;
    private JLabel lblPromedio;

    public CoffeeReadingForm(JFrame parent, CafeDAO cafeDAO) {
        super(parent, "📊 Listado de Cafés", true);
        this.cafeDAO = cafeDAO;
        initComponents();
        cargarDatos();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(1000, 600);
        getContentPane().setBackground(new Color(250, 245, 235));
        setLayout(new BorderLayout());
        add(crearPanelSuperior(), BorderLayout.NORTH);
        add(crearPanelTabla(), BorderLayout.CENTER);
        add(crearPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSuperior.setBackground(new Color(240, 230, 215));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblBuscar.setForeground(new Color(62, 44, 35));
        panelSuperior.add(lblBuscar);

        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 160, 140)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });
        panelSuperior.add(txtBuscar);

        lblPromedio = new JLabel("📈 Puntuación promedio: --");
        lblPromedio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPromedio.setForeground(new Color(107, 68, 35));
        panelSuperior.add(Box.createHorizontalStrut(30));
        panelSuperior.add(lblPromedio);

        return panelSuperior;
    }

    private JScrollPane crearPanelTabla() {
        String[] columnas = {"ID", "Nombre", "País Origen", "Método", "Tueste", "Puntuación", "Notas", "Favorito"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCafes = new JTable(tableModel);
        tablaCafes.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablaCafes.setRowHeight(30);
        tablaCafes.setIntercellSpacing(new Dimension(10, 5));
        tablaCafes.setSelectionBackground(new Color(210, 190, 170));
        tablaCafes.setSelectionForeground(Color.BLACK);

        // Estilizar header
        tablaCafes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaCafes.getTableHeader().setBackground(new Color(107, 68, 35));
        tablaCafes.getTableHeader().setForeground(Color.WHITE);
        tablaCafes.getTableHeader().setPreferredSize(new Dimension(100, 35));

        sorter = new TableRowSorter<>(tableModel);
        tablaCafes.setRowSorter(sorter);

        // Ajustar anchos
        tablaCafes.getColumnModel().getColumn(0).setMaxWidth(50);
        tablaCafes.getColumnModel().getColumn(4).setMaxWidth(70);
        tablaCafes.getColumnModel().getColumn(5).setMaxWidth(80);
        tablaCafes.getColumnModel().getColumn(7).setMaxWidth(70);

        JScrollPane scrollPane = new JScrollPane(tablaCafes);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 160)));

        return scrollPane;
    }

    private JPanel crearPanelInferior() {
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        panelInferior.setBackground(new Color(240, 230, 215));
        panelInferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 180, 160)));

        JButton btnEditar = crearBotonAccion("✏️ Editar Café", new Color(52, 152, 219));
        JButton btnEliminar = crearBotonAccion("🗑️ Eliminar Café", new Color(231, 76, 60));
        JButton btnExportar = crearBotonAccion("📤 Exportar Favoritos", new Color(46, 204, 113));
        JButton btnCerrar = crearBotonAccion("❌ Cerrar", new Color(149, 165, 166));

        btnEditar.addActionListener(e -> editarCafe());
        btnEliminar.addActionListener(e -> eliminarCafe());
        btnExportar.addActionListener(e -> exportarFavoritos());
        btnCerrar.addActionListener(e -> dispose());

        panelInferior.add(btnEditar);
        panelInferior.add(btnEliminar);
        panelInferior.add(btnExportar);
        panelInferior.add(btnCerrar);

        return panelInferior;
    }

    private JButton crearBotonAccion(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(140, 35));
        return boton;
    }

    private void cargarDatos() {
        tableModel.setRowCount(0);
        List<Cafe> cafes = cafeDAO.getAll();
        for (Cafe cafe : cafes) {
            tableModel.addRow(new Object[]{
                    cafe.getId(), cafe.getNombre(), cafe.getPaisOrigen(),
                    cafe.getMetodoPreparacion(), cafe.getNivelTueste(),
                    cafe.getPuntuacion(), cafe.getNotasSabor(),
                    cafe.isEsFavorito() ? "❤️" : "🤍"
            });
        }
        double promedio = cafeDAO.getAveragePuntuacion();
        if (promedio > 0) {
            lblPromedio.setText(String.format("📈 Puntuación promedio: %.2f", promedio));
        } else if (cafes.isEmpty()) {
            lblPromedio.setText("📈 Puntuación promedio: --");
        }
    }

    private void filtrar() {
        String texto = txtBuscar.getText();
        if (texto.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }

    private void editarCafe() {
        int filaSeleccionada = tablaCafes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Seleccione un café para editar");
            return;
        }
        int filaReal = tablaCafes.convertRowIndexToModel(filaSeleccionada);
        int idCafe = (int) tableModel.getValueAt(filaReal, 0);
        Cafe cafeEditar = cafeDAO.getById(idCafe);
        if (cafeEditar != null) {
            CoffeeWriteForm writeForm = new CoffeeWriteForm((JFrame) getParent(), cafeDAO, cafeEditar);
            writeForm.setVisible(true);
            cargarDatos();
        }
    }

    private void eliminarCafe() {
        int filaSeleccionada = tablaCafes.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Seleccione un café para eliminar");
            return;
        }
        int filaReal = tablaCafes.convertRowIndexToModel(filaSeleccionada);
        int idCafe = (int) tableModel.getValueAt(filaReal, 0);
        String nombreCafe = (String) tableModel.getValueAt(filaReal, 1);
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Eliminar café '" + nombreCafe + "'?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cafeDAO.delete(idCafe);
            JOptionPane.showMessageDialog(this, "✅ Café eliminado exitosamente");
            cargarDatos();
        }
    }

    private void exportarFavoritos() {
        List<Cafe> favoritos = cafeDAO.getFavoritos();
        if (favoritos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No hay cafés marcados como favoritos para exportar.");
            return;
        }
        CSVExporter.exportarFavoritos(favoritos, this);
    }
}