package gr.uoi.dit.master2025.gkouvas.dpp.util;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        System.out.println(
                new BCryptPasswordEncoder().encode("admin123")
        );


    }
}
