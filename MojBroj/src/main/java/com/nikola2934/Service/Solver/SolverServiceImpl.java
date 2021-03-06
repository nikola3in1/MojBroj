package com.nikola2934.Service.Solver;

import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.*;

@Service
public class SolverServiceImpl implements SolverService {
    private HashSet<ArrayList<String>> podskupovi = new HashSet<>();
    private HashMap<Izraz, Integer> izrazi = new HashMap<>();
    private ArrayList<String> pomocniIzraz = new ArrayList<>();
    private HashSet<Izraz> resenja = new HashSet<>();
    private int target;
    private int brojElemenata;

    @Override
    public ArrayList<String> findSolution(ArrayList<Integer> numbers, Integer target) throws InvalidParameterException {
        validateInput(numbers, target);
        init();
        this.target = target;
        this.brojElemenata = numbers.size();

        System.out.println("Elementi: " + Arrays.toString(numbers.toArray()) + "\nTrazeno resenje: " + target);
        System.out.println("Resavanje...");

        //Formatiranje
        ArrayList<String> numbersStr = new ArrayList<>();
        numbers.forEach(integer -> numbersStr.add(integer+""));
        pomocniIzraz = new ArrayList<>(numbersStr);
        resavanje(numbersStr);
        System.out.println("Broj resenja :" + resenja.size());
        return formatirajResenja();
    }

    private void validateInput(ArrayList<Integer> numbers, Integer target) {
        if (numbers == null || numbers.isEmpty()) {
            throw new InvalidParameterException("Invalid list of elements");
        }
        if (numbers.size() > 6) {
            throw new InvalidParameterException("To many elements");
        }
        if (target == null) {
            throw new InvalidParameterException("Invalid target value");
        }
        if (target <= 0) {
            throw new InvalidParameterException("Target number must be grater than 0");
        }
    }

    private void init() {
        podskupovi = new HashSet<>();
        izrazi = new HashMap<>();
        pomocniIzraz = new ArrayList<>();
        resenja = new HashSet<>();
        target = 0;
        brojElemenata = 0;
    }

    private void resavanje(ArrayList<String> numbers) {
        getPodskupovi(numbers, 0, 2, new ArrayList<>());

        String znakovi = "+-*/";
        //Biranje podskupa
        for (ArrayList<String> ps : podskupovi) {
            //Biranje operacije
            for (Character znak : znakovi.toCharArray()) {
                Izraz izraz = new Izraz(ps);
                //Gradjenje izraza
                izraz.push(znak + "");
                //Proveravamo da li je vrednost izraza razlicita od 0
                int val;
                if ((val = RPN.racunaj(izraz.getE())) != 0) {
                    izrazi.put(izraz, val);
                }
            }
        }
        ciklus();
    }

    private void ciklus() {
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

        for (int ciklus = 0; ciklus < brojElemenata; ciklus++) {
            if (!reseno) {
                //Kopiranje mape
                HashMap<Izraz, Integer> izraziNovi = new HashMap<>(izrazi);

                for (Map.Entry<Izraz, Integer> izraz : izraziNovi.entrySet()) {
                    Izraz stariIzraz = izraz.getKey();

                    // Razlika elemenata, u novoj listi iskljucujemo elemente iz starog izraza
                    ArrayList<String> pomocnaLista = getRazlika(pomocniIzraz, stariIzraz);

                    while (!pomocnaLista.isEmpty()) {
                        //Biranje kandidata
                        String broj = pomocnaLista.get(0);
                        pomocnaLista.remove(0);

                        Izraz noviIzraz = new Izraz(stariIzraz);
                        String operacije = "+-/*";

                        //Dodavanje novog elementa
                        noviIzraz.push(broj);

                        //Biranje operacije
                        for (int i = 0; i < operacije.length(); i++) {
                            String znak = operacije.charAt(i) + "";
                            Izraz temp = new Izraz(noviIzraz);
                            temp.push(znak);
                            int rezultat = RPN.racunaj(temp.getE());

                            if (rezultat != 0) {
                                //Pronasli smo resenje
                                if (rezultat == target) {
                                    resenja.add(temp);
                                    reseno = true;
                                    break;
                                }
                                //Dodaj novi izraz
                                izrazi.putIfAbsent(temp, rezultat);
                            }
                        }

                        //Mnozenje drugi slucaj
                        if (noviIzraz.isZnak("+") || noviIzraz.isZnak("-")) {
                            String slabijiZnak = noviIzraz.getZnak();
                            String jakiZnak = operacije.charAt(3) + "";
                            Izraz temp = new Izraz(noviIzraz);
                            temp.setElement(temp.size() - 3, temp.getElement(temp.size() - 2));
                            temp.setElement(temp.size() - 2, jakiZnak);
                            temp.setElement(temp.size() - 1, slabijiZnak);

                            int rezultat = RPN.racunaj(temp.getE());

                            if (rezultat != 0) {
                                //Pronasli smo resenje
                                reseno = isResenje(rezultat, temp);
                                //Dodaj novi izraz
                                izrazi.putIfAbsent(temp, rezultat);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isResenje(int rezultat, Izraz izraz) {
        //Imamo novo resenje
        if (rezultat == target) {
            resenja.add(izraz);
            return true;
        }
        return false;
    }

    private ArrayList<String> getRazlika(ArrayList<String> pomocniIzraz, Izraz stariIzrazE) {
        ArrayList<String> pomocnaLista = new ArrayList<>(pomocniIzraz);
        for (String karakter : stariIzrazE.getE()) {
            pomocnaLista.remove(karakter);
        }
        return pomocnaLista;
    }


    private void getPodskupovi(ArrayList<String> numbers, int currSize, int k, ArrayList<String> partial) {
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

    private ArrayList<String> formatirajResenja() {
        //Stampanje resenja
        ArrayList<String> formatiranaResenja = new ArrayList<>();
        if (resenja.size() > 0) {
            for (Izraz izraz : resenja) {
                formatiranaResenja.add(RPN.postToInfix(izraz.getE()) + " = " + target);
            }
        } else {
            System.out.println("Ne postoji resenje, proverite da li ste uneli ispravne brojeve.");
        }
        return formatiranaResenja;
    }


}

