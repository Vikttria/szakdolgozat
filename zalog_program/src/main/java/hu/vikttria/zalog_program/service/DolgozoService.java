package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.DolgozoReposiroty;
import hu.vikttria.zalog_program.zaloghaz.Beosztas;
import hu.vikttria.zalog_program.zaloghaz.Dolgozo;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DolgozoService {

    DolgozoReposiroty dolgozoRepo;
    @Autowired
    public void setDolgozoRepo(DolgozoReposiroty dolgozoRepo){
        this.dolgozoRepo = dolgozoRepo;
    }


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

    public String jelszo(){

        Random random = new Random();
        char[] word = new char[8];
        for (int i = 0; i < word.length; i++){
            word[i] = (char) ('a' + random.nextInt(26));
        }

        return new String(word);
    }
}
