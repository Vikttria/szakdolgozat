package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    private Ugyfel ugyfel;

    public User(){}

    public User(String username,String password, Ugyfel ugyfel, Role role) {
        this.username = username;
        this.password = password;
        this.ugyfel = ugyfel;
        this.roles.add(role);
    }

    public User(String username,String password, Role role) {
        this.username = username;
        this.password = password;
        this.roles.add(role);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Ugyfel getUgyfel(List<Ugyfel> byId) {
        return ugyfel;
    }

    public void setUgyfel(Ugyfel ugyfel) {
        this.ugyfel = ugyfel;
    }
}
