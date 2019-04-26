package com.nikola2934.Optimization;

import com.nikola2934.Service.RPN;

import java.util.*;

public class SolverService2 {
    private static HashSet<ArrayList<String>> podskupovi = new HashSet<>();
    private static HashMap<Izraz, Integer> izrazi = new HashMap<>();
    private static ArrayList<String> pomocniIzraz = new ArrayList<>();
    private static HashSet<Izraz> resenja = new HashSet<>();
    private static int target;
    private static int brojElemenata;

    public static void main(String[]args){
        SolverService2 srv = new SolverService2(new ArrayList<String>(Arrays.asList(new String[]{"1","3","3","4","5"})),15);
    }

    public SolverService2(ArrayList<String> numbers, int target) {
        SolverService2.target = target;
        SolverService2.brojElemenata = numbers.size();

        System.out.println("Elementi: " + Arrays.toString(numbers.toArray()) + "\nTrazeno resenje: " + target);
        System.out.println("Resavanje...");
        for (String i : numbers) {
            pomocniIzraz.add(i + "");
        }

        resi(numbers);
        stampajResenja();
        System.out.println("Broj resenja :"+ resenja.size());
//        logovanje();
    }

    private static void resi(ArrayList<String> numbers) {
        getPodskupovi(numbers, 0, 2, new ArrayList<>());

        String znakovi = "+-*/";

        //Biranje podskupa
        for (ArrayList<String> ps : podskupovi) {
            //Biranje operacije
            for (Character znak : znakovi.toCharArray()) {
                Izraz izraz = new Izraz(ps.size()+1);
                //Gradjenje izraza
                izraz.setE(convertToArray(ps));
                izraz.setZnak(znak);
                //Proveravamo da li je vrednost izraza razlicita od 0
                int val;
                if ((val = RPN.racunaj(izraz.getE())) != 0) {
                    izrazi.put(izraz, val);
                }
            }
        }
        ciklus();
    }


    private static void ciklus() {
        boolean reseno = false;
        //Pozivamo 4 ciklusa tj. trazimo izraz dubine 4. stepena
        /*
        *
        * s1 =  a b + c
        * s2 =  a b + c + d   <=>  s1 d +
        * s3 =  a b + c + d + e   <=>   s2 e +
        * s4 =  a b + c + d + e + f   <=>   s3 f +
        *
        * U slucaju da moguce resenje postoji,
        * bice pronadjeno u najvise 4 poziva funkcije ciklus().
        *
        * */

        //TODO NASTAVI NA DALJE

            for (int ciklus = 0; ciklus < brojElemenata; ciklus++) {
                if (!reseno) {
                //Kopiranje mape
                HashMap<Izraz, Integer> izraziNovi = new HashMap<>();
                izraziNovi.putAll(izrazi);

                for (Map.Entry<Izraz, Integer> entry : izraziNovi.entrySet()) {
                    Izraz stariIzraz = entry.getKey();
//                if (stariIzraz.length >= 10) {
//                    continue;
//                }
                    // Dodajemo listu svih elemenata iz koje brisemo
                    // sve elemente koji se vec nalaze u izrazu
                    String broj = "";
                    ArrayList<String> pomocnaLista = new ArrayList<>();
                    pomocnaLista.addAll(pomocniIzraz);
                    for (String karakter : stariIzraz.getE()) {
                        pomocnaLista.remove(karakter);
                    }

                    while (!pomocnaLista.isEmpty()) {
                        //Biranje kandidata
                        broj = pomocnaLista.get(0);
                        pomocnaLista.remove(0);

                        String[] noviIzraz = new String[stariIzraz.size() + 2];
                        //Prepisi stari niz
                        for (int i = 0; i < stariIzraz.size(); i++) {
                            noviIzraz[i] = stariIzraz.getE()[i];
                        }

                        String operacije = "+-/*";

                        //Dodavanje novog elementa
                        noviIzraz[stariIzraz.size()] = broj;

                        //Biranje operacije
                        for (int i = 0; i < operacije.length(); i++) {
                            String znak = operacije.charAt(i) + "";
                            Izraz temp = new Izraz(noviIzraz.clone());
                            temp.getE()[stariIzraz.size() + 1] = znak;

                            int rezultat = RPN.racunaj(temp.getE());

                            //Pronasli smo resenje
                            if (rezultat == target) {
                                if (!resenja.contains(temp)) {
                                    resenja.add(temp);
                                }
                                System.out.println(Arrays.toString(temp.getE()));
                                reseno = true;
                                break;
                            }
                            //Dodaj novi izraz
                            if (rezultat != 0) {
                                izrazi.putIfAbsent(temp, rezultat);
                            }
                        }

                        //Mnozenje drugi slucaj
                        if (noviIzraz[noviIzraz.length - 3].equals("+") || noviIzraz[noviIzraz.length - 3].equals("-")) {
                            String slabijiZnak = noviIzraz[noviIzraz.length - 3];
                            String jakiZnak = operacije.charAt(3) + "";
                            Izraz temp = new Izraz(noviIzraz.clone());
                            temp.getE()[temp.size() - 3] = temp.getE()[temp.size() - 2];
                            temp.getE()[temp.size() - 2] = jakiZnak;
                            temp.getE()[temp.size() - 1] = slabijiZnak;

                            int rezultat = RPN.racunaj(temp.getE());

                            //Pronasli smo resenje
                            if (rezultat == target) {
                                if (!resenja.contains(temp)) {
                                    resenja.add(temp);
                                }
                                System.out.println(Arrays.toString(temp.getE()));
                                reseno = true;
                                break;

                            }
                            //Dodaj novi izraz
                            if (rezultat != 0) {
                                izrazi.putIfAbsent(temp, rezultat);
                            }
                        }
                    }
                }
            }
        }
    }


