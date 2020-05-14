package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.RoleRepository;
import hu.vikttria.zalog_program.zaloghaz.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleService {

    RoleRepository roleRepo;
    @Autowired
    public void setUgyfelRepo(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    public Role roleSearch(String role) {
        return  roleRepo.findByName(role);
    }

    @PostConstruct
    public void init(){
        if (roleRepo.findByName("IGAZGATO") == null) {
            Role igazgato = new Role("IGAZGATO");
            roleRepo.save(igazgato);
        }
        if (roleRepo.findByName("DOLGOZO") == null) {
            Role dolgozo = new Role("DOLGOZO");
            roleRepo.save(dolgozo);
        }
        if (roleRepo.findByName("UGYFEL") == null) {
            Role ugyfel = new Role("UGYFEL");
            roleRepo.save(ugyfel);
        }
    }

}
