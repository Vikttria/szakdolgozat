package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    Role findById(long id);

}
