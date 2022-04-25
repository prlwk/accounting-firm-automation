package com.example.firm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.example.firm.security.jwt.JwtSecurityConfigurer;
import com.example.firm.security.jwt.JwtTokenProvider;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    ;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/singin").permitAll()
                .antMatchers("/employees/update/**",
                        "/employees/new-employee/add",
                        "/employees/employee/delete/**",
                        "/departments/update/**",
                        "/departments/department/delete/**",
                        "/departments/new-department/add",
                        "/projects/update/**",
                        "/projects/project/delete/**",
                        "/projects/new-project/add",
                        "/departments_employees/department_employee/delete_by_employee/**",
                        "/departments_employees/change_department_by_employee/**",
                        "/departments_employees/new_department_employee/add").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
