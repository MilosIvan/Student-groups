package com.example.model;

import com.example.enumeracije.Fakultet;
import com.example.enumeracije.Grad;

import java.util.ArrayList;
import java.util.List;

public class KlubStudenata extends Entitet {
    private Fakultet fakultet;
    private Grad grad;
    private Integer brojStudenata;
    private List<Student> studenti;

    public KlubStudenata(Integer id, String naziv, Fakultet fakultet, Grad grad) {
        super(id, naziv);
        this.fakultet = fakultet;
        this.grad = grad;
        studenti = new ArrayList<>();
        brojStudenata = 0;
    }

    public void dodajStudenta (Student student) {
        if(!studenti.contains(student)) {
            studenti.add(student);
            brojStudenata++;
        }
        else System.out.println("Student " + student.getNaziv() + " je već član kluba " + this.getNaziv());
    }
    public void ukloniStudenta (Student student) {
        if(studenti.remove(student)) brojStudenata--;
        else System.out.println("Student " + student.getNaziv() + " nije član kluba " + this.getNaziv());
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
        brojStudenata = studenti.size();
    }
    public Integer getBrojStudenata() {
        return brojStudenata;
    }
    public List<Student> getStudenti() {
        return studenti;
    }
    public Fakultet getFakultet() {
        return fakultet;
    }
    public Grad getGrad() {
        return grad;
    }

    @Override
    public String toString() {
        return fakultet.toString();
    }
}
