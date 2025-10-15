package org.example.servicio;

import org.example.DAO.FuncionarioDAO;
import org.example.modelo.Funcionario;
import java.sql.SQLException;

public class FuncionarioServicio {

    private FuncionarioDAO funcionarioDAO;

    public FuncionarioServicio() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    // Alta de funcionario
    public  void altaFuncionario(Funcionario funcionario) {
        try {
            funcionarioDAO.agregarFuncionario(funcionario);
            System.out.println("Funcionario agregado correctamente: " + funcionario.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al agregar funcionario: " + e.getMessage());
        }
    }

    // Modificación de funcionario
    public void modificarFuncionario(Funcionario funcionario) {
        try {
            funcionarioDAO.modificarFuncionario(funcionario);
            System.out.println("Funcionario modificado correctamente: " + funcionario.getNombre());
        } catch (SQLException e) {
            System.err.println("Error al modificar funcionario: " + e.getMessage());
        }
    }

    // Buscar funcionario por ID
    public Funcionario buscarFuncionario(String ci) {
        try {
            return funcionarioDAO.getFuncionarioPorCi(ci);
        } catch (SQLException e) {
            System.err.println("Error al buscar funcionario: " + e.getMessage());
            return null;
        }
    }

    // Eliminar funcionario
    public void eliminarFuncionario(int idFuncionario) {
        try {
            funcionarioDAO.eliminarFuncionario(idFuncionario);
            System.out.println("Funcionario eliminado correctamente: ID " + idFuncionario);
        } catch (SQLException e) {
            System.err.println("Error al eliminar funcionario: " + e.getMessage());
        }
    }
}
