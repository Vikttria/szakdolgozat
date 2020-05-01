package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ugyfel {

    @GeneratedValue
    @Id
    private long id;
    @Column(nullable = false)
    private String nev;
    @Column(unique = true, nullable = false)
    private String szig;
    @Column(nullable = false)
    private String anyjaNeve;
    @Column(nullable = false)
    private String cim;
    @Column(unique = true)
    private String email;
    @OneToMany(mappedBy = "ugyfel")
    private List<Zalogjegy> zalogtargyak;

    public Ugyfel(){}

    public Ugyfel(String nev, String anyjaNeve, String szig, String cim, String email){
        this.nev = nev;
        this.anyjaNeve = anyjaNeve;
        this.szig = szig;
        this.cim = cim;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSzig() {
        return szig;
    }

    public void setSzig(String szig) {
        this.szig = szig;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getAnyjaNeve() {
        return anyjaNeve;
    }

    public void setAnyjaNeve(String anyjaNeve) {
        this.anyjaNeve = anyjaNeve;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Zalogjegy> getZalogtargyak() {
        return zalogtargyak;
    }

    public void setZalogtargyak(List<Zalogjegy> zalogtargyak) {
        this.zalogtargyak = zalogtargyak;
    }
}
