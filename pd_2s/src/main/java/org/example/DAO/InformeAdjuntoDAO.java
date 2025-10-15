package org.example.DAO;

import org.example.conexion.Singleton;
import org.example.modelo.InformeAdjunto;

import java.sql.*;
import java.util.LinkedList;

public class InformeAdjuntoDAO {


        public void insertarInformeAdjunto(InformeAdjunto obj) throws SQLException {
            Connection con = Singleton.getInstance().getConnection();
            String sql = "INSERT INTO informe_adjunto(nombre, confidencial, categoria, fecha_subida, ruta_archivo, tipo, id_estudiante) VALUES (?,?,?,?,?,?,?)";

            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, obj.getNombre());
                ps.setBoolean(2, obj.isConfidencial());
                ps.setString(3, obj.getCategoria());
                ps.setDate(4, obj.getFechaSubida());
                ps.setString(5, obj.getRutaArchivo());
                ps.setString(6, obj.getTipo());
                ps.setInt(7, obj.getId_estudiante());
                ps.executeUpdate();
                try(ResultSet rs = ps.getGeneratedKeys()) {
                    if(rs.next()) {
                        obj.setId_informe(rs.getInt(1));
                    }
                }

            }
        }

        public LinkedList<InformeAdjunto> consultarInformeAdjunto(int id) throws SQLException {
            LinkedList<InformeAdjunto> lista = new LinkedList<>();
            Connection con = Singleton.getInstance().getConnection();
            String sql = "SELECT * FROM informe_adjunto WHERE id_estudiante = ?";

            try(PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    int id_informe = rs.getInt("id_informe");
                    String nombre = rs.getString("nombre");
                    boolean confidencial = rs.getBoolean("confidencial");
                    String categoria = rs.getString("categoria");
                    Date fechaSubida = rs.getDate("fecha_subida");
                    String ruta_archivo = rs.getString("ruta_archivo");
                    String tipo = rs.getString("tipo");
                    int id_estudiante = rs.getInt("id_estudiante");

                    lista.add(new InformeAdjunto(id_informe, nombre, confidencial, categoria, fechaSubida, ruta_archivo, tipo, id_estudiante));


                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
            return lista;
        }

        public void eliminarInformeAdjunto(InformeAdjunto obj) throws SQLException {
            Connection con = Singleton.getInstance().getConnection();
            String sql = "DELETE FROM informe_adjunto WHERE id_estudiante = ?";
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, obj.getId_estudiante());
            }
        }

        public void actualizarInformeAdjunto(InformeAdjunto obj) throws SQLException {
            Connection con = Singleton.getInstance().getConnection();
            String sql = "UPDATE informe_adjunto SET nombre=?, confidencial=?, categoria=?, fecha_subida=?, ruta_archivo=?, tipo='?' WHERE id_estudiante = ?";

            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, obj.getNombre());
                ps.setBoolean(2, obj.isConfidencial());
                ps.setString(3, obj.getCategoria());
                ps.setDate(4, obj.getFechaSubida());
                ps.setString(5, obj.getRutaArchivo());
                ps.setString(6, obj.getTipo());
                ps.setInt(7, obj.getId_estudiante());
                ps.executeUpdate();

            }
        }
}
