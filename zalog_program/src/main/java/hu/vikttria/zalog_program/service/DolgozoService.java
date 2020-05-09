package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.DolgozoReposiroty;
import hu.vikttria.zalog_program.zaloghaz.Dolgozo;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DolgozoService {

    DolgozoReposiroty dolgozoRepo;
    @Autowired
    public void setDolgozoRepo(DolgozoReposiroty dolgozoRepo){
        this.dolgozoRepo = dolgozoRepo;
    }


    public void ujDolgozo(String nev, String telefon, String email, Zalogfiok zalogfiok){
        Dolgozo dolgozo = new Dolgozo(nev, telefon, email, zalogfiok);

        dolgozoRepo.save(dolgozo);
    }

    public List<Dolgozo> allDolgozo(){
        return new ArrayList<>(dolgozoRepo.findAll());
    }

    public void dolgozoTorol(long id){
        dolgozoRepo.deleteById(id);
    }
}
