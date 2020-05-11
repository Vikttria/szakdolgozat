package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.Beosztas;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BeosztasRepository extends CrudRepository<Beosztas, Long> {

    List<Beosztas> findAllByOrderByMunkakor();

    Beosztas findByMunkakor(String mk);
}
