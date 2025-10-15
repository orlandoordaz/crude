package org.example.DAO;

import org.example.conexion.Singleton;
import org.example.modelo.ITR;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ITRDAO {

    public static List<ITR> listar() throws SQLException {
        List<ITR> lista = new ArrayList<>();

        Connection con = Singleton.getInstance().getConnection();
        String sql = "SELECT * FROM ITR";
        try(Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id_itr");
                String ubi = rs.getString("ubicacion");
                String region = rs.getString("region");
                String calle = rs.getString("calle");
                String nro = rs.getString("nro_puerta");
                String desc = rs.getString("descripcion");
                lista.add(new ITR(id, ubi, region, calle, nro, desc));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

}
