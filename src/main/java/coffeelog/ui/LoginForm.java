package coffeelog.ui;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JDialog {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancelar;
    private boolean autenticado = false;
    private static final String USUARIO_VALIDO = "admin";
    private static final String PASSWORD_VALIDO = "1234";

    public LoginForm(JFrame parent) {
        super(parent, "Login - CoffeeLogApp", true);
        initComponents();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(450, 320);
        setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(107, 68, 35), 2));

        // Panel superior con gradiente
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(107, 68, 35), getWidth(), 0, new Color(80, 50, 30));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setPreferredSize(new Dimension(450, 80));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("☕ CoffeeLogApp", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 245, 235));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblUsuario = new JLabel("👤 Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUsuario.setForeground(new Color(62, 44, 35));
        formPanel.add(lblUsuario, gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 160, 140)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPassword = new JLabel("🔒 Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPassword.setForeground(new Color(62, 44, 35));
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 160, 140)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(txtPassword, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setOpaque(false);

        btnLogin = crearBotonEstilizado("Iniciar Sesión", new Color(107, 68, 35));
        btnCancelar = crearBotonEstilizado("Cancelar", new Color(150, 130, 110));

        btnLogin.addActionListener(e -> login());
        btnCancelar.addActionListener(e -> System.exit(0));

        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);
        formPanel.add(panelBotones, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
        getRootPane().setDefaultButton(btnLogin);

        // Permitir arrastrar la ventana
        agregarArrastre(mainPanel);
    }

    private JButton crearBotonEstilizado(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(130, 35));
        return boton;
    }

    private void agregarArrastre(JComponent componente) {
        final Point[] mouseOffset = {null};
        componente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent e) {
                mouseOffset[0] = e.getPoint();
            }
        });
        componente.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - mouseOffset[0].x, p.y + e.getY() - mouseOffset[0].y);
            }
        });
    }

    private void login() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (usuario.equals(USUARIO_VALIDO) && password.equals(PASSWORD_VALIDO)) {
            autenticado = true;
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Usuario o contraseña incorrectos",
                    "Error de autenticación",
                    JOptionPane.ERROR_MESSAGE);
            txtUsuario.setText("");
            txtPassword.setText("");
            txtUsuario.requestFocus();
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }
}