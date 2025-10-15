package org.example.DAO;

import org.example.conexion.Singleton;
import org.example.modelo.*;
import org.example.seguridad.CifradoCesar;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UsuarioDAO {

    public static int insertarUsuario(Usuario usuario) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sqlusu = "INSERT INTO usuario (cedula, nombre, apellido, calle, nro_puerta, fecha_nacimiento, email, usuario, password, estado, id_itr) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        String sqltel = "INSERT INTO Usuario_Telefono (id_usuario, telefono) VALUES (?, ?);";

        // Verificar si ya existe un usuario con esa cédula
        String sqlVerificar = "SELECT id_usuario FROM usuario WHERE cedula = ?";
        try (PreparedStatement psVerificar = connection.prepareStatement(sqlVerificar)) {
            psVerificar.setString(1, usuario.getCedula());
            ResultSet rsVerificar = psVerificar.executeQuery();

            if (rsVerificar.next()) {
                // Ya existe, retornar el ID existente
                return rsVerificar.getInt("id_usuario");
            }
        }

        int idUsu = 0;

        try{
            PreparedStatement ps = connection.prepareStatement(sqlusu, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getCedula());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getCalle());
            ps.setString(5, usuario.getNumPuerta());
            ps.setDate(6, usuario.getFechaNacimiento());
            ps.setString(7, usuario.getEmail());
            ps.setString(8, usuario.getUsuario());
            ps.setString(9, usuario.getPassword());
            ps.setString(10, usuario.getEstado());
            ps.setInt(11, usuario.getItr().getId());

            ps.executeUpdate();

            // recuperar el id autogenerado
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                idUsu = rs.getInt(1);
            } else {
                throw new SQLException("No se pudo obtener el ID generado del usuario");
            }


            try (PreparedStatement psTel = connection.prepareStatement(sqltel)) {
                for (String tel : usuario.getTelefono()) {
                    psTel.setInt(1, idUsu);
                    psTel.setString(2, tel);
                    psTel.addBatch();
                }
                psTel.executeBatch();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idUsu;
    }

    public static int modificarUsuario(Usuario usuario) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE usuario SET nombre=?, apellido=?, calle=?, nro_puerta=?, fecha_nacimiento=?, " +
                "email=?, usuario=?, password=?, id_itr=? WHERE cedula=?";
        String sqltel = "update telefono SET telefono=? WHERE id_usuario=?";

        int idUsu = 0;

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getCalle());
            ps.setString(4, usuario.getNumPuerta());
            ps.setDate(5, usuario.getFechaNacimiento());
            ps.setString(6, usuario.getEmail());
            ps.setString(7, usuario.getUsuario());
            ps.setString(8, usuario.getPassword());
            ps.setInt(9, usuario.getItr().getId());
            ps.setString(10, usuario.getCedula());

            ps.executeUpdate();

            String select = "SELECT id_usuario FROM usuario WHERE cedula=?";
            try (PreparedStatement psSelect = connection.prepareStatement(select)) {
                psSelect.setString(1, usuario.getCedula());
                ResultSet rsSelect = psSelect.executeQuery();
                if (rsSelect.next()) {
                    idUsu = rsSelect.getInt("id_usuario");
                }else{
                    throw new SQLException("No se pudo obtener el ID del usuario");
                }
            }




            try (PreparedStatement psTel = connection.prepareStatement(sqltel)) {
                for (String tel : usuario.getTelefono()) {
                    psTel.setInt(1, idUsu);
                    psTel.setString(2, tel);
                    psTel.addBatch();
                }

            }


        } catch (SQLException e) {
            e.printStackTrace();

        }
        return idUsu;
    }

    public static void eliminarUsuario(String ci) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE usuario SET estado=? WHERE id_usuario=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "inactivo");
            ps.setString(2, ci);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static Usuario getUsuario(int id) throws SQLException {
        Connection conexion = Singleton.getInstance().getConnection();
        String sqlUsuario = "SELECT * FROM Usuario u LEFT JOIN ITR i ON u.ID_ITR = i.ID_ITR WHERE u.ID_Usuario=?";
        String sqlTelefonos = "SELECT telefono FROM Usuario_Telefono WHERE ID_Usuario=?";

        try (PreparedStatement ps = conexion.prepareStatement(sqlUsuario)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            int idusu = rs.getInt("id");
            String cedula = rs.getString("cedula");
            String nombre = rs.getString("nombre");
            String apellido = rs.getString("apellido");
            String calle = rs.getString("calle");
            String numPuerta = rs.getString("numPuerta");
            Date fechaNacimiento = rs.getDate("fechaNacimiento");
            String email = rs.getString("email");
            String usuario = rs.getString("usuario");
            String password = rs.getString("password");
            String estado = rs.getString("estado");
            ITR itr = null;
            int idItr = rs.getInt("ID_ITR");
            if (idItr != 0) {
                itr = new ITR(idItr, rs.getString("ubicacion"), rs.getString("region"), rs.getString("calle"), rs.getString("nro_puerta"), rs.getString("descripcion"));
            }
            LinkedList<String> telefonos = new LinkedList<>();
            try (PreparedStatement psTel = conexion.prepareStatement(sqlTelefonos)) {
                psTel.setInt(1, id);
                try (ResultSet rsTel = psTel.executeQuery()) {
                    while (rsTel.next()) {
                        telefonos.add(rsTel.getString("telefono"));
                    }
                }
            }

            return new Usuario(id, nombre, apellido, email, telefonos, calle, numPuerta,
                    cedula, itr, usuario, password, fechaNacimiento, estado);



        }
    }


    public Usuario login(String usuario, String password) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String usuCifrado = CifradoCesar.cifrar(usuario);
        String passwordCifrada = CifradoCesar.cifrar(password);

        String sql = "SELECT id_usuario, cedula, nombre, apellido, calle, nro_puerta, " +
                "fecha_nacimiento, email, usuario, password, estado " +
                "FROM usuario " +
                "WHERE usuario = ? AND password = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuCifrado);
            ps.setString(2, passwordCifrada);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idUsuario = rs.getInt("id_usuario");

                    // Verificar si es funcionario y obtener rol
                    String sqlFuncionario = "SELECT f.ID_Funcionario, r.ID_Rol, r.tipo_usuario " +
                            "FROM Funcionario f " +
                            "JOIN Rol r ON f.ID_Rol = r.ID_Rol " +
                            "WHERE f.ID_Usuario = ?";
                    try (PreparedStatement psFunc = connection.prepareStatement(sqlFuncionario)) {
                        psFunc.setInt(1, idUsuario);
                        try (ResultSet rsFunc = psFunc.executeQuery()) {
                            if (rsFunc.next()) {
                                Rol rol = new Rol(rsFunc.getInt("ID_Rol"),
                                        tipoUsuario.valueOf(rsFunc.getString("tipo_usuario")));
                                Funcionario func = new Funcionario();
                                func.setId(idUsuario);
                                func.setCedula(rs.getString("cedula"));
                                func.setNombre(rs.getString("nombre"));
                                func.setApellido(rs.getString("apellido"));
                                func.setCalle(rs.getString("calle"));
                                func.setNumPuerta(rs.getString("nro_puerta"));
                                func.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                                func.setEmail(rs.getString("email"));
                                func.setUsuario(rs.getString("usuario"));
                                func.setPassword(rs.getString("password"));
                                func.setEstado(rs.getString("estado"));
                                func.setRol(rol);
                                return func;
                            }
                        }
                    }

                    // Si no es funcionario, devolver Usuario normal
                    Usuario user = new Usuario();
                    user.setId(idUsuario);
                    user.setCedula(rs.getString("cedula"));
                    user.setNombre(rs.getString("nombre"));
                    user.setApellido(rs.getString("apellido"));
                    user.setCalle(rs.getString("calle"));
                    user.setNumPuerta(rs.getString("nro_puerta"));
                    user.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    user.setEmail(rs.getString("email"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setPassword(rs.getString("password"));
                    user.setEstado(rs.getString("estado"));
                    return user;
                }
            }
        }
        return null;
    }
    public Usuario buscarPorUsuario(String usuario) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String usuCifrado = CifradoCesar.cifrar(usuario);

        String sql = "SELECT * FROM usuario WHERE usuario = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, usuCifrado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setId(rs.getInt("id_usuario"));
                    user.setCedula(rs.getString("cedula"));
                    user.setNombre(rs.getString("nombre"));
                    user.setApellido(rs.getString("apellido"));
                    user.setCalle(rs.getString("calle"));
                    user.setNumPuerta(rs.getString("nro_puerta"));
                    user.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    user.setEmail(rs.getString("email"));
                    user.setUsuario(rs.getString("usuario"));
                    user.setPassword(rs.getString("password"));
                    user.setEstado(rs.getString("estado"));
                    return user;
                }
            }
        }
        return null;
    }


}