    private static void getPodskupovi(ArrayList<String> numbers, int currSize, int k, ArrayList<String> partial) {
        if (partial.size() == k) {
            podskupovi.add(partial);
        }

        for (int i = 0; i < numbers.size(); i++) {
            ArrayList<String> remaining = new ArrayList<String>();
            String n = numbers.get(i);
            for (int j = i + 1; j < numbers.size(); j++) remaining.add(numbers.get(j));

            ArrayList<String> partial_rec = new ArrayList<String>(partial);
            partial_rec.add(n);
            getPodskupovi(remaining, currSize + 1, k, partial_rec);
        }
    }

    private static void stampajResenja() {
        //Stampanje resenja
        if (resenja.size() > 0) {
            for (Izraz izraz : resenja) {
                System.out.println(RPN.postToInfix(izraz.getE()) + " = " + target);
            }
        } else {
            System.out.println("Ne postoji resenje, proverite da li ste uneli ispravne brojeve.");
        }
    }

    private static String[] convertToArray(ArrayList<String> list) {
        String[] arr = new String[list.size() + 1];
        arr = list.toArray(arr);
        return arr;
    }


}
class Izraz{
    private String[] elementi;

    Izraz(Integer size){
        this.elementi = new String[size];
    }

    Izraz(String ... elementi){
        this.elementi = elementi;
    }

    public void setZnak(Character element){
        elementi[size()-1] = element+"";
    }

    public Izraz getKomutativno(){
        return new Izraz(elementi[1], elementi[0], elementi[2]);
    }


    public String[] getE() {
        return elementi;
    }

    public void setE(String[] elementi) {
        this.elementi = elementi;
    }
    
    public Integer size(){
        return this.elementi.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Izraz izraz = (Izraz) o;
        return Arrays.equals(elementi, izraz.elementi);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elementi);
    }

    @Override
    public String toString() {
        return "Izraz{" +
                "elementi=" + Arrays.toString(elementi) +
                '}';
    }
}
