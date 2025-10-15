package org.example.DAO;
import org.example.DAO.UsuarioDAO;
import org.example.conexion.Singleton;
import org.example.modelo.Funcionario;
import org.example.modelo.Rol;
import org.example.modelo.Usuario;
import org.example.modelo.tipoUsuario;

import java.sql.*;

public class FuncionarioDAO {

    public void agregarFuncionario(Funcionario funcionario) throws SQLException {
        int id = UsuarioDAO.insertarUsuario(funcionario);
        int idrol = funcionario.getRol().getId(); // usar el rol existente

        // Crear registro en Funcionario
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "INSERT INTO Funcionario (ID_Usuario, ID_Rol) VALUES (?, ?)";

        try (PreparedStatement pr = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setInt(1, id);
            pr.setInt(2, idrol);
            pr.executeUpdate();

            try (ResultSet rs = pr.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setIdFuncionario(rs.getInt(1));
                }
            }
        }
    }

    public void modificarFuncionario (Funcionario funcionario) throws SQLException {
        // Modifico usuario
        UsuarioDAO.modificarUsuario(funcionario);

        // Modifico funcionario (rol)
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE Funcionario SET ID_Rol=? WHERE ID_Funcionario=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, funcionario.getRol().getId());
            ps.setInt(2, funcionario.getIdFuncionario());
            ps.executeUpdate();
        }
    }

    public Funcionario getFuncionarioPorCi (String ci) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "SELECT f.id_funcionario, r.tipo_usuario, u.nombre, u.apellido " +
                "FROM Funcionario f " +
                "JOIN Rol r ON f.ID_Rol = r.ID_Rol " +
                "JOIN Usuario u ON f.id_Usuario = u.id_Usuario " +
                "WHERE u.cedula = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ci);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idfunc = rs.getInt("id_funcionario");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");

                    Rol rol = new Rol();
                    rol.setTipoUsuario(tipoUsuario.valueOf(rs.getString("tipo_usuario")));

                    return new Funcionario(idfunc, nombre, apellido, rol);
                }
            }
        }
        return null;
    }

    public void eliminarFuncionario ( int idFuncionario) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "DELETE FROM Funcionario WHERE ID_Funcionario=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFuncionario);
            ps.executeUpdate();
        }
    }


}
