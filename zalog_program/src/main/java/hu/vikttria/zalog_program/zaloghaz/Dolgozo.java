package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;

@Entity
public class Dolgozo {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;
    private String nev;
    private String telefon;
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToOne
    private Beosztas beosztas;
    @ManyToOne
    private Zalogfiok zalogfiok;

    public Dolgozo(){}

    public Dolgozo(String nev, String telefon, String email, Zalogfiok zalogfiok, Beosztas beosztas){
        this.nev = nev;
        this.telefon = telefon;
        this.email = email;
        this.zalogfiok = zalogfiok;
        this.beosztas = beosztas;
    }

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

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
