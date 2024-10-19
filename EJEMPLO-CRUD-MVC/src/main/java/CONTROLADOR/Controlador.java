/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Alumno;
import MODELO.AlumnoDAO;
import VISTA.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controlador implements ActionListener {

    private Vista vista;
    private AlumnoDAO dao;
    private DefaultTableModel modelo;
    private int idAlumnoEditar = -1; // Variable para guardar el ID del alumno a editar

    public Controlador(Vista vista, AlumnoDAO dao) {
    this.vista = vista;
    this.dao = dao;
    this.modelo = (DefaultTableModel) this.vista.getTabla().getModel();
    
    // Asignar listeners a los botones
    this.vista.getBtnGuardar().addActionListener(this);
    this.vista.getBtnListar().addActionListener(this);
    this.vista.getBtnEliminar().addActionListener(this);
    this.vista.getBtnEditar().addActionListener(this);
    this.vista.getBtnOk().addActionListener(this);
    
    // Validaciones en los campos de texto

    // Validación de solo letras para el campo de Nombre
    vista.getTxtNombre().addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            validarSoloLetras(evt);
        }
    });

    // Validación de solo letras para el campo de Apellido
    vista.getTxtApellido().addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            validarSoloLetras(evt);
        }
    });

    // Validación de solo 8 números para el DNI
    vista.getTxtDni().addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            validarSoloNumeros(evt, vista.getTxtDni().getText(), 8);
        }
    });

    // Validación de solo 9 números para el Teléfono
    vista.getTxtTelefono().addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyTyped(java.awt.event.KeyEvent evt) {
            validarSoloNumeros(evt, vista.getTxtTelefono().getText(), 9);
        }
    });
}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnGuardar()) {
            agregar(); // Guardar
        } else if (e.getSource() == vista.getBtnListar()) {
            listar(); // Listar
        } else if (e.getSource() == vista.getBtnEliminar()) {
            eliminar(); // Eliminar
        } else if (e.getSource() == vista.getBtnEditar()) {
            cargarDatosEnCampos(); // Cargar datos para editar
        } else if (e.getSource() == vista.getBtnOk()) {
            actualizar(); // Guardar la modificación
        }
    }

    private void agregar() {
    if (vista.getTxtNombre().getText().isEmpty() ||
        vista.getTxtApellido().getText().isEmpty() ||
        vista.getTxtDni().getText().isEmpty() ||
        vista.getTxtTelefono().getText().isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
        return;
    }

    // Validar que el DNI tenga exactamente 8 dígitos
    if (vista.getTxtDni().getText().length() != 8) {
        JOptionPane.showMessageDialog(vista, "El DNI debe tener exactamente 8 dígitos.");
        return;
    }

    // Validar que el Teléfono tenga exactamente 9 dígitos
    if (vista.getTxtTelefono().getText().length() != 9) {
        JOptionPane.showMessageDialog(vista, "El teléfono debe tener exactamente 9 dígitos.");
        return;
    }

    // Crear el objeto Alumno y guardar
    Alumno alumno = new Alumno();
    alumno.setNombre(vista.getTxtNombre().getText());
    alumno.setApellido(vista.getTxtApellido().getText());
    alumno.setDni(vista.getTxtDni().getText());
    alumno.setTelefono(vista.getTxtTelefono().getText());

    // Guardar nuevo alumno en la base de datos
    dao.agregar(alumno);
    JOptionPane.showMessageDialog(vista, "Alumno agregado correctamente.");
    
    limpiarCampos(); // Limpiar los campos después de guardar
    idAlumnoEditar = -1; // Resetear el ID para nuevos registros
}




    private void listar() {
    modelo.setRowCount(0);  // Limpiar la tabla antes de listar
    List<Alumno> lista = dao.listar();  // Obtener todos los alumnos
    for (Alumno alumno : lista) {
        modelo.addRow(new Object[]{
            alumno.getId(),
            alumno.getNombre(),
            alumno.getApellido(),
            alumno.getDni(),
            alumno.getTelefono()
        });
    }
}


    private void eliminar() {
    int fila = vista.getTabla().getSelectedRow();  // Obtener la fila seleccionada
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Debe seleccionar un alumno para eliminar.");
    } else {
        int id = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());  // Obtener el ID del alumno
        dao.eliminar(id);  // Llamar al DAO para eliminar el alumno
        JOptionPane.showMessageDialog(vista, "Alumno eliminado correctamente.");
        listar();  // Refrescar la tabla
    }
}




    private void cargarDatosEnCampos() {
    int fila = vista.getTabla().getSelectedRow();  // Obtener la fila seleccionada
    if (fila != -1) {
        idAlumnoEditar = Integer.parseInt(vista.getTabla().getValueAt(fila, 0).toString());  // Obtener el ID del alumno
        vista.getTxtNombre().setText(vista.getTabla().getValueAt(fila, 1).toString());
        vista.getTxtApellido().setText(vista.getTabla().getValueAt(fila, 2).toString());
        vista.getTxtDni().setText(vista.getTabla().getValueAt(fila, 3).toString());
        vista.getTxtTelefono().setText(vista.getTabla().getValueAt(fila, 4).toString());
    } else {
        JOptionPane.showMessageDialog(vista, "Debe seleccionar un alumno para editar.");
    }
}



  private void actualizar() {
    if (idAlumnoEditar != -1) {  // Verificar que se ha seleccionado un alumno para editar
        Alumno alumno = new Alumno();
        alumno.setId(idAlumnoEditar);  // Asegurar que estamos actualizando el ID correcto
        alumno.setNombre(vista.getTxtNombre().getText());
        alumno.setApellido(vista.getTxtApellido().getText());
        alumno.setDni(vista.getTxtDni().getText());
        alumno.setTelefono(vista.getTxtTelefono().getText());

        int resultado = dao.actualizar(alumno);  // Actualizar el registro en la base de datos
        if (resultado == 1) {
            JOptionPane.showMessageDialog(vista, "Alumno actualizado correctamente.");
            listar();  // Refrescar la tabla
            limpiarCampos();  // Limpiar los campos después de la actualización
            idAlumnoEditar = -1;  // Reiniciar la variable
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar el alumno.");
        }
    } else {
        JOptionPane.showMessageDialog(vista, "Debe seleccionar un alumno para actualizar.");
    }
}


  // Método para validar que solo se ingresen letras
private void validarSoloLetras(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
        evt.consume(); // No permitir caracteres que no sean letras o espacios
    }
}

// Método para validar que solo se ingresen números y que no se exceda el límite permitido
private void validarSoloNumeros(java.awt.event.KeyEvent evt, String textoActual, int maxLength) {
    char c = evt.getKeyChar();
    if (!Character.isDigit(c) || textoActual.length() >= maxLength) {
        evt.consume(); // No permitir más caracteres que el límite establecido o caracteres que no sean números
    }
}

    // Método para limpiar los campos de texto en la vista
    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtApellido().setText("");
        vista.getTxtDni().setText("");
        vista.getTxtTelefono().setText("");
    }
}
