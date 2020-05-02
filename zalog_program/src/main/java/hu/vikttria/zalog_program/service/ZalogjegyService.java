package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogjegyRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class ZalogjegyService {

    ZalogjegyRepository zalogjegyRepo;

    double kamat = 0.2;
    double napiKamat = kamat / 90;
    double kezelesiKoltseg = 0.05;

    @Autowired
    public void setZalogjegyRepo(ZalogjegyRepository zalogjegyRepo){
        this.zalogjegyRepo = zalogjegyRepo;
    }

    public void ujZalog(String leiras, int karat, double suly, int dbSzam, int osszeg, LocalDate beadas, Ugyfel ugyfel){
        Zalogjegy zalogjegy = new Zalogjegy(leiras, karat, suly, dbSzam, osszeg, beadas, ugyfel);
        zalogjegyRepo.save(zalogjegy);
    }

    public void kivaltZalogjegy(Long id){
        zalogjegyRepo.deleteById(id);
    }

    public void hosszabbitZalogjegy(LocalDate beadas, long id){
        zalogjegyRepo.setBeadasFor(beadas, id);
    }

    public Zalogjegy getZalogjegy(long id, int osszeg){
        return zalogjegyRepo.findFirstByIdAndOsszeg(id, osszeg);
    }

    public LocalDate futamidoLejarta(LocalDate beadas){
        return beadas.plusDays(90);
    }

    public int kivaltOsszeg(LocalDate beadas, int kolcsonOsszeg){
        long napok = ChronoUnit.DAYS.between(beadas, LocalDate.now());
        double kivaltOsszeg = kolcsonOsszeg + (kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg);

        return (int)kivaltOsszeg;
    }

    public int hosszabbitOsszeg(LocalDate beadas, int kolcsonOsszeg) {
        long napok = ChronoUnit.DAYS.between(beadas, LocalDate.now());
        double hosszabbitOsszeg = (kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg);

        return (int)hosszabbitOsszeg;
    }

    public List<Zalogjegy> allZalogjegy(){
        return new ArrayList<>(zalogjegyRepo.findAll());
    }
}
