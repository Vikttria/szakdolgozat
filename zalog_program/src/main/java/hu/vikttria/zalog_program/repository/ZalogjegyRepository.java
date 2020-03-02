package hu.vikttria.zalog_program.repository;

import org.springframework.data.repository.CrudRepository;

import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;

import java.util.List;

public interface ZalogjegyRepository extends CrudRepository<Zalogjegy, Integer> {

    List<Zalogjegy> findAll();

}
