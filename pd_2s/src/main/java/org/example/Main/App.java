package org.example.Main;

import org.example.DAO.UsuarioDAO;
import org.example.conexion.Singleton;
import org.example.modelo.*;
import org.example.servicio.*;
import org.example.seguridad.ValidadorCedula;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final EstudianteServicio estudianteServicio = new EstudianteServicio();
    private final FuncionarioServicio funcionarioServicio = new FuncionarioServicio();
    private final RolServicio rolServicio = new RolServicio();

    private static final InstanciaServicio instanciaServicio = new InstanciaServicio();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // 1. Conexión a la base de datos
        try {
            Singleton con = Singleton.getInstance();
            System.out.println("Conexión exitosa: " + con.getConnection());
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos:");
            e.printStackTrace();
            return;
        }

        // 2. Proceso de autenticación usando la nueva clase Login
        Login login = new Login();
        Usuario usuarioLogueado = login.autenticarUsuario();

        // 3. Si el login fue exitoso, se determina el menú a mostrar
        if (usuarioLogueado != null) {
            App app = new App();
            try {
                // Determinar menú según el tipo de usuario y su rol
                if (usuarioLogueado instanceof Funcionario) {
                    Funcionario func = (Funcionario) usuarioLogueado;
                    tipoUsuario tipo = func.getRol().getTipoUsuario();

                    switch (tipo) {
                        case ADMINISTRADOR:
                            app.menuAdmin();
                            break;
                        case ANALISTA:
                            app.menuAnalista();
                            break;
                        case FUNCIONARIO:
                            app.menuFuncionario();
                            break;
                        case PSICOPEDAGOGO:
                            app.menuPsicopedagogo();
                            break;
                        default:
                            System.out.println("Rol no reconocido.");
                            break;
                    }
                } else {
                    // Si no es funcionario, se asume que es estudiante
                    menuEstudiante();
                }
            } catch (SQLException e) {
                System.out.println("Ocurrió un error en la aplicación: " + e.getMessage());
                e.printStackTrace();
            }
        }
        // Si el login no fue exitoso (usuarioLogueado es null), el programa termina.
    }


    //menu funcionario

    public void menuFuncionario() throws SQLException {
        int option;
        do {
            System.out.println("--- Menú Funcionario ---");
            System.out.println("1. Buscar Estudiante");
            System.out.println("2. Buscar Funcionario");
            System.out.println("3. Buscar Rol");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    buscarEstudiante();
                    break;
                case 2:
                    System.out.println("Funcionalidad en desarrollo");
                    ;
                    break;
                case 3:
                    consultarRol();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    menuFuncionario();
            }
        } while (option != 4);
    }

    //menu estudiante

    public static void menuEstudiante() {
        int opcion;
        System.out.println("\n--- Menú Estudiante ---");
        System.out.println("1. Ver mis datos");
        System.out.println("2. Modificar mis datos");
        System.out.println("3. Salir");

        opcion = sc.nextInt();
        sc.nextLine();

        do {
            switch (opcion) {
                case 1:
                    buscarEstudiante();
                    break;
                case 2:
                    modificarEstudiante();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    menuEstudiante();
                    break;
            }
        } while (opcion != 3);
    }

    //menu admin
    public void menuAdmin() throws SQLException {
        int option;
        do {
            System.out.println("\n--- Menú Administrador ---");
            System.out.println("1. Gestión de estudiantes");
            System.out.println("2. Gestión de roles");
            System.out.println("3. Gestion de funcionarios");
            System.out.println("4. Gestion de instancia");
            System.out.println("5. ver datos confidenciales");
            System.out.println("6. Modificar datos confidenciales");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    gestEst();
                    break;
                case 2:
                    gestRoles();
                    break;
                case 3:
                    gestFuncionarios();
                    break;
                case 4:
                    gestInstancia();
                case 5:
                    visualizarDatosConfidenciales();
                    break;
                case 6:
                    modificarObsConfidenciales();
                    break;
                case 7:
                    System.out.println("Saliendo del sistema.");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    menuAdmin();
            }
        } while (option != 6);
    }

    //menu psdicopedagogo
    public void menuPsicopedagogo() throws SQLException {
        int opcion;
        do {
            System.out.println("--- Gestion de psicopedagogo ---");
            System.out.println("1. Gestion de estudiantes");
            System.out.println("2. Visualizar campos confidenciales");
            System.out.println("3. Modificar datos confidenciales");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    gestEst();
                    break;
                case 2:
                    visualizarDatosConfidenciales();
                    break;
                case 3:
                    modificarObsConfidenciales();
                    break;
                case 4:
                    System.out.println("Saliendo...");
                    ;
                    break;
            }
        } while (opcion != 4);
    }

    //menu analista
    public void menuAnalista() throws SQLException {
        int option;
        do {
            System.out.println("\n--- Menú Analista ---");
            System.out.println("1. Gestión de estudiantes");
            System.out.println("2. Gestión de roles");
            System.out.println("3. Gestión de funcionarios");
            System.out.println("4. Salir");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    gestEst();
                    break;
                case 2:
                    gestRoles();
                    break;
                case 3:
                    gestFuncionarios();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    menuAnalista();
            }
        } while (option != 4);
    }

    //menu gestion funcionarios
    private void gestFuncionarios() {
        int option;
        do {
            System.out.println("\n--- Gestión de Funcionarios ---");
            System.out.println("1. Alta de funcionarios");
            System.out.println("2. Baja lógica de funcionarios");
            System.out.println("3. Modificación de funcionarios");
            System.out.println("4. Buscar funcionario");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    altaFuncionario();
                    break;
                case 2:
                    System.out.println("Funcionalidad en desarrollo.");
                    break;
                case 3:
                    System.out.println("Funcionalidad en desarrollo.");
                    break;
                case 4:
                    System.out.println("Funcionalidad en desarrollo.");
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 6);
    }

    private void altaFuncionario() {
        Funcionario func = new Funcionario();
        System.out.println("--- Alta de Funcionario ---");

        String cedula;
        boolean cedulaValida = false;

        while (!cedulaValida) {
            System.out.print("Cédula: ");
            cedula = sc.nextLine();

            if (ValidadorCedula.esValida(cedula)) {
                func.setCedula(cedula);
                cedulaValida = true;
            } else {
                System.out.println("Cédula inválida. Por favor, intente nuevamente.");

            }
        }

        System.out.print("Nombre: ");
        func.setNombre(sc.nextLine());

        System.out.print("Apellido: ");
        func.setApellido(sc.nextLine());

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        try {
            LocalDate fecha = LocalDate.parse(sc.nextLine());
            func.setFechaNacimiento(java.sql.Date.valueOf(fecha));


            int edad = LocalDate.now().getYear() - fecha.getYear();
            if (fecha.plusYears(edad).isAfter(LocalDate.now())) {
                edad--;
            }

            if (edad < 18) {
                System.out.println(" Error: el funcionario debe ser mayor de edad.");
                return;
            }

        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
            return;
        }

        System.out.print("Calle: ");
        func.setCalle(sc.nextLine());

        System.out.print("N° Puerta: ");
        func.setNumPuerta(sc.nextLine());

        System.out.println("=== Agregar Teléfono ===");
        System.out.print("Teléfonos: ");
        LinkedList<String> tel = new LinkedList<>();
        boolean x;
        do {
            System.out.print("Ingrese número de teléfono: ");
            tel.add(sc.nextLine());
            System.out.print("¿Desea agregar otro número? (Y/N): ");
            x = sc.nextLine().equalsIgnoreCase("y");
        } while (x);
        func.setTelefono(tel);

        mostraritr();
        func.setItr(crearITRsel());

        System.out.println("Email: ");
        func.setEmail(sc.nextLine());

        System.out.println("Usuario: ");
        func.setUsuario(sc.nextLine());

        System.out.println("Password: ");
        func.setPassword(sc.nextLine());

        System.out.println("Seleccione un rol:");
        tipoUsuario[] tipos = tipoUsuario.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }
        System.out.print(": ");
        int opcion = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        Rol rolExistente = rolServicio.getRolPorTipo(tipos[opcion - 1]);
        if (rolExistente != null) {
            func.setRol(rolExistente);
        } else {
            Rol nuevoRol = new Rol();
            nuevoRol.setTipoUsuario(tipos[opcion - 1]);
            rolServicio.agregarRol(nuevoRol);
            func.setRol(nuevoRol);
        }


        func.setEstado("Activo");
        funcionarioServicio.altaFuncionario(func);
        System.out.println("Funcionario agregado correctamente.");

    }

    // ---------------- GESTIÓN DE ESTUDIANTES ----------------
    private void gestEst() throws SQLException {
        int option;
        do {
            System.out.println("\n--- Gestión de Estudiantes ---");
            System.out.println("1. Alta de estudiantes");
            System.out.println("2. Baja lógica de estudiantes");
            System.out.println("3. Modificación de estudiantes");
            System.out.println("4. Adjuntar documentación médica");
            System.out.println("5. Buscar estudiante");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    altaEstudiante();
                    break;
                case 2:
                    bajaEstudiante();
                    break;
                case 3:
                    modificarEstudiante();
                    break;
                case 4:
                    System.out.println("Funcionalidad en desarrollo");
                    break;
                case 5:
                    buscarEstudiante();
                    break;
                case 6:
                    System.out.println("Volviendo al menú principal.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 6);
    }

    private void altaEstudiante() {
        Estudiante est = new Estudiante();

        System.out.println("--- Alta de Estudiante ---");

        String cedula;
        boolean cedulaValida = false;

        while (!cedulaValida) {
            System.out.print("Cédula: ");
            cedula = sc.nextLine();

            if (ValidadorCedula.esValida(cedula)) {
                est.setCedula(cedula);
                cedulaValida = true;
            } else {
                System.out.println("Cédula inválida. Por favor, intente nuevamente.");

            }
        }

        System.out.print("Nombre: ");
        est.setNombre(sc.nextLine());

        System.out.print("Apellido: ");
        est.setApellido(sc.nextLine());

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        try {
            LocalDate fecha = LocalDate.parse(sc.nextLine());
            est.setFechaNacimiento(java.sql.Date.valueOf(fecha));
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
        }

        System.out.print("Calle: ");
        est.setCalle(sc.nextLine());

        System.out.print("N° Puerta: ");
        est.setNumPuerta(sc.nextLine());

        System.out.println("=== Agregar Teléfono ===");
        System.out.print("Teléfonos: ");
        LinkedList<String> tel = new LinkedList<>();
        boolean x;
        do {
            System.out.print("Ingrese número de teléfono: ");
            tel.add(sc.nextLine());
            System.out.print("¿Desea agregar otro número? (Y/N): ");
            x = sc.nextLine().equalsIgnoreCase("y");
        } while (x);
        est.setTelefono(tel);

        System.out.println("Email: ");
        est.setEmail(sc.nextLine());

        System.out.println("Usuario: ");
        est.setUsuario(sc.nextLine());

        System.out.println("Password: ");
        est.setPassword(sc.nextLine());

        System.out.print("Sistema de salud: ");
        est.setSistSalud(sc.nextLine());

        System.out.print("Motivo de derivación: ");
        est.setMotivoDerivacion(sc.nextLine());

        System.out.print("Estado de salud: ");
        est.setEstadoSalud(sc.nextLine());

        System.out.print("Observaciones: ");
        est.setComentarios(sc.nextLine());

        System.out.print("Observaciones confidenciales: ");
        est.setObservacionesConfidenciales(sc.nextLine());

        mostraritr();
        est.setItr(crearITRsel());

        Carrera carrera = new Carrera();
        System.out.print("Nombre de la carrera: ");
        carrera.setNombreCarrera(sc.nextLine());
        //Crea Grupo
        Grupo grupo = new Grupo();
        System.out.print("Nombre del grupo: ");
        grupo.setNombreGrupo(sc.nextLine());
        //Asocia la carrera con el grupo (a posterior se asocia el grupo con el Estudiante)
        Grupo grupoCreado = estudianteServicio.crearCarreraGrupo(carrera, grupo);
        est.setGrupo(grupoCreado);
        est.setCarrera(grupoCreado.getCarrera());

        System.out.print("Foto: ");
        est.setFoto(sc.nextLine());
        est.setEstado("Activo");
        estudianteServicio.altaEstudiante(est);
        //Se asocia Grupo con Estudiante
        estudianteServicio.asociarEstudianteGrupo(est, grupoCreado);
        System.out.println("Estudiante agregado correctamente.");
    }

    private void bajaEstudiante() {
        System.out.print("Ingrese el estudiante a dar de baja: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            estudianteServicio.bajaLogicaEstudiante(String.valueOf(id));
            System.out.println("Estudiante dado de baja correctamente.");
        } catch (Exception e) {
            System.out.println("Error al dar de baja al estudiante: " + e.getMessage());
        }
    }

    private static void modificarEstudiante() {
        System.out.print("Ingrese cedula del estudiante a modificar: ");
        String ci = sc.nextLine();
        if (!ValidadorCedula.esValida(ci)) {
            System.out.println("Cédula inválida.");
            return;
        }
        Estudiante est = estudianteServicio.buscarEstudiante(ci);
        if (est == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.println("Modificando estudiante: " + est.getNombre());

        System.out.println("¿Qué datos quiere modificar?");
        System.out.println("1 - Nombre");
        System.out.println("2 - Apellido");
        System.out.println("3 - Dirección");
        System.out.println("4 - Teléfono");
        System.out.println("5 - Email");
        System.out.println("6 - Sistema de salud");
        System.out.println("7 - Estado de salud");
        System.out.println("8 - Fecha de nacimiento");
        System.out.println("9 - Foto");
        System.out.println("10 - Motivo");
        System.out.println("11 - Usuario");
        System.out.println("12 - Password");
        System.out.println("0 - Salir");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Nuevo nombre: ");
                est.setNombre(sc.nextLine());
                break;

            case 2:
                System.out.print("Nuevo apellido: ");
                est.setApellido(sc.nextLine());
                break;

            case 3:
                System.out.print("Nueva calle: ");
                est.setCalle(sc.nextLine());
                System.out.print("Nuevo número de puerta: ");
                est.setNumPuerta(sc.nextLine());
                break;

            case 4:
                System.out.println("=== Agregar Teléfono ===");
                LinkedList<String> tel = new LinkedList<>();
                boolean x;
                do {
                    System.out.print("Ingrese el nuevo número de teléfono: ");
                    tel.add(sc.nextLine());
                    System.out.print("¿Desea agregar otro número? (Y/N): ");
                    x = sc.nextLine().equalsIgnoreCase("y");
                } while (x);
                est.setTelefono(tel);
                break;

            case 5:
                System.out.print("Nuevo email: ");
                est.setEmail(sc.nextLine());
                break;

            case 6:
                System.out.print("Nuevo sistema de salud: ");
                est.setSistSalud(sc.nextLine());
                break;

            case 7:
                System.out.print("Nuevo estado de salud: ");
                est.setEstadoSalud(sc.nextLine());
                break;

            case 8:
                System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
                try {
                    LocalDate fecha = LocalDate.parse(sc.nextLine());
                    est.setFechaNacimiento(java.sql.Date.valueOf(fecha));
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido. Use YYYY-MM-DD.");
                }
                break;

            case 9:
                System.out.print("Nueva foto: ");
                est.setFoto(sc.nextLine());
                break;

            case 10:
                System.out.print("Nuevo motivo: ");
                est.setMotivoDerivacion(sc.nextLine());
                break;

            case 11:
                System.out.print("Nuevo nombre de usuario: ");
                est.setUsuario(sc.nextLine());
                break;

            case 12:
                System.out.print("Nueva contraseña: ");
                est.setPassword(sc.nextLine());
                break;

            case 0:
                System.out.println("Saliendo sin modificar...");
                return;

            default:
                System.out.println("Opción inválida");
                return;
        }

        estudianteServicio.modificarEstudiante(est);
    }

    public void visualizarDatosConfidenciales() throws SQLException {
        System.out.println("Se requiere verificar usuario");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogueado = null;
        int intentos = 0;
        int maxIntentos = 3;

        while (usuarioLogueado == null && intentos < maxIntentos) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();

            System.out.print("Contraseña: ");
            String password = sc.nextLine();


            try {
                usuarioLogueado = usuarioDAO.login(usuario, password);

                if (usuarioLogueado != null) {
                    System.out.println("Login exitoso!");
                    System.out.println("Bienvenido/a: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido());


                    System.out.println("Ingrese el id del estudiante: ");
                    int id = sc.nextInt();
                    System.out.println(estudianteServicio.verDatosConf(id));

                } else {
                    intentos++;
                    System.out.println("Usuario o contraseña incorrectos.");

                    if (intentos < maxIntentos) {
                        System.out.println("Intentos restantes: " + (maxIntentos - intentos));
                    } else {
                        System.out.println("Se alcanzó el límite de intentos. Acceso bloqueado.");
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
                e.printStackTrace();
            }


        }
    }

    public void modificarObsConfidenciales() throws SQLException {
        System.out.println("Se requiere verificar usuario");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        EstudianteServicio estudianteServicio = new EstudianteServicio();
        Usuario usuarioLogueado = null;
        int intentos = 0;
        int maxIntentos = 3;

        while (usuarioLogueado == null && intentos < maxIntentos) {
            System.out.print("Usuario: ");
            String usuario = sc.nextLine();

            System.out.print("Contraseña: ");
            String password = sc.nextLine();

            try {
                usuarioLogueado = usuarioDAO.login(usuario, password);

                if (usuarioLogueado != null) {
                    System.out.println("Login exitoso!");
                    System.out.println("Bienvenido/a: " + usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellido());

                    System.out.print("Ingrese el ID del estudiante: ");
                    int idEstudiante = sc.nextInt();
                    sc.nextLine(); // limpiar buffer

                    System.out.print("Ingrese la nueva observación confidencial: ");
                    String nuevaObs = sc.nextLine();

                    estudianteServicio.actualizarObsConfidenciales(idEstudiante, nuevaObs);

                    System.out.println("Observación confidencial actualizada correctamente.");
                    break;

                } else {
                    intentos++;
                    System.out.println("Usuario o contraseña incorrectos.");
                    if (intentos < maxIntentos) {
                        System.out.println("Intentos restantes: " + (maxIntentos - intentos));
                    } else {
                        System.out.println("Se alcanzó el límite de intentos. Acceso bloqueado.");
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void buscarEstudiante() {
        System.out.print("Ingrese el estudiante a buscar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Estudiante est = estudianteServicio.buscarEstudiante(String.valueOf(id));
        if (est != null) {
            System.out.println("Estudiante encontrado: " + est.getNombre() + " " + est.getApellido());
        } else {
            System.out.println("No se encontró ningún estudiante.");
            System.exit(0);
        }
    }

    // ---------------- GESTIÓN DE ROLES ----------------
    private void gestRoles() {
        int option;
        do {
            System.out.println("\n--- Gestión de Roles ---");
            System.out.println("1. Crear rol");
            System.out.println("2. Modificar rol");
            System.out.println("3. Eliminar rol");
            System.out.println("4. Consultar rol");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    crearRol();
                    break;
                case 2:
                    modificarRol();
                    break;
                case 3:
                    eliminarRol();
                    break;
                case 4:
                    consultarRol();
                    break;
                case 5:
                    System.out.println("Salir...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (option != 4);
    }

    private void consultarRol() {
        System.out.print("Ingrese ID del rol a consultar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Rol rolsol = rolServicio.getRol(id);
        System.out.println(rolsol.toString());
    }

    private void crearRol() {


        System.out.println("Tipos de usuario disponibles:");
        tipoUsuario[] tipos = tipoUsuario.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }

        System.out.print("\nIngrese tipo de rol: ");
        String tipoInput = sc.nextLine().trim().toUpperCase();

        try {
            // Intentar convertir el string a enum
            tipoUsuario tipoUsuario = org.example.modelo.tipoUsuario.valueOf(tipoInput);

            // Crear y configurar el rol
            Rol rol = new Rol();
            rol.setTipoUsuario(tipoUsuario);

            // Agregar el rol
            rolServicio.agregarRol(rol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modificarRol() {
        System.out.print("Ingrese ID del rol a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Rol rol = new Rol();
        rol.setId(id);

        System.out.println("Tipos de usuario disponibles:");
        tipoUsuario[] tipos = tipoUsuario.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i]);
        }

        System.out.print("\nIngrese nuevo tipo de rol: ");
        String tipoInput = sc.nextLine().trim().toUpperCase();

        try {

            tipoUsuario tipoUsuario = org.example.modelo.tipoUsuario.valueOf(tipoInput);

            rol.setTipoUsuario(tipoUsuario);

            rolServicio.modificarRol(rol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void eliminarRol() {
        System.out.print("Ingrese ID del rol a eliminar: ");
        int id = sc.nextInt();
        sc.nextLine();
        rolServicio.eliminarRol(id);

    }

    public void mostraritr() {
        List<ITR> lista = ITRServicio.mostrarITR();
        System.out.println("Lista de ITRs: ");
        for(ITR itr : lista) {
            System.out.println(itr.toString());
        }
    }

    public ITR crearITRsel() {
        System.out.println("Ingrese id del ITR al que pertenece el usuario: ");
        int id = sc.nextInt();
        sc.nextLine();
        return  new ITR(id);
    }


    // ---------------- GESTIÓN DE INSTANCIAS ----------------
    private void gestInstancia() {
        int opcion;
        do {
            System.out.println("\n--- Menú Instancias ---");
            System.out.println("1 - Alta de instancia");
            System.out.println("2 - Modificar instancia");
            System.out.println("3 - Buscar instancia por ID");
            System.out.println("4 - Buscar instancias por estudiante");
            System.out.println("5 - Buscar instancias por funcionario");
            System.out.println("0 - Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    altaInstancia();
                    break;
                case 2:
                    modificarInstancia();
                    break;
                case 3:
                    buscarInstancia();
                    break;
                case 4:
                    System.out.println("Funcionalidad en desarrollo.");
                    break;
                case 5:
                    System.out.println("Funcionalidad en desarrollo.");
                    break;
                case 0:
                    System.out.println("Salir");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        } while (opcion != 0);

    }


    private static void altaInstancia() {
        Instancia instancia = new Instancia();

        System.out.println("--- Alta de Instancia ---");

        System.out.print("Título: ");
        instancia.setTitulo(sc.nextLine());

        System.out.print("Comentario confidencial: ");
        instancia.setComConfidencial(sc.nextLine());

        System.out.print("Comentario: ");
        instancia.setComenatario(sc.nextLine());

        System.out.print("ID Estudiante: ");
        int idEstudiante = sc.nextInt();
        sc.nextLine();
        Estudiante est = new Estudiante();
        est.setIdEstudiante(idEstudiante);
        instancia.setEstudiante(est);

        System.out.print("ID Funcionario: ");
        int idFuncionario = sc.nextInt();
        sc.nextLine();
        Funcionario func = new Funcionario();
        func.setIdFuncionario(idFuncionario);
        instancia.setFuncionario(func);


        instancia.setFechaHora(java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

        instanciaServicio.crearInstancia(instancia);
    }

    private static void modificarInstancia() {
        System.out.print("Ingrese ID de la instancia a modificar: ");
        int id = sc.nextInt();
        sc.nextLine();

        Instancia instancia = instanciaServicio.getInstancia(id);
        if (instancia == null) {
            System.out.println("Instancia no encontrada.");
            return;
        }

        System.out.println("Modificando instancia: " + instancia.getTitulo());

        System.out.println("¿Qué datos quiere modificar?");
        System.out.println("1 - Título");
        System.out.println("2 - Comentario confidencial");
        System.out.println("3 - Comentario");
        System.out.println("0 - Salir");
        System.out.print("Opción: ");

        int opcion = sc.nextInt();
        sc.nextLine();

        switch (opcion) {
            case 1:
                System.out.print("Nuevo título: ");
                instancia.setTitulo(sc.nextLine());
                break;
            case 2:
                System.out.print("Nuevo comentario confidencial: ");
                instancia.setComConfidencial(sc.nextLine());
                break;
            case 3:
                System.out.print("Nuevo comentario: ");
                instancia.setComenatario(sc.nextLine());
                break;
            case 0:
                System.out.println("Saliendo.");
                break;
            default:
                System.out.println("Opción inválida.");
        }
        instanciaServicio.modificarInstancia(instancia);
    }

    private static void buscarInstancia() {
        System.out.print("Ingrese ID de la instancia: ");
        int id = sc.nextInt();
        sc.nextLine();

        Instancia instancia = instanciaServicio.getInstancia(id);
        if (instancia != null) {
            System.out.println("ID: " + instancia.getIdInstancia() + ", Título: " + instancia.getTitulo()
                    + ", Estudiante ID: " + instancia.getEstudiante().getIdEstudiante()
                    + ", Funcionario ID: " + instancia.getFuncionario().getIdFuncionario());
        } else {
            System.out.println("Instancia no encontrada.");
        }
    }
}












