package com.hacker.slack.seurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
 
import com.hacker.slack.jwt.AuthEntryPointJwt;
import com.hacker.slack.jwt.AuthTokenFilter;
import com.hacker.slack.jwt.UserDetailsServiceImpl;
 

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
 
	@Autowired
	private DataSource dataSource;
	  @Autowired
	    PasswordEncoder passwordEncoder;
	  @Autowired
		UserDetailsServiceImpl userDetailsService;

	  @Autowired
		private AuthEntryPointJwt unauthorizedHandler;

		@Bean
		public AuthTokenFilter authenticationJwtTokenFilter() {
			return new AuthTokenFilter();
		}
		
	 
		
		/*
		 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
		 * Exception { auth.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder())
		 * .authoritiesByUsernameQuery("SELECT username,role as authority FROM user_role where username=?"
		 * )
		 * .usersByUsernameQuery("SELECT username,password,enabled FROM users where username=?"
		 * ); }
		 */
		 
		
		  @Override public void configure(AuthenticationManagerBuilder
		  authenticationManagerBuilder) throws Exception {
		  authenticationManagerBuilder.userDetailsService(userDetailsService).
		  passwordEncoder(passwordEncoder()); }
		 

	    @Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

	    
	    
	 
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
		
		/*
		 * @Override protected void configure(HttpSecurity http) throws Exception {
		 * http.cors().and().csrf().disable()
		 * .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
		 * and() .authorizeRequests().antMatchers("/api/auth/**").permitAll()
		 * .antMatchers("/api/test/**").permitAll() .antMatchers("/**").permitAll()
		 * .anyRequest().authenticated() .and() .formLogin()
		 * .loginPage("/login").failureUrl("/login?error=true")
		 * .defaultSuccessUrl("/home") .usernameParameter("username")
		 * .passwordParameter("password") .and().logout() .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout"))
		 * .logoutSuccessUrl("/login?logout=true").and().exceptionHandling()
		 * .accessDeniedPage("/accessdenied");
		 * 
		 * http.addFilterBefore(authenticationJwtTokenFilter(),
		 * UsernamePasswordAuthenticationFilter.class); }
		 */
		
		  @Override protected void configure(HttpSecurity http) throws Exception { http
		  .csrf().disable() .httpBasic().and() .authorizeRequests()
		  .antMatchers("/api/auth/**").permitAll().antMatchers("/api/test/**").permitAll() .antMatchers("/**").permitAll().anyRequest().authenticated() .and()
		  .formLogin() .loginPage("/login").failureUrl("/login?error=true")
		  .defaultSuccessUrl("/home") .usernameParameter("username")
		  .passwordParameter("password") .and().logout() .logoutRequestMatcher(new
		  AntPathRequestMatcher("/logout"))
		  .logoutSuccessUrl("/login?logout=true").and().exceptionHandling()
		  .accessDeniedPage("/accessdenied"); }
		 
	    
		 
			/*
			 * @Override protected void configure(final HttpSecurity http) throws Exception
			 * { http.csrf().disable().authorizeRequests() .antMatchers(HttpMethod.GET,
			 * "/login","/api/contacts", "/index*", "/static/**", "/*.js", "/*.json",
			 * "/*.ico", "/*.sccs","/*.woff2", "/*.css").permitAll() .anyRequest()
			 * .authenticated() .and() .formLogin() .loginPage("/")
			 * .loginProcessingUrl("/auth") .usernameParameter("username")
			 * .passwordParameter("password") .successHandler(successHandler())
			 * .failureHandler(failureHandler()) .permitAll() .and() .logout().permitAll();
			 * }
			 */

			/*
			 * private AuthenticationSuccessHandler successHandler() { return new
			 * AuthenticationSuccessHandler() {
			 * 
			 * @Override public void onAuthenticationSuccess(HttpServletRequest
			 * httpServletRequest, HttpServletResponse httpServletResponse, Authentication
			 * authentication) throws IOException, ServletException {
			 * System.out.println("successHandler");
			 * httpServletResponse.getWriter().append("OK");
			 * httpServletResponse.setStatus(200); } }; }
			 * 
			 * private AuthenticationFailureHandler failureHandler() { return new
			 * AuthenticationFailureHandler() {
			 * 
			 * @Override public void onAuthenticationFailure(HttpServletRequest
			 * httpServletRequest, HttpServletResponse httpServletResponse,
			 * AuthenticationException e) throws IOException, ServletException {
			 * System.out.println("failureHandler");
			 * httpServletResponse.getWriter().append("Authentication failure");
			 * httpServletResponse.setStatus(401); } }; }
			 */
		 
	    
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	 
	/*
	 * @Bean public WebMvcConfigurer corsConfigurer() { return new
	 * WebMvcConfigurerAdapter() {
	 * 
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**").allowedOrigins("http://localhost:4200");
	 * 
	 * } }; }
	 */
		/*
		 * @Override protected void configure(HttpSecurity http) throws Exception {
		 * http.cors().and() .authorizeRequests()
		 * .antMatchers("/api/register","/api/login","/logout").permitAll()
		 * .anyRequest().fullyAuthenticated().and() .logout() .permitAll()
		 * .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")) .and()
		 * .httpBasic().and()
		 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		 * .and() .csrf().disable(); }
		 */
}
