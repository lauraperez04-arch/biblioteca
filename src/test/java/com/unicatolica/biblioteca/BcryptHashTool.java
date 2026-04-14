package com.unicatolica.biblioteca;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Ejecutar: mvnw -q exec:java -Dexec.mainClass=com.unicatolica.biblioteca.BcryptHashTool -Dexec.classpathScope=test */
public final class BcryptHashTool {

    public static void main(String[] args) {
        String hash = new BCryptPasswordEncoder().encode("admin123");
        System.out.println(hash);
    }
}
