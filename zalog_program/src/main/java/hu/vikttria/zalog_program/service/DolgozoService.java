package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.DolgozoReposiroty;
import hu.vikttria.zalog_program.zaloghaz.Beosztas;
import hu.vikttria.zalog_program.zaloghaz.Dolgozo;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DolgozoService {

    @Autowired
    DolgozoReposiroty dolgozoRepo;


    public void ujDolgozo(String nev, String telefon, String email, Zalogfiok zalogfiok, Beosztas beosztas){
        Dolgozo dolgozo = new Dolgozo(nev, telefon, email, zalogfiok, beosztas);

        dolgozoRepo.save(dolgozo);
    }

    public List<Dolgozo> allDolgozo(){
        return new ArrayList<>(dolgozoRepo.findAllByOrderByNev());
    }

    public void dolgozoTorol(long id){
        dolgozoRepo.deleteById(id);
    }

}
