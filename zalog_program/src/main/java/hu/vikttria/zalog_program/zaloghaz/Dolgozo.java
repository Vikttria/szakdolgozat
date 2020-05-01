package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;

@Entity
public class Dolgozo {

    @GeneratedValue
    @Id
    private long id;
    private String nev;
    @ManyToOne
    private Beosztas beosztas;
    @ManyToOne
    private Zalogfiok zalogfiok;

    public Dolgozo(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Beosztas getBeosztas() {
        return beosztas;
    }

    public void setBeosztasId(Beosztas beosztas) {
        this.beosztas = beosztas;
    }

    public Zalogfiok getZalogfiok() {
        return zalogfiok;
    }

    public void setZalogfiok(Zalogfiok zalogfiok) {
        this.zalogfiok = zalogfiok;
    }

    public void setBeosztas(Beosztas beosztas) {
        this.beosztas = beosztas;
    }
}
