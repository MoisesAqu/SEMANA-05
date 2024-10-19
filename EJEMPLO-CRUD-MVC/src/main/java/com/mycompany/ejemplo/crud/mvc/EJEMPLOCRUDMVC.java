/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ejemplo.crud.mvc;

import CONTROLADOR.Controlador;
import MODELO.AlumnoDAO;
import MODELO.Conexion;
import VISTA.Vista;

/**
 *
 * @author ADMIN
 */
public class EJEMPLOCRUDMVC {

    public static void main(String[] args) {
        Vista vista = new Vista();

        // Instanciamos el modelo (DAO) para manejar los datos de los alumnos
        AlumnoDAO modelo = new AlumnoDAO();

        // Creamos el controlador, pasándole la vista y el modelo
        Controlador controlador = new Controlador(vista, modelo);

        // Hacemos visible la ventana principal (Vista)
        vista.setVisible(true);
        
        // Usamos la nueva instancia de Conexion
        Conexion conexion = new Conexion();
        conexion.getConnection(); // Establecer la conexión con la base de datos

        
    }
}
