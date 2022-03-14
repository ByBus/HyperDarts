package dartsgame.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private User user1 = new User("ivanhoe@acme.com", "oMoa3VvqnLxW", "GAMER");
    private User user2 = new User("robinhood@acme.com", "ai0y9bMvyF6G", "GAMER");
    private User user3 = new User("wilhelmtell@acme.com", "bv0y9bMvyF7E", "GAMER");
    private User admin = new User("admin@acme.com", "zy0y3bMvyA6T", "ADMIN");

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(user1.getEmail()).password("{noop}" + user1.getPassword()).roles(user1.getRole())
                .and()
                .withUser(user2.getEmail()).password("{noop}" + user2.getPassword()).roles(user2.getRole())
                .and()
                .withUser(user3.getEmail()).password("{noop}" + user3.getPassword()).roles(user3.getRole())
                .and()
                .withUser(admin.getEmail()).password("{noop}" + admin.getPassword()).roles(admin.getRole());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
