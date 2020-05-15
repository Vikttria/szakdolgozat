package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.UgyfelRepository;
import hu.vikttria.zalog_program.zaloghaz.Ugyfel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UgyfelService {

    @Autowired
    UgyfelRepository ugyfelRepo;


    public void ujUgyfel(String nev, String anyjaNeve, String szig, String cim, String email){
        Ugyfel ugyfel = new Ugyfel(nev, anyjaNeve, szig, cim, email);

        ugyfelRepo.save(ugyfel);
    }

    public List<Ugyfel> allUgyfel() {
        return new ArrayList<>(ugyfelRepo.findAllByOrderByNev());
    }

    public Ugyfel ugyfelEmail(String email){
        return ugyfelRepo.findByEmail(email);
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
