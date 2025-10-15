package org.example.servicio;

import org.example.DAO.EstudianteDAO;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Grupo;

import java.sql.SQLException;

public class EstudianteServicio {

    private EstudianteDAO estudianteDAO = new EstudianteDAO();

    // Alta estudiante: persiste en la DB
    public void altaEstudiante(Estudiante est) {
        try {
            estudianteDAO.agregarEstudiante(est);
            System.out.println("Estudiante dado de alta: " + est.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al agregar estudiante: " + e.getMessage());
        }
    }

    // Baja lógica: obtenemos el estudiante, lo marcamos inactivo y actualizamos
    public void bajaLogicaEstudiante(String ci) {
        try {
            Estudiante est = estudianteDAO.getEstudiante(ci);
            if (est != null) {
                if (est.estadoActivo()) {
                    estudianteDAO.bajaEstudiante(ci);
                    System.out.println("Estudiante dado de baja (lógica): " + est.getNombre());
                } else {
                    System.out.println("El estudiante con cédula " + ci + " ya se encuentra inactivo.");
                }
            } else {
                System.out.println("No se encontró estudiante con cédula " + ci);
            }
        } catch (SQLException e) {
            System.err.println("Error al dar baja lógica: " + e.getMessage());
        }
    }

    // Modificación: persiste los cambios en la DB
    public void modificarEstudiante(Estudiante est) {
        try {
            estudianteDAO.modificarEstudiante(est);
            System.out.println("Estudiante modificado: " + est.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al modificar estudiante: " + e.getMessage());
        }
    }

    // Búsqueda por ID
    public Estudiante buscarEstudiante(String ci) {
        try {
            return estudianteDAO.getEstudiante(ci);
        } catch (SQLException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
            return null;
        }
    }


    public void actualizarObsConfidenciales(int idEstudiante, String nuevaObservacion) {
        try {
            estudianteDAO.actualizarObservacionConfidencial(idEstudiante, nuevaObservacion);
            System.out.println("Observaciones confidenciales actualizadas.");
        } catch (SQLException e) {
            System.err.println("Error al actualizar observaciones confidenciales: " + e.getMessage());
        }
    }


    public String verDatosConf(int id) throws SQLException {
        return estudianteDAO.obtenerCamposConfidenciales(id);
    }


    public Grupo crearCarreraGrupo(Carrera carrera, Grupo grupo) {
        try {
            Grupo grupoCreado = estudianteDAO.crearCarreraYGrupo(carrera, grupo);
            return grupoCreado;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear carrera y grupo", e);
        }
    }

    public void asociarEstudianteGrupo(Estudiante estudiante, Grupo grupo) {
        try {
            estudianteDAO.asociarGrupoEstudiante(estudiante, grupo);
        } catch (SQLException e) {
            throw new RuntimeException("Error al asociar el estudiante con el grupo", e);
        }
    }

}
