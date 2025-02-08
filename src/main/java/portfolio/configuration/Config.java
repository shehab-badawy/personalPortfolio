package portfolio.configuration;

import java.net.InetAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*").allowCredentials(false).maxAge(-1);
    }

    public void setHostIp()
    {
        try
        {
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ipAddress = inetAddress.getHostAddress();
            System.out.println("address: "+ipAddress);
            System.setProperty("server.port", "2001");
        }catch(Exception e)
        {
            System.out.println( "could not find the ip addr: " + e.getMessage());
        }

    }
}
