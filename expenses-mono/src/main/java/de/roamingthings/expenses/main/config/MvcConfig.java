package de.roamingthings.expenses.main.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Alexander Sparkowsky [info@roamingthings.de]
 * @version 2017/04/29
 */
@Configuration
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }


    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        super.configurePathMatch(configurer);
        configurer.setUseRegisteredSuffixPatternMatch(false);
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/");
/*
        super.addResourceHandlers(registry);
        registry.addResourceHandler("*/
/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/frontend/0.0.1/");
*/
    }

/*
    @Controller
    static class IndexController {

        @RequestMapping(value = "/", method = GET)
        public void addRequestMapping(HttpServletResponse response) throws IOException {
            response.sendRedirect("/index.html");
        }
    }
*/

}