package portfolio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.Security.JwtUtil;
import portfolio.entity.Admin.Admin;
import portfolio.repository.AdminRepository.AdminRepository;

@RestController
@RequestMapping("/login")
public class LoginController 
{
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public ObjectMapper objectMapper = new ObjectMapper();
    @PostMapping("/")
    public String res(@RequestBody String json)
    {
        try
        {
            System.out.println(json);
            JsonNode rootNode = objectMapper.readTree(json);
            try
            {
                String username = rootNode.get("username").asText();
                String password = rootNode.get("password").asText();
                Optional<Admin> admin = adminRepository.findByUsername(username);
                if(admin.isPresent())
                {
                    System.out.println("admin is found");
                    boolean condition = passwordEncoder.matches(password, admin.get().getPassword());
                    if(condition)
                    {
                        System.out.println("before");
                        System.out.println(JwtUtil.generateToken(username));
                        System.out.println(JwtUtil.generateToken(username));
                        return JwtUtil.generateToken(username);
                    }
                    System.out.println("wrong pass");
                }
                return "wrong credintials!";
            } 
            catch(Exception e){
                return e.getMessage();
            }
        }catch(Exception e){
            return e.getMessage();
        }
    } 
    
}
