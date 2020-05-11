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

    public Role roleSearche(String role) {
        return roleRepo.findByName(role);
    }

    @PostConstruct
    public void init(){
        if (roleRepo.findByName("IGAZGATO") != null
                && roleRepo.findByName("DOLGOZO") != null
                && roleRepo.findByName("UGYFEL") != null ){
            return;
        }

        Role igazgato = new Role("IGAZGATO");
        Role dolgozo = new Role("DOLGOZO");
        Role ugyfel = new Role("UGYFEL");

        roleRepo.save(igazgato);
        roleRepo.save(dolgozo);
        roleRepo.save(ugyfel);

    }

}
