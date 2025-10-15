package org.example.Main;

import org.example.DAO.UsuarioDAO;
import org.example.modelo.Funcionario;
import org.example.modelo.Usuario;
import org.example.modelo.tipoUsuario;

import java.sql.SQLException;
import java.util.Scanner;

public class Login {
    private final UsuarioDAO usuarioDAO;
    private final Scanner sc;

    public Login() {
        this.usuarioDAO = new UsuarioDAO();
        this.sc = new Scanner(System.in);
    }

    public Usuario autenticarUsuario() {
        System.out.println("=== LOGIN ===");
        Usuario usuarioLogueado = null;
        int intentos = 0;
        final int maxIntentos = 3;

        while (usuarioLogueado == null && intentos < maxIntentos) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();
            System.out.print("Contraseña: ");
            String password = sc.nextLine();

            try {
                // Verificar si el usuario existe
                Usuario usuarioExistente = usuarioDAO.buscarPorUsuario(usuario);
                if (usuarioExistente != null && "inactivo".equalsIgnoreCase(usuarioExistente.getEstado())) {
                    System.out.println("Usuario inactivo.");
                    return null;
                }

                // Intentar login
                usuarioLogueado = usuarioDAO.login(usuario, password);

                if (usuarioLogueado != null) {
                    System.out.println("Login exitoso!");
                    System.out.println("Bienvenido/a: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido());
                    return usuarioLogueado;
                } else {
                    intentos++;
                    System.out.println("Usuario no existe o contraseña incorrecta.");
                    if (intentos < maxIntentos) {
                        System.out.println("Intentos restantes: " + (maxIntentos - intentos));
                    } else {
                        System.out.println("Se alcanzó el límite de intentos. Acceso bloqueado.");
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }

        return null;
    }
}