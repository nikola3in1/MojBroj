package com.nikola2934.Service.Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Izraz {
    private ArrayList<String> elementi;

    public Izraz() {
        this.elementi = new ArrayList<>();
    }

    public Izraz(Izraz copy) {
        this.elementi = new ArrayList<>(copy.elementi);
    }

    public Izraz(ArrayList<String> elementi) {
        this.elementi = new ArrayList<>(elementi);
    }

    //Util

    public void push(String e) {
        elementi.add(e);
    }

    public Integer size() {
        return this.elementi.size();
    }

    public boolean isZnak(String znak) {
        return this.elementi.get(this.elementi.size() - 3).equals(znak);
    }

    //Get-set

    public ArrayList<String> getE() {
        return elementi;
    }

    public void setE(ArrayList<String> elementi) {
        this.elementi = elementi;
    }

    public String getZnak() {
        return this.elementi.get(this.elementi.size() - 3);
    }

    public void setElement(int position, String element) {
        this.elementi.set(position, element);
    }

    public String getElement(int position) {
        return this.elementi.get(position);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Izraz izraz = (Izraz) o;
        return Objects.equals(elementi, izraz.elementi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementi);
    }

    @Override
    public String toString() {
        return "Izraz{" +
                "elementi=" + Arrays.toString(elementi.toArray()) +
                '}';
    }
}
