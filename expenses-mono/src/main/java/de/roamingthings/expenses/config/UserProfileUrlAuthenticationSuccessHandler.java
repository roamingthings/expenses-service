package de.roamingthings.expenses.config;

import de.roamingthings.expenses.business.userprofile.domain.UserProfile;
import de.roamingthings.expenses.business.userprofile.repository.UserProfileRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/03
 */
@Component
public class UserProfileUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    protected Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private UserProfileRepository userProfileRepository;

    public UserProfileUrlAuthenticationSuccessHandler(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {

        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication)
            throws IOException {

        final String requestURI = request.getRequestURI();
        OAuth2AuthenticationProvider authenticationProvider = OAuth2AuthenticationProvider.withLoginUri(requestURI);

        // TODO store authenticationProvider in authentication

        String targetUrl = determineTargetUrl(authentication, authenticationProvider);

        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication, OAuth2AuthenticationProvider authenticationProvider) {
        UserProfile userProfile = null;

        switch (authenticationProvider) {
            case GITHUB:
                Map<String, Object> details = (Map<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
                String principal = (String) authentication.getPrincipal();
                userProfile = userProfileRepository.findByGithubId(String.valueOf(principal));
                break;
            case FACEBOOK:
                Map<String, String> facebookDetails = (Map<String, String>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
                String facebookPrincipal = (String) authentication.getPrincipal();
                final String facebookId = facebookDetails.get("id");
                userProfile = userProfileRepository.findByFacebookId(facebookId);
                break;
            default:
                break;
        }

        if (userProfile == null) {
            return "/userprofile?provider=" + authenticationProvider;
        } else {
            return "/";
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
