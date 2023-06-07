import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class CRUDExample extends JFrame {
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtCarrera;
    private JTextField txtGustos;
    private JButton btnGuardar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnCambiarColor; // Botón Cambiar color agregado
    private JTable table;
    private DefaultTableModel model;

    public CRUDExample() {
        setTitle("CRUD Example");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel lblNombre = new JLabel("Nombre:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panelFormulario.add(lblNombre, constraints);

        txtNombre = new JTextField(20);
        constraints.gridx = 1;
        panelFormulario.add(txtNombre, constraints);

        JLabel lblApellido = new JLabel("Apellido:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panelFormulario.add(lblApellido, constraints);

        txtApellido = new JTextField(20);
        constraints.gridx = 1;
        panelFormulario.add(txtApellido, constraints);

        JLabel lblCorreo = new JLabel("Correo:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        panelFormulario.add(lblCorreo, constraints);

        txtCorreo = new JTextField(20);
        constraints.gridx = 1;
        panelFormulario.add(txtCorreo, constraints);

        JLabel lblCarrera = new JLabel("Carrera:");
        constraints.gridx = 0;
        constraints.gridy = 3;
        panelFormulario.add(lblCarrera, constraints);

        txtCarrera = new JTextField(20);
        constraints.gridx = 1;
        panelFormulario.add(txtCarrera, constraints);

        JLabel lblGustos = new JLabel("Gustos:");
        constraints.gridx = 0;
        constraints.gridy = 4;
        panelFormulario.add(lblGustos, constraints);

        txtGustos = new JTextField(20);
        constraints.gridx = 1;
        panelFormulario.add(txtGustos, constraints);

        btnGuardar = new JButton("Guardar");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnGuardar, constraints);

        btnModificar = new JButton("Modificar");
        constraints.gridy = 6;
        panelFormulario.add(btnModificar, constraints);

        btnEliminar = new JButton("Eliminar");
        constraints.gridy = 7;
        panelFormulario.add(btnEliminar, constraints);

        btnBuscar = new JButton("Buscar");
        constraints.gridy = 8;
        panelFormulario.add(btnBuscar, constraints);

        btnCambiarColor = new JButton("Cambiar color");
        constraints.gridy = 9;
        panelFormulario.add(btnCambiarColor, constraints);

        JPanel panelTabla = new JPanel(new BorderLayout());
        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Correo");
        model.addColumn("Carrera");
        model.addColumn("Gustos");
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        add(panelFormulario, BorderLayout.WEST);
        add(panelTabla, BorderLayout.CENTER);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String carrera = txtCarrera.getText();
                String gustos = txtGustos.getText();

                if (!nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !carrera.isEmpty() && !gustos.isEmpty()) {
                    String[] datos = {nombre, apellido, correo, carrera, gustos};
                    model.addRow(datos);
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(CRUDExample.this, "Por favor, complete todos los campos", "Guardar", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    String correo = txtCorreo.getText();
                    String carrera = txtCarrera.getText();
                    String gustos = txtGustos.getText();

                    if (!nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !carrera.isEmpty() && !gustos.isEmpty()) {
                        String[] datos = {nombre, apellido, correo, carrera, gustos};
                        for (int i = 0; i < datos.length; i++) {
                            model.setValueAt(datos[i], selectedRow, i);
                        }
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(CRUDExample.this, "Por favor, complete todos los campos", "Modificar", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(CRUDExample.this, "Por favor, seleccione un registro para modificar", "Modificar", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(CRUDExample.this, "¿Está seguro de eliminar este registro?", "Eliminar", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        model.removeRow(selectedRow);
                        limpiarCampos();
                    }
                } else {
                    JOptionPane.showMessageDialog(CRUDExample.this, "Por favor, seleccione un registro para eliminar", "Eliminar", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = JOptionPane.showInputDialog(CRUDExample.this, "Ingrese el término de búsqueda:", "Buscar", JOptionPane.QUESTION_MESSAGE);
                if (searchTerm != null && !searchTerm.isEmpty()) {
                    buscarRegistro(searchTerm);
                }
            }
        });

        btnCambiarColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(CRUDExample.this, "Seleccione un color", table.getBackground());
                if (color != null) {
                    table.setBackground(color);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String nombre = model.getValueAt(selectedRow, 0).toString();
                    String apellido = model.getValueAt(selectedRow, 1).toString();
                    String correo = model.getValueAt(selectedRow, 2).toString();
                    String carrera = model.getValueAt(selectedRow, 3).toString();
                    String gustos = model.getValueAt(selectedRow, 4).toString();

                    txtNombre.setText(nombre);
                    txtApellido.setText(apellido);
                    txtCorreo.setText(correo);
                    txtCarrera.setText(carrera);
                    txtGustos.setText(gustos);
                }
            }
        });

        pack();
    }

    private void buscarRegistro(String searchTerm) {
        ArrayList<Integer> resultados = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                if (model.getValueAt(i, j).toString().toLowerCase().contains(searchTerm.toLowerCase())) {
                    resultados.add(i);
                    break;
                }
            }
        }

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(CRUDExample.this, "No se encontraron registros coincidentes", "Buscar", JOptionPane.INFORMATION_MESSAGE);
        } else {
            resaltarFilas(resultados);
        }
    }

    private void resaltarFilas(ArrayList<Integer> filas) {
        table.clearSelection();
        for (int fila : filas) {
            table.addRowSelectionInterval(fila, fila);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtCorreo.setText("");
        txtCarrera.setText("");
        txtGustos.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CRUDExample().setVisible(true);
            }
        });
    }
}
