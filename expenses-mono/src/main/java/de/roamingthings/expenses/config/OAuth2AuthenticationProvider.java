package de.roamingthings.expenses.config;

import java.util.stream.Stream;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
public enum OAuth2AuthenticationProvider {
    LOCAL("/login"), GITHUB("/login/github"), FACEBOOK("/login/facebook");

    String loginUri;

    OAuth2AuthenticationProvider(String loginUri) {
        this.loginUri = loginUri;
    }

    public static OAuth2AuthenticationProvider withLoginUri(String requestURI) {
        return Stream.of(OAuth2AuthenticationProvider.values())
                .filter(oAuth2AuthenticationProvider -> oAuth2AuthenticationProvider.loginUri.equals(requestURI))
                .findFirst().orElse(OAuth2AuthenticationProvider.LOCAL);
    }
}
