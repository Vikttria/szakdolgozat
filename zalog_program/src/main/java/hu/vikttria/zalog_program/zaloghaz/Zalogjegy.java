package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Zalogjegy {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private long id;
    @Column(nullable = false)
    private String leiras;
    @Column(nullable = false)
    private int karat;
    @Column(nullable = false)
    private double suly;
    @Column(nullable = false)
    private int dbSzam;
    @Column(nullable = false)
    private LocalDate beadas;
    @Column(nullable = false)
    private int osszeg;
    @ManyToOne
    private Zalogfiok zalogfiok;
    @ManyToOne
    private Ugyfel ugyfel;

    public Zalogjegy() {
    }

    public Zalogjegy(String leiras, int karat, double suly, int dbSzam, int osszeg, LocalDate beadas, Ugyfel ugyfel, Zalogfiok zalogfiok){
        this.leiras = leiras;
        this.karat = karat;
        this.suly = suly;
        this.dbSzam = dbSzam;
        this.osszeg = osszeg;
        this.beadas = beadas;
        this.ugyfel = ugyfel;
        this.zalogfiok = zalogfiok;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public int getKarat() {
        return karat;
    }

    public void setKarat(int karat) {
        this.karat = karat;
    }

    public double getSuly() {
        return suly;
    }

    public void setSuly(double suly) {
        this.suly = suly;
    }

    public int getDbSzam() {
        return dbSzam;
    }

    public void setDbSzam(int dbSzam) {
        this.dbSzam = dbSzam;
    }

    public LocalDate getBeadas() {
        return beadas;
    }

    public void setBeadas(LocalDate beadas) {
        this.beadas = beadas;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(int osszeg) {
        this.osszeg = osszeg;
    }

    public Zalogfiok getZalogfiok() {
        return zalogfiok;
    }

    public void setZalogfiok(Zalogfiok zalogfiok) {
        this.zalogfiok = zalogfiok;
    }

    public Ugyfel getUgyfel() {
        return ugyfel;
    }

    public void setUgyfel(Ugyfel ugyfel) {
        this.ugyfel = ugyfel;
    }

}
