package org.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import org.example.conexion.Singleton;
import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Grupo;
import org.example.modelo.ITR;


public class EstudianteDAO {


    public void agregarEstudiante(Estudiante estudiante) throws SQLException {
        int idUsuario = UsuarioDAO.insertarUsuario(estudiante);

        Connection connection = Singleton.getInstance().getConnection();
        String sql = "INSERT INTO estudiante (id_usuario, info_estado_salud, sistema_salud, motivo, obs_coment, obs_confidenciales, foto) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idUsuario);
            ps.setString(2, estudiante.getEstadoSalud());
            ps.setString(3, estudiante.getSistSalud());
            ps.setString(4, estudiante.getMotivoDerivacion());
            ps.setString(5, estudiante.getComentarios());
            ps.setString(6, estudiante.getObservacionesConfidenciales());
            ps.setString(7, estudiante.getFoto());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    estudiante.setIdEstudiante(rs.getInt(1));
                    estudiante.setId(idUsuario);
                }
            }
        }
    }




    public void modificarEstudiante(Estudiante estudiante) throws SQLException {
        int idUsu = UsuarioDAO.modificarUsuario(estudiante);

        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE Estudiante SET info_estado_salud=?, sistema_salud =?, motivo =?, obs_coment =?, obs_confidenciales =?, foto =? WHERE id_usuario=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, estudiante.getEstadoSalud());
            ps.setString(2, estudiante.getSistSalud());
            ps.setString(3, estudiante.getMotivoDerivacion());
            ps.setString(4, estudiante.getComentarios());
            ps.setString(5, estudiante.getObservacionesConfidenciales());
            ps.setString(6, estudiante.getFoto());


            ps.setInt(7 ,idUsu);

            ps.executeUpdate();
        }
    }

    public Estudiante getEstudiante(String cedula) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();

        String sql = "SELECT " +
                "e.id_estudiante, e.id_usuario, e.info_estado_salud, e.sistema_salud, " +
                "e.motivo, e.obs_coment, e.obs_confidenciales, e.foto, " +
                "u.cedula, u.nombre, u.apellido, u.calle, u.nro_puerta, " +
                "u.fecha_nacimiento, u.email, u.usuario, u.password, u.estado " +
                "FROM estudiante e " +
                "INNER JOIN usuario u ON e.id_usuario = u.id_usuario " +
                "WHERE u.cedula = ?";


        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Estudiante est = new Estudiante();

                    // Datos del estudiante
                    est.setIdEstudiante(rs.getInt("id_estudiante"));
                    est.setEstadoSalud(rs.getString("info_estado_salud"));
                    est.setSistSalud(rs.getString("sistema_salud"));
                    est.setMotivoDerivacion(rs.getString("motivo"));
                    est.setComentarios(rs.getString("obs_coment"));
                    est.setObservacionesConfidenciales(rs.getString("obs_confidenciales"));
                    est.setFoto(rs.getString("foto"));

                    // Datos del usuario (heredados)
                    est.setId(rs.getInt("id_usuario"));
                    est.setCedula(rs.getString("cedula"));
                    est.setNombre(rs.getString("nombre"));
                    est.setApellido(rs.getString("apellido"));
                    est.setCalle(rs.getString("calle"));
                    est.setNumPuerta(rs.getString("nro_puerta"));
                    est.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
                    est.setEmail(rs.getString("email"));
                    est.setUsuario(rs.getString("usuario"));
                    est.setPassword(rs.getString("password"));
                    est.setEstado(rs.getString("estado"));


                    // Obtener teléfonos
                    est.setTelefono(obtenerTelefonos(rs.getInt("id_usuario")));

                    return est;
                }
            }
        }
        return null; // No se encontró estudiante con esa cédula
    }

    private LinkedList<String> obtenerTelefonos(int idUsuario) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        LinkedList<String> telefonos = new LinkedList<>();

        String sql = "SELECT telefono FROM Usuario_Telefono WHERE id_usuario = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    telefonos.add(rs.getString("telefono"));
                }
            }
        }

        return telefonos;
    }

    public static void bajaEstudiante(String ci) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE usuario SET estado = 'inactivo' WHERE cedula = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ci);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerCamposConfidenciales(int id) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "SELECT obs_coment, obs_confidenciales " +
                "FROM estudiante " +
                "WHERE id_estudiante = ?";


        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return "Estudiante id :" + id + ", \n Comentarios: " + rs.getString("obs_coment") + ", \n Comentarios confidenciales: " + rs.getString("obs_confidenciales");

            } else {
                System.out.println("No se encontró estudiante con id: " + id);
                return " ";
            }

        } catch (SQLException e) {
            System.err.println("Error al consultar estudiante: " + e.getMessage());
            e.printStackTrace();
            return " ";
        }
    }
    public void actualizarObservacionConfidencial(int idEstudiante, String nuevaObservacion) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE estudiante SET obs_confidenciales = ? WHERE id_estudiante = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nuevaObservacion);
            ps.setInt(2, idEstudiante);
            ps.executeUpdate();
        }
    }

    public Grupo crearCarreraYGrupo(Carrera carrera, Grupo grupo) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();

        String sqlCarreraInsert = "INSERT INTO Carrera (nom_carrera) VALUES (?) RETURNING id_carrera";
        try (PreparedStatement psInsert = connection.prepareStatement(sqlCarreraInsert)) {
            psInsert.setString(1, carrera.getNombreCarrera());
            ResultSet rsInsert = psInsert.executeQuery();
            if(rsInsert.next()) {
                carrera.setIdCarrera(rsInsert.getInt(1));
            }
        }

        String sqlGrupoInsert = "INSERT INTO Grupo (nom_grupo, id_carrera) VALUES (?, ?) RETURNING id_grupo";
        try (PreparedStatement psInsert = connection.prepareStatement(sqlGrupoInsert)) {
            psInsert.setString(1, grupo.getNombreGrupo());
            psInsert.setInt(2, carrera.getIdCarrera());
            ResultSet rsInsert = psInsert.executeQuery();
            if(rsInsert.next()) {
                grupo.setIdGrupo(rsInsert.getInt(1));
            }
        }

        //Asociar la carrera al grupo
        grupo.setCarrera(carrera);

        return grupo;
    }
    public void asociarGrupoEstudiante(Estudiante estudiante, Grupo grupo) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();

        String sql = "INSERT INTO Pertenece (id_estudiante, id_grupo) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.setInt(2, grupo.getIdGrupo());
            ps.executeUpdate();
        }
    }
}
