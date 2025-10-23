//package com.hulkaach.hr_skill_ninja;
//
//import org.flywaydb.core.Flyway;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//
////@Configuration
//public class FlywayConfig {
////    @Bean
//    public Flyway flyway(DataSource dataSource) {
//        Flyway flyway = Flyway.configure()
//                .dataSource(dataSource)
//                .baselineOnMigrate(true)       // если база уже существует
//                .validateOnMigrate(false)      // отключаем проверку версии БД
//                .load();
//        flyway.migrate();
//        return flyway;
//    }
//}
