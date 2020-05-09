package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.Dolgozo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DolgozoReposiroty extends CrudRepository<Dolgozo, Long> {

    List<Dolgozo> findAllByOrderByNev();
}
