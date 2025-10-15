package org.example.seguridad;

import java.util.Arrays;

public final class ValidadorCedula {

    // Coeficientes oficiales para los 7 dígitos base (de izquierda a derecha)
    private static final int[] COEF = {2, 9, 8, 7, 6, 3, 4};

    public ValidadorCedula() {}

    /**
     * Valida una cédula uruguaya de **exactamente 8 dígitos**.
     * @param ci String de 8 dígitos.
     * @return true si es válida, false si el dígito verificador no coincide.
     * @throws IllegalArgumentException si contiene caracteres no numéricos o no tiene 8 dígitos.
     */
    public static boolean esValida(String ci) {
        if (ci == null) throw new IllegalArgumentException("Cédula nula");
        if (ci.length() != 8) throw new IllegalArgumentException("Cédula debe tener exactamente 8 dígitos");
        if (!ci.matches("\\d{8}")) throw new IllegalArgumentException("Cédula contiene caracteres no numéricos");

        // Separar cuerpo y dígito verificador
        String cuerpo = ci.substring(0, 7);
        int dvIngresado = Character.digit(ci.charAt(7), 10);

        // Convertir cuerpo a array de enteros
        int[] base7 = new int[7];
        for (int i = 0; i < 7; i++) {
            base7[i] = Character.digit(cuerpo.charAt(i), 10);
        }

        // Calcular suma ponderada
        int suma = 0;
        for (int i = 0; i < 7; i++) {
            suma += base7[i] * COEF[i];
        }

        // Dígito verificador esperado: complemento a 10 del resto
        int dvCalculado = (10 - (suma % 10)) % 10;

        return dvIngresado == dvCalculado;
    }

    /**
     * Devuelve la cédula en formato canónico #######-# si es válida, null si no.
     */
    public static String normalizarCanonico(String ci) {
        if (!esValida(ci)) return null;
        String cuerpo = ci.substring(0, 7);
        String dv = ci.substring(7, 8);
        return cuerpo + "-" + dv;
    }
}