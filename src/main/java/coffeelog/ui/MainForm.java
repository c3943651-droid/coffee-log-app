package coffeelog.ui;

import coffeelog.dao.CafeDAO;
import coffeelog.util.CSVExporter;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainForm extends JFrame {
    private CafeDAO cafeDAO;

    public MainForm() {
        cafeDAO = new CafeDAO();
        initComponents();
        setTitle("CoffeeLogApp - Gestor de Catas de Café");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Crear menu bar con estilo
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(62, 44, 35));
        menuBar.setForeground(Color.WHITE);

        JMenu menuArchivo = crearMenu("Archivo");
        JMenu menuConsultas = crearMenu("Consultas");

        JMenuItem menuItemRegistrar = crearMenuItem("📝 Registrar Café");
        JMenuItem menuItemExportar = crearMenuItem("📤 Exportar Favoritos a CSV");
        JMenuItem menuItemSalir = crearMenuItem("🚪 Salir");

        menuItemRegistrar.addActionListener(e -> abrirFormularioRegistro());
        menuItemExportar.addActionListener(e -> exportarFavoritos());
        menuItemSalir.addActionListener(e -> System.exit(0));

        menuArchivo.add(menuItemRegistrar);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemExportar);
        menuArchivo.addSeparator();
        menuArchivo.add(menuItemSalir);

        JMenuItem menuItemListar = crearMenuItem("📋 Listar Cafés");
        menuItemListar.addActionListener(e -> abrirListado());
        menuConsultas.add(menuItemListar);

        menuBar.add(menuArchivo);
        menuBar.add(menuConsultas);
        setJMenuBar(menuBar);

        // Panel con gradiente
        setContentPane(new GradientPanel());
        getContentPane().setLayout(new BorderLayout());

        // Panel de bienvenida mejorado
        JPanel panelBienvenida = new JPanel(new GridBagLayout());
        panelBienvenida.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Logo o ícono
        JLabel lblIcono = new JLabel("☕");
        lblIcono.setFont(new Font("Segoe UI", Font.PLAIN, 80));
        lblIcono.setForeground(new Color(107, 68, 35));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBienvenida.add(lblIcono, gbc);

        JLabel lblBienvenida = new JLabel("Bienvenido a CoffeeLogApp");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBienvenida.setForeground(new Color(62, 44, 35));
        gbc.gridy = 1;
        panelBienvenida.add(lblBienvenida, gbc);

        JLabel lblInstrucciones = new JLabel("Use el menú Archivo para registrar cafés o exportar favoritos");
        lblInstrucciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInstrucciones.setForeground(new Color(100, 80, 70));
        gbc.gridy = 2;
        panelBienvenida.add(lblInstrucciones, gbc);

        JLabel lblSubtitulo = new JLabel("Registra, puntúa y gestiona tus experiencias de cata");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblSubtitulo.setForeground(new Color(120, 100, 90));
        gbc.gridy = 3;
        panelBienvenida.add(lblSubtitulo, gbc);

        getContentPane().add(panelBienvenida, BorderLayout.CENTER);
    }

    private JMenu crearMenu(String texto) {
        JMenu menu = new JMenu(texto);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 13));
        menu.setForeground(Color.WHITE);
        return menu;
    }

    private JMenuItem crearMenuItem(String texto) {
        JMenuItem item = new JMenuItem(texto);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return item;
    }

    private void abrirFormularioRegistro() {
        CoffeeWriteForm writeForm = new CoffeeWriteForm(this, cafeDAO, null);
        writeForm.setVisible(true);
    }

    private void abrirListado() {
        CoffeeReadingForm readingForm = new CoffeeReadingForm(this, cafeDAO);
        readingForm.setVisible(true);
    }

    private void exportarFavoritos() {
        List<coffeelog.model.Cafe> cafes = cafeDAO.getAll();
        CSVExporter.exportarFavoritos(cafes, this);
    }

    // Clase para panel con gradiente
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            int w = getWidth();
            int h = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(245, 235, 220), w, h, new Color(230, 215, 195));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo cargar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm(null);
            login.setVisible(true);

            if (login.isAutenticado()) {
                new MainForm().setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}