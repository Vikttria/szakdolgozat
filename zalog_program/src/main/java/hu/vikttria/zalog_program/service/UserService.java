package hu.vikttria.zalog_program.service;

import hu.vikttria.zalog_program.zaloghaz.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

}
