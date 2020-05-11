package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
