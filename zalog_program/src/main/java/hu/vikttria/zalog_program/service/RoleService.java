package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.RoleRepository;
import hu.vikttria.zalog_program.zaloghaz.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepo;


    public Role roleSearch(long id) {
        return roleRepo.findById(id);
    }

    @PostConstruct
    public void init(){
        if (roleRepo.findByName("ROLE_IGAZGATO") == null) {
            Role igazgato = new Role("ROLE_IGAZGATO");
            roleRepo.save(igazgato);
        }
        if (roleRepo.findByName("ROLE_DOLGOZO") == null) {
            Role dolgozo = new Role("ROLE_DOLGOZO");
            roleRepo.save(dolgozo);
        }
        if (roleRepo.findByName("ROLE_UGYFEL") == null) {
            Role ugyfel = new Role("ROLE_UGYFEL");
            roleRepo.save(ugyfel);
        }
    }

}
