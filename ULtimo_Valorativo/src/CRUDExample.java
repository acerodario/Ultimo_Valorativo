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
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtNombre, txtApellido, txtCorreo, txtCarrera, txtGustos;

    public CRUDExample() {
        setTitle("CRUD Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear la tabla y el modelo
        model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Correo");
        model.addColumn("Carrera");
        model.addColumn("Gustos");
        table = new JTable(model);

        // Crear los campos de texto
        txtNombre = new JTextField(20);
        txtApellido = new JTextField(20);
        txtCorreo = new JTextField(20);
        txtCarrera = new JTextField(20);
        txtGustos = new JTextField(20);

        // Crear los botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");

        // Agregar los componentes al panel
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(new JLabel("Apellido:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtApellido, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(new JLabel("Correo:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(new JLabel("Carrera:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtCarrera, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(new JLabel("Gustos:"), gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtGustos, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnGuardar, gbc);

        gbc.gridy = 6;
        panelFormulario.add(btnModificar, gbc);

        gbc.gridy = 7;
        panelFormulario.add(btnEliminar, gbc);

        gbc.gridy = 8;
        panelFormulario.add(btnBuscar, gbc);

        // Agregar el panelFormulario al marco
        add(panelFormulario, BorderLayout.NORTH);

        // Agregar la tabla al panel
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(new JScrollPane(table), BorderLayout.CENTER);

        // Agregar los paneles al marco
        add(panelFormulario, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);

        // Establecer acciones para los botones
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRegistro();
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarRegistro();
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarRegistro();
            }
        });

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarRegistro();
            }
        });

        // Establecer acción al seleccionar una fila en la tabla
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String nombre = (String) model.getValueAt(selectedRow, 0);
                    String apellido = (String) model.getValueAt(selectedRow, 1);
                    String correo = (String) model.getValueAt(selectedRow, 2);
                    String carrera = (String) model.getValueAt(selectedRow, 3);
                    String gustos = (String) model.getValueAt(selectedRow, 4);

                    txtNombre.setText(nombre);
                    txtApellido.setText(apellido);
                    txtCorreo.setText(correo);
                    txtCarrera.setText(carrera);
                    txtGustos.setText(gustos);
                }
            }
        });

        // Cargar los registros existentes desde el archivo binario
        cargarRegistros();

        pack();
        setVisible(true);
    }
    private void guardarRegistro() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String correo = txtCorreo.getText();
        String carrera = txtCarrera.getText();
        String gustos = txtGustos.getText();

        if (!nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !carrera.isEmpty() && !gustos.isEmpty()) {
            Object[] row = {nombre, apellido, correo, carrera, gustos};
            model.addRow(row);
            limpiarCampos();
            guardarRegistros();
        } else {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();
            String carrera = txtCarrera.getText();
            String gustos = txtGustos.getText();

            if (!nombre.isEmpty() && !apellido.isEmpty() && !correo.isEmpty() && !carrera.isEmpty() && !gustos.isEmpty()) {
                table.setValueAt(nombre, selectedRow, 0);
                table.setValueAt(apellido, selectedRow, 1);
                table.setValueAt(correo, selectedRow, 2);
                table.setValueAt(carrera, selectedRow, 3);
                table.setValueAt(gustos, selectedRow, 4);
                limpiarCampos();
                guardarRegistros();
            } else {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un registro para modificar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            limpiarCampos();
            guardarRegistros();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un registro para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarRegistro() {
        String searchTerm = JOptionPane.showInputDialog(this, "Ingrese el nombre a buscar:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String nombre = (String) model.getValueAt(i, 0);
                if (nombre.equalsIgnoreCase(searchTerm)) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    break;
                }
            }
        }
    }

    private void cargarRegistros() {
        try {
            File file = new File("registros.bin");
            if (file.exists()) {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                ArrayList<String[]> registros = (ArrayList<String[]>) inputStream.readObject();
                inputStream.close();

                for (String[] registro : registros) {
                    model.addRow(registro);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void guardarRegistros() {
        try {
            File file = new File("registros.bin");
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            ArrayList<String[]> registros = new ArrayList<>();
            for (int i = 0; i < model.getRowCount(); i++) {
                String[] registro = new String[5];
                registro[0] = (String) model.getValueAt(i, 0);
                registro[1] = (String) model.getValueAt(i, 1);
                registro[2] = (String) model.getValueAt(i, 2);
                registro[3] = (String) model.getValueAt(i, 3);
                registro[4] = (String) model.getValueAt(i, 4);
                registros.add(registro);
            }
            outputStream.writeObject(registros);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
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
                new CRUDExample();
            }
        });
    }
}