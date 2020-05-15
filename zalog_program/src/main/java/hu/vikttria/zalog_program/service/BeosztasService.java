package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.BeosztasRepository;
import hu.vikttria.zalog_program.zaloghaz.Beosztas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class BeosztasService {

    @Autowired
    BeosztasRepository beosztasRepo;

    public List<Beosztas> allBeosztas(){
        return new ArrayList<>(beosztasRepo.findAllByOrderByMunkakor());
    }

    public Beosztas beosztasKeres(String mk){
        return beosztasRepo.findByMunkakor(mk);
    }

    @PostConstruct
    public void init(){
        if (beosztasKeres("becsüs") == null) {
            Beosztas becsus = new Beosztas("becsüs");
            beosztasRepo.save(becsus);
        }
        if (beosztasKeres("pénztáros") == null) {
            Beosztas penztaros = new Beosztas("pénztáros");
            beosztasRepo.save(penztaros);
        }
    }

}
