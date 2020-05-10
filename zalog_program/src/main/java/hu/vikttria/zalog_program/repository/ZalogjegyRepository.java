package hu.vikttria.zalog_program.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ZalogjegyRepository extends CrudRepository<Zalogjegy, Long> {

    List<Zalogjegy> findAll();

    List<Zalogjegy> findByUgyfelId(long id);

    Zalogjegy findFirstByIdAndOsszeg(long id, int osszeg);

    Zalogjegy findById(long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Zalogjegy z SET z.beadas = :datum WHERE z.id = :id")
    int setBeadasFor(@Param("datum") LocalDate beadas, @Param("id") long id);

    List<Zalogjegy> findZalogjegyByBeadasBefore(LocalDate datum);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Zalogjegy z WHERE z.beadas <= :datum")
    void torolDatumElott(@Param("datum") LocalDate beadas);

    //void deleteZalogjegyByBeadasBefore(LocalDate datum);
}
