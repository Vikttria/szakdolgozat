package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Zalogjegy {

    @GeneratedValue
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
    private Date beadas;
//    private Date lejarat;
    @Column(nullable = false)
    private int osszeg;
    @ManyToOne
    private Zalogfiok zalogfiok;
    @ManyToOne
    private Ugyfel ugyfel;
    @OneToOne
    private Megjegyzes megjegyzes;

    public Zalogjegy() {
    }

    public Zalogjegy(String leiras, int karat, double suly, int dbSzam, int osszeg, Date beadas, Ugyfel ugyfel){
        this.leiras = leiras;
        this.karat = karat;
        this.suly = suly;
        this. dbSzam = dbSzam;
        this.osszeg = osszeg;
        this.beadas = beadas;
        this.ugyfel = ugyfel;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getBeadas() {
        return beadas;
    }

    public void setBeadas(Date beadas) {
        this.beadas = beadas;
    }

/*    public Date getLejarat() {
        return lejarat;
    }

    public void setLejarat(Date lejarat) {
        this.lejarat = lejarat;
    }*/

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
