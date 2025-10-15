package org.example.servicio;

import org.example.DAO.UsuarioDAO;
import org.example.modelo.Usuario;
import java.sql.SQLException;

public class UsuarioServicio {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void agregarUsuario(Usuario u) {
        try {
            if (u.getEdad() < 18) {
                System.out.println("Error: el usuario debe ser mayor de edad.");
                return;
            }

            UsuarioDAO.insertarUsuario(u);
            System.out.println("Usuario agregado: " + u.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al agregar usuario: " + e.getMessage());
        }
    }

    public void modificarUsuario(Usuario u) {
        try {
            UsuarioDAO.modificarUsuario(u);
            System.out.println("Usuario modificado: " + u.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al modificar usuario: " + e.getMessage());
        }
    }

    public void eliminarUsuario(String ci) {
        try {
            usuarioDAO.eliminarUsuario(ci);
            System.out.println("Usuario eliminado: ID " + ci);
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    public Usuario getUsuario(int id) {
        try {
            return UsuarioDAO.getUsuario(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener usuario: " + e.getMessage());
            return null;
        }
    }
}