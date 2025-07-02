package portfolio.repository.AdminRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.entity.Admin.Admin;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
