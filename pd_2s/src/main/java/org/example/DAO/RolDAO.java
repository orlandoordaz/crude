package org.example.DAO;

import org.example.conexion.Singleton;
import org.example.modelo.Rol;
import org.example.modelo.tipoUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RolDAO {

    public void insertRol(Rol rol) throws SQLException {
        Connection con = Singleton.getInstance().getConnection();
        String sql = "insert into rol (tipo_usuario) values (?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getTipoUsuario().name());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateRol(Rol rol) throws SQLException {
        Connection con = Singleton.getInstance().getConnection();
        String sql = "update rol set tipo_usuario = ? where id_rol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rol.getTipoUsuario().name());
            ps.setInt(2, rol.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRol(int id) throws SQLException {
        Connection con = Singleton.getInstance().getConnection();
        String sql = "delete from rol where id_rol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 0) {
                System.out.println("No se encontró ningún rol con ID: " + id);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Rol getRolById(int id) throws SQLException {
        Connection con = Singleton.getInstance().getConnection();
        String sql = "select * from rol where id_rol = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Rol rol = new Rol();
                rol.setId(rs.getInt("id_rol"));
                rol.setTipoUsuario(tipoUsuario.valueOf(rs.getString("tipo_usuario")));
                return rol;
            }

            // Si no encuentra nada, retorna null
            return null;
        }
    }



}
