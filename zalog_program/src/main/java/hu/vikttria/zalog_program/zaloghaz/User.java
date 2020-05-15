package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String password;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Ugyfel> ugyfel = new ArrayList<>();

    public User(){}

    public User(String username,String password, Ugyfel ugyfel, Role role) {
        this.username = username;
        this.password = password;
        this.ugyfel.add(ugyfel);
        this.role = role;
    }

    public User(String username,String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Ugyfel getUgyfel(List<Ugyfel> byId) {
        return ugyfel.get(0);
    }

    public void setUgyfel(Ugyfel ugyfel) {
        this.ugyfel.add(ugyfel);
    }
}
