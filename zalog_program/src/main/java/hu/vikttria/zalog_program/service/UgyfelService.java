package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.UgyfelRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UgyfelService {

    UgyfelRepository ugyfelRepo;
    @Autowired
    public void setUgyfelRepo(UgyfelRepository ugyfelRepo) {
        this.ugyfelRepo = ugyfelRepo;
    }

    public void ujUgyfel(String nev, String anyjaNeve, String szig, String cim, String email){
        Ugyfel ugyfel = new Ugyfel(nev, anyjaNeve, szig, cim, email);
        ugyfelRepo.save(ugyfel);
    }

    public List<Ugyfel> allUgyfel() {
        return new ArrayList<>(ugyfelRepo.findAll());
    }

    @PostConstruct
    public void init(){
        Ugyfel ugyfel = new Ugyfel("Anonymous", "", "", "", "");
        ugyfelRepo.save(ugyfel);
    }
}
