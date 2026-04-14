package com.unicatolica.biblioteca.config;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Diagnóstico: si Neon muestra tablas pero Hibernate dice "relation does not exist",
 * la app suele estar usando otra URL/credenciales (p. ej. variables de entorno).
 */
@Configuration
public class JdbcStartupDiagnostics {

    private static final Logger log = LoggerFactory.getLogger(JdbcStartupDiagnostics.class);

    @Bean
    @ConditionalOnProperty(name = "app.log-jdbc-info", havingValue = "true")
    ApplicationRunner logEffectiveJdbcTarget(DataSource dataSource) {
        return args -> {
            if (dataSource instanceof HikariDataSource hikari) {
                log.info("JDBC URL efectiva: {}", hikari.getJdbcUrl());
            }
            try (Connection c = dataSource.getConnection();
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery("select current_database(), current_schema()")) {
                if (rs.next()) {
                    log.info("PostgreSQL session: current_database()={}, current_schema()={}",
                            rs.getString(1), rs.getString(2));
                }
            }
            try (Connection c = dataSource.getConnection();
                 Statement st = c.createStatement();
                 ResultSet rs = st.executeQuery(
                         "select count(*) from information_schema.tables where table_schema = 'public' and table_name = 'usuarios'")) {
                if (rs.next()) {
                    log.info("Tabla public.usuarios visible desde esta conexión: {}", rs.getInt(1) == 1);
                }
            }
        };
    }
}
