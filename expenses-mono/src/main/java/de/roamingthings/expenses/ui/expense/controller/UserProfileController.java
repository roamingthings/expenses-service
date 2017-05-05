package de.roamingthings.expenses.ui.expense.controller;

import de.roamingthings.expenses.business.userprofile.domain.UserProfile;
import de.roamingthings.expenses.business.userprofile.repository.UserProfileRepository;
import de.roamingthings.expenses.config.OAuth2AuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/05/04
 */
@Controller
public class UserProfileController {
    private final UserProfileRepository userProfileRepository;

    public UserProfileController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @RequestMapping("/userprofile")
    @Transactional
    public String editUserProfile(Model model, @RequestParam("provider") String provider, Principal principal) {
        UserProfile userProfile = null;

        if (provider != null) {
            final String principalName = principal.getName();


            if (OAuth2AuthenticationProvider.GITHUB.name().equals(provider)) {
                userProfile = userProfileRepository.findByGithubId(principalName);
                if (userProfile == null) {
                    userProfile = new UserProfile();
                    userProfile.setGithubId(principalName);
                    userProfileRepository.save(userProfile);
                }
            } else if (OAuth2AuthenticationProvider.FACEBOOK.name().equals(provider)) {
                userProfile = userProfileRepository.findByFacebookId(principalName);
                if (userProfile == null) {
                    userProfile = new UserProfile();
                    userProfile.setFacebookId(principalName);
                    userProfileRepository.save(userProfile);
                }
            }

            if (userProfile != null) {
                userProfileRepository.save(userProfile);
            }

        }

        model.addAttribute("userProfile", userProfile);
        return "user_profile/form";
    }

    @RequestMapping(value = "/userprofile", method = RequestMethod.POST)
    @Transactional
    public String updateUserProfile(Model model, @ModelAttribute UserProfile userProfile, BindingResult bindingResult) {
        final UserProfile storedUserProfile = userProfileRepository.findOne(userProfile.getId());
        storedUserProfile.setEmail(userProfile.getEmail());
        userProfileRepository.save(storedUserProfile);

        model.addAttribute("userProfile", storedUserProfile);
        return "user_profile/form";
    }
}