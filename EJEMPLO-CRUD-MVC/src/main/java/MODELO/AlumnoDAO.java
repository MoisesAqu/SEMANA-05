/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Método para listar alumnos
    public List<Alumno> listar() {
        List<Alumno> datos = new ArrayList<>();
        String sql = "SELECT * FROM Alumno";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt(1));
                alumno.setNombre(rs.getString(2));
                alumno.setApellido(rs.getString(3));
                alumno.setDni(rs.getString(4));
                alumno.setTelefono(rs.getString(5));
                datos.add(alumno);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar alumnos: " + e.getMessage());
        }
        return datos;
    }

    // Método para agregar un alumno
    public int agregar(Alumno alumno) {
        String sql = "INSERT INTO Alumno(nombre, apellido, dni, telefono) VALUES (?, ?, ?, ?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getDni());
            ps.setString(4, alumno.getTelefono());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al agregar alumno: " + e.getMessage());
            return 0;
        }
        return 1;
    }

    public int actualizar(Alumno alumno) {
        String sql = "UPDATE Alumno SET nombre=?, apellido=?, dni=?, telefono=? WHERE id=?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getDni());
            ps.setString(4, alumno.getTelefono());
            ps.setInt(5, alumno.getId());  // Actualizar usando el ID
            ps.executeUpdate();
            return 1;  // Indicar éxito
        } catch (SQLException e) {
            System.err.println("Error al actualizar alumno: " + e.getMessage());
            return 0;  // Indicar error
        }
    }



    
    public void eliminar(int id) {
        String sql = "DELETE FROM Alumno WHERE id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);  // Eliminar el alumno con el ID
            ps.executeUpdate();

            // Verificar si la tabla está vacía después de eliminar
            String checkCountSQL = "SELECT COUNT(*) FROM Alumno";
            ps = con.prepareStatement(checkCountSQL);
            rs = ps.executeQuery();
            rs.next();  // Mover el cursor al primer registro
            int count = rs.getInt(1);  // Obtener el número de registros

            if (count == 0) {
                // Si la tabla está vacía, resetear el IDENTITY
                String resetSQL = "DBCC CHECKIDENT ('Alumno', RESEED, 0)";
                ps = con.prepareStatement(resetSQL);
                ps.executeUpdate();
                System.out.println("IDENTITY reseed ejecutado correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar alumno: " + e.getMessage());
        }
    }
}

