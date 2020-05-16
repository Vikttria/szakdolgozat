package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogjegyRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import hu.vikttria.zalog_program.zaloghaz.Zalogjegy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class ZalogjegyService {

    @Autowired
    ZalogjegyRepository zalogjegyRepo;

    private final int futamido = 90;
    private final double kamat = 0.2;
    private final double napiKamat = kamat / futamido;
    private final double kezelesiKoltseg = 0.05;


    public void ujZalog(String leiras, int karat, double suly, int dbSzam, int osszeg, LocalDate beadas, Ugyfel ugyfel, Zalogfiok zalogfiok){
        Zalogjegy zalogjegy = new Zalogjegy(leiras, karat, suly, dbSzam, osszeg, beadas.plusDays(1), ugyfel, zalogfiok);
        zalogjegyRepo.save(zalogjegy);
    }

    public void kivaltZalogjegy(Long id){
        zalogjegyRepo.deleteById(id);
    }

    public void hosszabbitZalogjegy(LocalDate beadas, long id){
        zalogjegyRepo.setBeadasFor(beadas.plusDays(1), id);
    }

    public Zalogjegy getZalogjegyKivalt(long id, int osszeg, Zalogfiok zalogfiok){
        return zalogjegyRepo.findFirstByIdAndOsszegAndZalogfiok(id, osszeg, zalogfiok);
    }

    public Zalogjegy getZalogjegyHosszabbit(long id, int osszeg){
        return zalogjegyRepo.findFirstByIdAndOsszeg(id, osszeg);
    }

    public LocalDate futamidoLejarta(LocalDate beadas){
        return beadas.plusDays(futamido);
    }

    public int kivaltOsszeg(LocalDate beadas, int kolcsonOsszeg){
        long napok = ChronoUnit.DAYS.between(beadas, LocalDate.now());

        return (int)(kolcsonOsszeg + (kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg));
    }

    public int hosszabbitOsszeg(LocalDate beadas, int kolcsonOsszeg) {
        long napok = ChronoUnit.DAYS.between(beadas, LocalDate.now());

        return (int)((kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg));
    }

    public int lekerKivaltOsszeg(LocalDate beadas, LocalDate kivalt, int kolcsonOsszeg){
        long napok = ChronoUnit.DAYS.between(beadas, kivalt);

        return (int)(kolcsonOsszeg + (kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg));
    }

    public int lekerKamatOsszeg(LocalDate beadas, LocalDate kivaltDatum, int kolcsonOsszeg){
        long napok = ChronoUnit.DAYS.between(beadas, kivaltDatum);

        return (int)((kolcsonOsszeg * napiKamat * napok) + (kolcsonOsszeg * kezelesiKoltseg));
    }

    public List<Zalogjegy> ugyfelId(long id){
        return zalogjegyRepo.findByUgyfelId(id);
    }

    public Zalogjegy zalogjegyId(long id){
        return  zalogjegyRepo.findById(id);
    }

    public List<Zalogjegy> datumElotti(LocalDate beadas){
        return new ArrayList<>(zalogjegyRepo.findZalogjegyByBeadasBefore(beadas.plusDays(1)));
    }

    public void bevonasDatumElotti(LocalDate beadas){
        zalogjegyRepo.torolDatumElott(beadas);
    }

}
