package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.List;

@Entity
public class Beosztas {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;
    private String munkakor;
    @OneToMany(mappedBy = "beosztas")
    private List<Dolgozo> dolgozok;

    public Beosztas(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMunkakor() {
        return munkakor;
    }

    public void setMunkakor(String munkakor) {
        this.munkakor = munkakor;
    }

    public List<Dolgozo> getDolgozok() {
        return dolgozok;
    }

    public void setDolgozok(List<Dolgozo> dolgozok) {
        this.dolgozok = dolgozok;
    }
}
