package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogjegyRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
public class ZalogjegyService {

    ZalogjegyRepository zalogjegyRepo;

    @Autowired
    public void setZalogjegyRepo(ZalogjegyRepository zalogjegyRepo){
        this.zalogjegyRepo = zalogjegyRepo;
    }

    public void ujZalog(String leiras, int karat, double suly, int dbSzam, int osszeg, LocalDate beadas, Ugyfel ugyfel){
        Zalogjegy zalogjegy = new Zalogjegy(leiras, karat, suly, dbSzam, osszeg, beadas, ugyfel);
        zalogjegyRepo.save(zalogjegy);
    }

    public Zalogjegy getZalogjegy(long id, int osszeg){
        return zalogjegyRepo.findFirstByIdAndOsszeg(id, osszeg);
    }

    public LocalDate futamidoLejarta(LocalDate beadas){
        return beadas.plusDays(90);
    }

    public int kivaltOsszeg(LocalDate beadas, int kolcsonOsszeg){
        long napok = ChronoUnit.DAYS.between(beadas, LocalDate.now().plusDays(1));
        double kivaltOsszeg = kolcsonOsszeg + (kolcsonOsszeg * 0.2 / 90 * napok);

        return (int)kivaltOsszeg;
    }

}
