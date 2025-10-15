package org.example.servicio;

import org.example.DAO.RolDAO;
import org.example.modelo.Rol;
import org.example.modelo.tipoUsuario;

import java.sql.SQLException;

public class RolServicio {

    private final RolDAO rolDAO = new RolDAO();



    public void agregarRol(Rol rol) {
        try {
            rolDAO.insertRol(rol);
            System.out.println("Rol agregado: " + rol.getTipoUsuario());
        } catch (SQLException e) {
            System.err.println("Error al agregar rol: " + e.getMessage());
        }
    }

    public void modificarRol(Rol rol) {
        try {
            rolDAO.updateRol(rol);
            System.out.println("Rol modificado: " + rol.getTipoUsuario());
        } catch (SQLException e) {
            System.err.println("Error al modificar rol: " + e.getMessage());
        }
    }

    public void eliminarRol(int id) {
        try {
            rolDAO.deleteRol(id);
            System.out.println("Rol eliminado: " + id);
        } catch (SQLException e) {
            System.err.println("Error al eliminar rol: " + e.getMessage());
        }
    }

    public Rol getRol(int id) {
        try {
            return rolDAO.getRolById(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener rol: " + e.getMessage());
            return null;
        }
    }

}