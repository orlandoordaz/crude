package org.example.servicio;


import org.example.modelo.Instancia;
import org.example.DAO.InstanciaDAO;

import java.sql.SQLException;
import java.util.List;

public class InstanciaServicio {

    private final InstanciaDAO instanciaDAO = new InstanciaDAO();
    public void crearInstancia(Instancia instancia) {
        try {
            instanciaDAO.crearInstancia(instancia);
            System.out.println("Instancia creada: " + instancia.getTitulo() + " (ID: " + instancia.getIdInstancia() + ")");
        } catch (SQLException e) {
            System.err.println("Error al crear instancia: " + e.getMessage());
        }
    }

    public void modificarInstancia(Instancia instancia) {
        try {
            instanciaDAO.modificarInstancia(instancia);
            System.out.println("Instancia modificada: ID " + instancia.getIdInstancia());
        } catch (SQLException e) {
            System.err.println("Error al modificar instancia: " + e.getMessage());
        }
    }
    public Instancia getInstancia(int id) {
        try {
            return instanciaDAO.busquedaPorId(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener la instancia: " + e.getMessage());
            return null;
        }
    }

//    public List<Instancia> getInstanciasEstudiante(int idEstudiante) {
//        try {
//            return instanciaDAO.buscarPorIdEstudiante(idEstudiante);
//        } catch (SQLException e) {
//            System.err.println("Error al obtener instancias por estudiante: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Instancia> getInstanciasFuncionario(int idFuncionario) {
//        try {
//            return instanciaDAO.buscarPorIdFuncionario(idFuncionario);
//        } catch (SQLException e) {
//            System.err.println("Error al obtener instancias por funcionario: " + e.getMessage());
//            return null;
//        }
//    }
}
