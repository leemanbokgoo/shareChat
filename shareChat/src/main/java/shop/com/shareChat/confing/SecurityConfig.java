package shop.com.shareChat.confing;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import shop.com.shareChat.confing.auth.CustomOAuth2UserService;
import shop.com.shareChat.domain.user.Role;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig  {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] paths = {
                "/api/v1/mentor/**",
                "/api/v1/user/**",
                "/api/v1/email/**",
                "/api/v1/email/**",
                "/api/v1/mentorTime/**",
                "/api/v1/mentoring/**",
                "/user/mentoring/**",
                "/mentoring/**"
        };

        http
                .csrf((csrfConfig) ->
                        csrfConfig.disable()
                )
                .headers((headerConfig) ->
                        headerConfig.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()
                        )
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                                .requestMatchers(paths).hasRole(Role.USER.name())
                                .anyRequest().permitAll()
                )
                .formLogin( (formLogin) ->
                        formLogin
                                .loginPage("/login")
                                .defaultSuccessUrl("/", true)
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                )
                .oauth2Login( (oauth2) -> oauth2.userInfoEndpoint(userInfo -> userInfo
                                .userService(this.customOAuth2UserService)
                                )
                )

        ;
        return http.build();
    }

}

