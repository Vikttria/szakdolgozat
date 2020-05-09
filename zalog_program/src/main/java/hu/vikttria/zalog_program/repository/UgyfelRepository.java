package hu.vikttria.zalog_program.repository;

import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UgyfelRepository extends CrudRepository<Ugyfel, Long> {

    List<Ugyfel> findAllByOrderByNev();

    List<Ugyfel> findById(long id);

}
