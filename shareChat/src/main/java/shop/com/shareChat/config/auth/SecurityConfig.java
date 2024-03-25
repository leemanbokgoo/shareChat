package shop.com.shareChat.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import shop.com.shareChat.domain.user.Role;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.util.CustomHttpHeaderUtil;

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
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        String[] paths = {
                "/api/v1/user/**",
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
                // 서버 세션유지 하지않음.
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                                .requestMatchers(paths).hasRole(Role.USER.name())
                                .anyRequest().permitAll()
                )
                .formLogin( (formLogin) -> formLogin.disable()
                )
                .oauth2Login( (oauth2) -> oauth2.userInfoEndpoint(userInfo -> userInfo
                                .userService(this.customOAuth2UserService)
                        )
                )
                .httpBasic((basic) -> basic.disable())

                .exceptionHandling((exceptionConfig) ->
                                exceptionConfig
                                        .authenticationEntryPoint((request, response, authException) -> {
                                            CustomHttpHeaderUtil.fail(response, ErrorCode.NOT_USER, HttpStatus.UNAUTHORIZED);
                                        })
                                        .accessDeniedHandler((request, response, e) ->{
                                            CustomHttpHeaderUtil.fail(response, ErrorCode.NOT_AUTHORIZATION, HttpStatus.FORBIDDEN);
                                        })
                )
        ;

        return http.build();
    }

}