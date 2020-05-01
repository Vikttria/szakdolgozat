package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogjegyRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ZalogjegyService {

    ZalogjegyRepository zalogjegyRepo;

    @Autowired
    public void setZalogjegyRepo(ZalogjegyRepository zalogjegyRepo){
        this.zalogjegyRepo = zalogjegyRepo;
    }

    public void ujZalog(String leiras, int karat, double suly, int dbSzam, int osszeg, Date beadas, Ugyfel ugyfel){
        Zalogjegy zalogjegy = new Zalogjegy(leiras, karat, suly, dbSzam, osszeg, beadas, ugyfel);
        zalogjegyRepo.save(zalogjegy);
    }

    public Zalogjegy getZalogjegy(long id, int osszeg){
        return zalogjegyRepo.findByIdAndOsszeg(id, osszeg);
    }

}
