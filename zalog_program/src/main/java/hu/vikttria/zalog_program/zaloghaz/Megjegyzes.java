package hu.vikttria.zalog_program.zaloghaz;

import javax.persistence.*;

@Entity
public class Megjegyzes {

    @GeneratedValue
    @Id
    private long id;
    private String megjegyzes;
    @OneToOne
    private Zalogjegy zalogjegy;

    public Megjegyzes(){}

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public Zalogjegy getZalogjegy() {
        return zalogjegy;
    }

    public void setZalogjegy(Zalogjegy zalogjegy) {
        this.zalogjegy = zalogjegy;
    }
}
