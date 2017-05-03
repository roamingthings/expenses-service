package de.roamingthings.expenses.config;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
public class OAuth2AuthenticationProviderTest {
    @Test
    public void should_return_github_uri() throws Exception {
        assertThat(OAuth2AuthenticationProvider.withLoginUri(OAuth2AuthenticationProvider.GITHUB.loginUri), is(OAuth2AuthenticationProvider.GITHUB));
    }

    @Test
    public void should_return_facebook_uri() throws Exception {
        assertThat(OAuth2AuthenticationProvider.withLoginUri(OAuth2AuthenticationProvider.FACEBOOK.loginUri), is(OAuth2AuthenticationProvider.GITHUB));
    }

    @Test
    public void should_return_local_uri() throws Exception {
        assertThat(OAuth2AuthenticationProvider.withLoginUri(OAuth2AuthenticationProvider.LOCAL.loginUri), is(OAuth2AuthenticationProvider.LOCAL));
    }

}