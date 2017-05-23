package de.roamingthings.expenses.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/22
 */
@Configuration
//@PropertySource({ "classpath:persistence.properties" })
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {// @formatter:off
        clients
                .jdbc(dataSource)
//				.inMemory().withClient("sampleClientId").authorizedGrantTypes("implicit")
//				.scopes("read", "write", "foo", "bar").autoApprove(false).accessTokenValiditySeconds(3600)
//
//				.and().withClient("fooClientIdPassword").secret("secret")
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("foo", "read", "write")
//				.accessTokenValiditySeconds(3600) // 1 hour
//				.refreshTokenValiditySeconds(2592000) // 30 days
//
//				.and().withClient("barClientIdPassword").secret("secret")
//				.authorizedGrantTypes("password", "authorization_code", "refresh_token").scopes("bar", "read", "write")
//				.accessTokenValiditySeconds(3600) // 1 hour
//				.refreshTokenValiditySeconds(2592000) // 30 days
        ;
    } // @formatter:on

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // @formatter:off
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer()));
        endpoints.tokenStore(tokenStore())
                // .accessTokenConverter(accessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
        // @formatter:on
    }

    // @Autowired
    // public void init(AuthenticationManagerBuilder auth) throws Exception {
//		// @formatter:off
//		auth.jdbcAuthentication().dataSource(dataSource());
//		// @formatter:on
    // }

    /*
    @Bean
    public TokenStore tokenStore() {
    return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
    final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    // converter.setSigningKey("123");
    final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("mytest.jks"), "mypass".toCharArray());
    converter.setKeyPair(keyStoreKeyFactory.getKeyPair("mytest"));
    return converter;
    }
    */
    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

}