package org.example.DAO;
import org.example.conexion.Singleton;
import org.example.modelo.Estudiante;
import org.example.modelo.Funcionario;
import org.example.modelo.Instancia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstanciaDAO {

    public void crearInstancia(Instancia instancia) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "INSERT INTO Instancia (titulo, comentario_confid, fecha_hora, comentario) " +
                "VALUES (?, ?, ?, ?)";
        String sqlEst = "INSERT INTO Instancia_Estudiante (id_instancia, id_estudiante) VALUES (?, ?)";
        String sqlFun = "INSERT INTO Instancia_Funcionario (id_instancia, id_funcionario) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, instancia.getComConfidencial());
            ps.setTimestamp(3, instancia.getFechaHora());
            ps.setString(4, instancia.getComenatario());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    instancia.setIdInstancia(rs.getInt(1));
                }
            }
        }

        try (PreparedStatement psEst = connection.prepareStatement(sqlEst)) {
            psEst.setInt(1, instancia.getIdInstancia());
            psEst.setInt(2, instancia.getEstudiante().getIdEstudiante());
            psEst.executeUpdate();
        }

        try (PreparedStatement psFun = connection.prepareStatement(sqlFun)) {
            psFun.setInt(1, instancia.getIdInstancia());
            psFun.setInt(2, instancia.getFuncionario().getIdFuncionario());
            psFun.executeUpdate();
        }
    }

    private Instancia mapearInstancia(ResultSet rs) throws SQLException {
        Instancia instancia = new Instancia();
        instancia.setIdInstancia(rs.getInt("id_instancia"));
        instancia.setTitulo(rs.getString("titulo"));
        instancia.setComConfidencial(rs.getString("comentario_confid"));
        instancia.setFechaHora(rs.getTimestamp("fecha_hora"));
        instancia.setComenatario(rs.getString("comentario"));
        return instancia;
    }

    public Instancia busquedaPorId(int id) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "SELECT * FROM Instancia WHERE id_instancia = ?";
        Instancia instancia = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                instancia = mapearInstancia(rs);
            }
        }
        return instancia;
    }

//    public List<Instancia> buscarPorIdEstudiante(int idEstudiante) throws SQLException {
//        Connection connection = Singleton.getInstance().getConnection();
//        String sql = "SELECT * FROM Instancia WHERE id_estudiante = ? AND activo = 1";
//        List<Instancia> lista = new ArrayList<>();
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, idEstudiante);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                lista.add(mapearInstancia(rs));
//            }
//        }
//        return lista;
//    }


//    public List<Instancia> buscarPorIdFuncionario(int idFuncionario) throws SQLException {
//        Connection connection = Singleton.getInstance().getConnection();
//        String sql = "SELECT * FROM Instancia WHERE id_funcionario = ? AND activo = 1";
//        List<Instancia> lista = new ArrayList<>();
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setInt(1, idFuncionario);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                lista.add(mapearInstancia(rs));
//            }
//        }
//        return lista;
//    }


    public void modificarInstancia(Instancia instancia) throws SQLException {
        Connection connection = Singleton.getInstance().getConnection();
        String sql = "UPDATE Instancia SET titulo=?, comentario_confid=?, fecha_hora=?, comentario=? " +
                "WHERE id_instancia=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, instancia.getTitulo());
            ps.setString(2, instancia.getComConfidencial());
            ps.setTimestamp(3, instancia.getFechaHora());
            ps.setString(4, instancia.getComenatario());
            ps.setInt(5, instancia.getIdInstancia());
            ps.executeUpdate();
        }
    }

    //public void eliminar(int id) throws SQLException { //consulta sobre Estado (recordatorio)
    //}

//    public List<Instancia> buscarPorRangoFechas(Timestamp fechaInicio, Timestamp fechaFin) throws SQLException {
//    }

}