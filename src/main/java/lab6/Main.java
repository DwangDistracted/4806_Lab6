package lab6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Configuration
@EnableWebMvc
public class Main implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry register) {
        register.addResourceHandler("/resources/script/js/**")
                .addResourceLocations("/resources/script/js/");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
