package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.ZalogjegyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZalogjegyService {

    ZalogjegyRepository zalogjegyRepo;

    @Autowired
    public void setZalogjegyRepo(ZalogjegyRepository zalogjegyRepo){
        this.zalogjegyRepo = zalogjegyRepo;
    }

}
