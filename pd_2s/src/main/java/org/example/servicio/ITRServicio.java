package org.example.servicio;

import org.example.DAO.ITRDAO;
import org.example.modelo.ITR;

import java.sql.SQLException;
import java.util.List;

public class ITRServicio {

    public static List<ITR>  mostrarITR() {
        try{
            List<ITR> lista= ITRDAO.listar();
            return lista;
        } catch (SQLException e) {
            System.err.println("Error al obtener los ITR" + e.getMessage());
            return null;
        }
    }
}
