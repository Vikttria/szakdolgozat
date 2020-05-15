package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogfiokRepository;
import hu.vikttria.zalog_program.zaloghaz.Zalogfiok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZalogfiokService {

    ZalogfiokRepository fiokRepo;
    @Autowired
    public void setFiokRepo(ZalogfiokRepository fiokRepo){
        this.fiokRepo = fiokRepo;
    }


    public void ujFiok(String cim, String telefon){
        Zalogfiok fiok = new Zalogfiok(cim, telefon);

        fiokRepo.save(fiok);
    }

    public List<Zalogfiok> allFiok(){
        return new ArrayList<>(fiokRepo.findAll());
    }

    public void fiokTorol(long id){
        fiokRepo.deleteById(id);
    }

    public Zalogfiok zalogjegyCim(String cim) {
        return fiokRepo.findByCim(cim);
    }

}
