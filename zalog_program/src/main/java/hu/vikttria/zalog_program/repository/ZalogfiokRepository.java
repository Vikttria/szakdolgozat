package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ZalogfiokRepository extends CrudRepository<Zalogfiok, Long> {

    List<Zalogfiok> findAll();

    Zalogfiok findByCim(String cim);
}
