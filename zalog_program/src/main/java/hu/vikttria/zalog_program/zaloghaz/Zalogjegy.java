package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Zalogjegy {

    @GeneratedValue
    @Id
    private int id;
    private String megnevezes;
    private int karat;
    private double suly;
    private int dbSzam;
    private Date beadas;
    private Date lejarat;
    private int osszeg;
    @ManyToOne
    private Zalogfiok zalogfiok;
    @ManyToOne
    private Ugyfel ugyfel;
    @OneToOne
    private Megjegyzes megjegyzes;

    public Zalogjegy() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
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

    public Date getBeadas() {
        return beadas;
    }

    public void setBeadas(Date beadas) {
        this.beadas = beadas;
    }

    public Date getLejarat() {
        return lejarat;
    }

    public void setLejarat(Date lejarat) {
        this.lejarat = lejarat;
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

    public Megjegyzes getMegjegyzesId() {
        return megjegyzes;
    }

    public void setMegjegyzesId(Megjegyzes megjegyzesId) {
        this.megjegyzes = megjegyzesId;
    }

    public Megjegyzes getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(Megjegyzes megjegyzes) {
        this.megjegyzes = megjegyzes;
    }
}
