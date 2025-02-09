//package com.example.test.config;  // Vérifiez que votre package correspond ici.
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()  // Permet l'accès à ces URL sans authentification
//                .anyRequest().authenticated()  // Toutes les autres requêtes doivent être authentifiées
//                .and()
//                .formLogin()
//                .loginPage("/login")  // Spécifie l'URL de la page de login
//                .loginProcessingUrl("/login")  // Spécifie l'URL de traitement du formulaire de login
//                .defaultSuccessUrl("/home", true)  // Redirige vers la page d'accueil après une connexion réussie
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();  // Permet la déconnexion
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();  // Utilisation de l'encodeur bcrypt pour le mot de passe
//    }
//}
