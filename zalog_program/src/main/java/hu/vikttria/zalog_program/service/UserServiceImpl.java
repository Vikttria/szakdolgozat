package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.repository.RoleRepository;
import hu.vikttria.zalog_program.repository.UserRepository;
import hu.vikttria.zalog_program.zaloghaz.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {


    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void userDelete(String username){
        userRepository.deleteById(findByUsername(username).getId());
    }

    @Override
    public void save(User user) {
        log.info(user.getPassword());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



}
