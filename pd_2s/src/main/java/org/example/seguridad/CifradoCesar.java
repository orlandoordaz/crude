package org.example.seguridad;

public class CifradoCesar {

    private static final int DESPLAZAMIENTO = 3;

    public static String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + DESPLAZAMIENTO) % 26 + base);
            }
            resultado.append(c);
        }
        return resultado.toString();
    }
}
