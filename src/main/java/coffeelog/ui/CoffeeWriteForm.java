package coffeelog.ui;

import coffeelog.dao.CafeDAO;
import coffeelog.model.Cafe;
import coffeelog.util.CBOption;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class CoffeeWriteForm extends JDialog {
    private final CafeDAO cafeDAO;
    private final Cafe cafeEditar;
    private final boolean editando;
    private JTextField txtNombre;
    private JComboBox<CBOption> cbPais;
    private JComboBox<CBOption> cbMetodo;
    private JSlider sliderTueste;
    private JSlider sliderPuntuacion;
    private JTextArea txtNotas;
    private JCheckBox chkFavorito;

    public CoffeeWriteForm(JFrame parent, CafeDAO cafeDAO, Cafe cafeEditar) {
        super(parent, cafeEditar == null ? "✏️ Registrar Café" : "✏️ Editar Café", true);
        this.cafeDAO = cafeDAO;
        this.cafeEditar = cafeEditar;
        this.editando = (cafeEditar != null);
        initComponents();
        if (editando) cargarDatosParaEditar();
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setSize(550, 650);
        getContentPane().setBackground(new Color(250, 245, 235));
        setLayout(new BorderLayout());
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelBotones(), BorderLayout.SOUTH);
    }

    private JPanel crearPanelFormulario() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(new Color(250, 245, 235));
        panelForm.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Nombre
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("☕ Nombre del Café:*"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        estilizarCampo(txtNombre);
        panelForm.add(txtNombre, gbc);
        row++;

        // País Origen
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("🌍 País Origen:"), gbc);
        gbc.gridx = 1;
        cbPais = new JComboBox<>();
        cbPais.addItem(new CBOption("COL", "Colombia"));
        cbPais.addItem(new CBOption("BRA", "Brasil"));
        cbPais.addItem(new CBOption("ETH", "Etiopía"));
        cbPais.addItem(new CBOption("MEX", "México"));
        cbPais.addItem(new CBOption("GTM", "Guatemala"));
        estilizarCombo(cbPais);
        panelForm.add(cbPais, gbc);
        row++;

        // Método
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("🔧 Método:"), gbc);
        gbc.gridx = 1;
        cbMetodo = new JComboBox<>();
        cbMetodo.addItem(new CBOption("V60", "V60"));
        cbMetodo.addItem(new CBOption("CHEMEX", "Chemex"));
        cbMetodo.addItem(new CBOption("FRENCH", "Prensa Francesa"));
        cbMetodo.addItem(new CBOption("ESPRESSO", "Espresso"));
        cbMetodo.addItem(new CBOption("AEROPRESS", "Aeropress"));
        estilizarCombo(cbMetodo);
        panelForm.add(cbMetodo, gbc);
        row++;

        // Tueste
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("🔥 Nivel Tueste (1-5):"), gbc);
        gbc.gridx = 1;
        JPanel panelTueste = new JPanel(new BorderLayout());
        panelTueste.setOpaque(false);
        sliderTueste = new JSlider(1, 5, 3);
        sliderTueste.setMajorTickSpacing(1);
        sliderTueste.setPaintTicks(true);
        sliderTueste.setPaintLabels(true);
        sliderTueste.setBackground(new Color(250, 245, 235));
        JLabel lblTuesteValor = new JLabel("Valor: 3");
        lblTuesteValor.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sliderTueste.addChangeListener(e -> lblTuesteValor.setText("Valor: " + sliderTueste.getValue()));
        panelTueste.add(sliderTueste, BorderLayout.CENTER);
        panelTueste.add(lblTuesteValor, BorderLayout.EAST);
        panelForm.add(panelTueste, gbc);
        row++;

        // Puntuación
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("⭐ Puntuación (1-10):"), gbc);
        gbc.gridx = 1;
        JPanel panelPuntuacion = new JPanel(new BorderLayout());
        panelPuntuacion.setOpaque(false);
        sliderPuntuacion = new JSlider(1, 10, 7);
        sliderPuntuacion.setMajorTickSpacing(1);
        sliderPuntuacion.setPaintTicks(true);
        sliderPuntuacion.setPaintLabels(true);
        sliderPuntuacion.setBackground(new Color(250, 245, 235));
        JLabel lblPuntuacionValor = new JLabel("Valor: 7");
        lblPuntuacionValor.setFont(new Font("Segoe UI", Font.BOLD, 12));
        sliderPuntuacion.addChangeListener(e -> lblPuntuacionValor.setText("Valor: " + sliderPuntuacion.getValue()));
        panelPuntuacion.add(sliderPuntuacion, BorderLayout.CENTER);
        panelPuntuacion.add(lblPuntuacionValor, BorderLayout.EAST);
        panelForm.add(panelPuntuacion, gbc);
        row++;

        // Notas
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("📝 Notas de Sabor:"), gbc);
        gbc.gridx = 1;
        txtNotas = new JTextArea(4, 20);
        txtNotas.setLineWrap(true);
        txtNotas.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtNotas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        JScrollPane scrollNotas = new JScrollPane(txtNotas);
        scrollNotas.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 160)));
        panelForm.add(scrollNotas, gbc);
        row++;

        // Favorito
        gbc.gridx = 0; gbc.gridy = row;
        panelForm.add(crearLabel("❤️ Marcar como Favorito:"), gbc);
        gbc.gridx = 1;
        chkFavorito = new JCheckBox();
        chkFavorito.setBackground(new Color(250, 245, 235));
        panelForm.add(chkFavorito, gbc);

        return panelForm;
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(62, 44, 35));
        return label;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 180, 160)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
    }

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBackground(Color.WHITE);
    }

    private JPanel crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBotones.setBackground(new Color(250, 245, 235));
        panelBotones.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 180, 160)));

        JButton btnGuardar = new JButton(editando ? "💾 Actualizar" : "💾 Guardar");
        JButton btnCancelar = new JButton("❌ Cancelar");

        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnGuardar.setBackground(new Color(107, 68, 35));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setPreferredSize(new Dimension(120, 35));

        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCancelar.setBackground(new Color(150, 130, 110));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setPreferredSize(new Dimension(120, 35));

        btnGuardar.addActionListener(e -> guardarCafe());
        btnCancelar.addActionListener(e -> dispose());

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        return panelBotones;
    }

    private void cargarDatosParaEditar() {
        if (cafeEditar == null) return;
        txtNombre.setText(cafeEditar.getNombre());
        String paisKey = cafeEditar.getPaisOrigen();
        for (int i = 0; i < cbPais.getItemCount(); i++) {
            CBOption option = cbPais.getItemAt(i);
            if (option != null && option.getKey().equals(paisKey)) {
                cbPais.setSelectedIndex(i);
                break;
            }
        }
        String metodoKey = cafeEditar.getMetodoPreparacion();
        for (int i = 0; i < cbMetodo.getItemCount(); i++) {
            CBOption option = cbMetodo.getItemAt(i);
            if (option != null && option.getKey().equals(metodoKey)) {
                cbMetodo.setSelectedIndex(i);
                break;
            }
        }
        sliderTueste.setValue(cafeEditar.getNivelTueste());
        sliderPuntuacion.setValue(cafeEditar.getPuntuacion());
        txtNotas.setText(cafeEditar.getNotasSabor());
        chkFavorito.setSelected(cafeEditar.isEsFavorito());
    }

    private void guardarCafe() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "❌ El nombre del café es obligatorio");
            txtNombre.requestFocus();
            return;
        }
        CBOption paisSelected = (CBOption) cbPais.getSelectedItem();
        CBOption metodoSelected = (CBOption) cbMetodo.getSelectedItem();
        if (paisSelected == null || metodoSelected == null) {
            JOptionPane.showMessageDialog(this, "❌ Error al obtener los datos seleccionados");
            return;
        }
        Cafe cafe = new Cafe(
                txtNombre.getText().trim(),
                paisSelected.getKey(),
                metodoSelected.getKey(),
                sliderTueste.getValue(),
                sliderPuntuacion.getValue(),
                txtNotas.getText().trim(),
                chkFavorito.isSelected()
        );
        if (editando && cafeEditar != null) {
            cafe.setId(cafeEditar.getId());
            cafeDAO.update(cafe);
            JOptionPane.showMessageDialog(this, "✅ Café actualizado exitosamente");
        } else {
            cafeDAO.save(cafe);
            JOptionPane.showMessageDialog(this, "✅ Café guardado exitosamente");
        }
        dispose();
    }
}