package hu.vikttria.zalog_program.zaloghaz;


import javax.persistence.*;
import java.util.List;

@Entity
public class Zalogfiok {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;
    private String cim;
    private String telefon;
    @OneToMany(mappedBy = "zalogfiok")
    private List<Zalogjegy> zalogtargyak;
    @OneToMany(mappedBy = "zalogfiok")
    private List<Dolgozo> dolgozok;

    public Zalogfiok(){}

    public Zalogfiok(String cim, String telefon){
        this.cim = cim;
        this.telefon = telefon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<Zalogjegy> getZalogtargyak() {
        return zalogtargyak;
    }

    public void setZalogtargyak(List<Zalogjegy> zalogtargyak) {
        this.zalogtargyak = zalogtargyak;
    }

    public List<Dolgozo> getDolgozok() {
        return dolgozok;
    }

    public void setDolgozok(List<Dolgozo> dolgozok) {
        this.dolgozok = dolgozok;
    }
}
