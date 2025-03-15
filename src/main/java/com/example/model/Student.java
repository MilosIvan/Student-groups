package com.example.model;

import com.example.enumeracije.Fakultet;
import com.example.enumeracije.Spol;

import java.math.BigDecimal;

public class Student extends Entitet {
    private Integer godinaStudija;
    private Spol spol;
    private String jmbag;
    private BigDecimal prosjekOcjena;
    private Fakultet fakultet;

    public static class Builder {
        private final Integer id;
        private final String ime;
        private Integer godinaStudija;
        private Spol spol;
        private String jmbag;
        private BigDecimal prosjekOcjena;
        private Fakultet fakultet;

        public Builder(Integer id, String ime) {
            this.id = id;
            this.ime = ime;
        }

        public Builder godinaStudijaBuilder(Integer godinaStudija) {
            this.godinaStudija = godinaStudija;
            return this;
        }
        public Builder spolBuilder(Spol spol) {
            this.spol = spol;
            return this;
        }
        public Builder jmbagBuilder(String jmbag) {
            this.jmbag = jmbag;
            return this;
        }
        public Builder prosjekOcjenaBuilder(BigDecimal prosjekOcjena) {
            this.prosjekOcjena = prosjekOcjena;
            return this;
        }
        public Builder fakultetBuilder(Fakultet fakultet) {
            this.fakultet = fakultet;
            return this;
        }
        public Student build() {
            Student student = new Student(this.id, this.ime);
            student.godinaStudija = this.godinaStudija;
            student.spol = this.spol;
            student.jmbag = this.jmbag;
            student.prosjekOcjena = this.prosjekOcjena;
            student.fakultet = this.fakultet;
            return student;
        }
    }

    public Student(Integer id, String ime) {
        super(id, ime);
    }

    public Integer getGodinaStudija() {
        return godinaStudija;
    }
    public void setGodinaStudija(Integer godinaStudija) {
        this.godinaStudija = godinaStudija;
    }
    public String getJmbag() {
        return jmbag;
    }
    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }
    public Fakultet getFakultet() {
        return fakultet;
    }
    public void setFakultet(Fakultet fakultet) {
        this.fakultet = fakultet;
    }
    public BigDecimal getProsjekOcjena() {
        return prosjekOcjena;
    }
    public void setProsjekOcjena(BigDecimal prosjekOcjena) {
        this.prosjekOcjena = prosjekOcjena;
    }
    public Spol getSpol() {
        return spol;
    }
    public void setSpol(Spol spol) {
        this.spol = spol;
    }

    @Override
    public String toString() {
        return "Student " + this.getNaziv() + ", ID: " + this.getId().toString();
    }
}
