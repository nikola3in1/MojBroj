//package com.nikola2934.Service;
//
//import java.io.*;
//import java.util.*;
//
//public class SolverService  {
//    private static HashSet<ArrayList<Integer>> podskupovi = new HashSet<>();
//    private static HashMap<String[], Integer> izrazi = new HashMap<>();
//    private static ArrayList<String> pomocniIzraz = new ArrayList<>();
//    private static HashSet<String[]> resenja = new HashSet<>();
//    private static int target;
//
//    public SolverService(ArrayList<Integer> numbers, int target) {
//        SolverService.target = target;
//
//        System.out.println("Elementi: " + Arrays.toString(numbers.toArray()) + "\nTrazeno resenje: " + target);
//        System.out.println("Resavanje...");
//        for (Integer i : numbers) {
//            pomocniIzraz.add(i + "");
//        }
//
//        resi(numbers);
//        stampajResenja();
//        System.out.println("Broj resenja :"+ resenja.size());
////        logovanje();
//    }
//
//    private static void resi(ArrayList<Integer> numbers) {
//        getPodskupovi(numbers, 0, 2, new ArrayList<>());
//        String znakovi = "+-*/";
//
//        //Biranje podskupa
//        for (ArrayList<Integer> ps : podskupovi) {
//            //Biranje operacije
//            for (int z = 0; z < znakovi.length(); z++) {
//                String[] izraz = new String[ps.size() + 1];
//
//                //Gradjenje izraza
//                char znak = znakovi.charAt(z);
//                for (int i = 0; i < ps.size(); i++) {
//                    izraz[i] = ps.get(i) + "";
//                }
//                izraz[ps.size()] = znak + "";
//
//                //Provera da li je znak komutativan
//                if (znak == '+' || znak == '*') {
//                    String[] komutativnost = {izraz[1], izraz[0], izraz[2]};
//                    if (!izrazi.containsKey(izrazi) && !izrazi.containsKey(komutativnost)) {
//                        int vrednost = RPN.racunaj(izraz);
//                        if (vrednost != 0) {
//                            izrazi.put(izraz, vrednost);
//                        }
//                    }
//                } else {
//                    if (!izrazi.containsKey(izrazi)) {
//                        int vrednost = RPN.racunaj(izraz);
//                        if (vrednost != 0) {
//                            izrazi.put(izraz, vrednost);
//                        }
//                    }
//                }
//            }
//        }
//        ciklus();
//    }
//
//
//    private static void ciklus() {
//        boolean reseno = false;
//        //Pozivamo 4 ciklusa tj. trazimo izraz dubine 4. stepena
//        /*
//        *
//        * s1 =  a b + c
//        * s2 =  a b + c + d   <=>  s1 d +
//        * s3 =  a b + c + d + e   <=>   s2 e +
//        * s4 =  a b + c + d + e + f   <=>   s3 f +
//        *
//        * U slucaju da moguce resenje postoji,
//        * bice pronadjeno u najvise 4 poziva funkcije ciklus().
//        *
//        * */
//
//            for (int ciklus = 0; ciklus < 6; ciklus++) {
//                if (!reseno) {
//                //Kopiranje mape
//                HashMap<String[], Integer> izraziNovi = new HashMap<>();
//                izraziNovi.putAll(izrazi);
//
//                for (Map.Entry<String[], Integer> entry : izraziNovi.entrySet()) {
//                    String[] stariIzraz = entry.getKey();
////                if (stariIzraz.length >= 10) {
////                    continue;
////                }
//                    // Dodajemo listu svih elemenata iz koje brisemo
//                    // sve elemente koji se vec nalaze u izrazu
//                    String broj = "";
//                    ArrayList<String> pomocnaLista = new ArrayList<>();
//                    pomocnaLista.addAll(pomocniIzraz);
//                    for (String karakter : stariIzraz) {
//                        pomocnaLista.remove(karakter);
//                    }
//
//                    while (!pomocnaLista.isEmpty()) {
//                        //Biranje kandidata
//                        broj = pomocnaLista.get(0);
//                        pomocnaLista.remove(0);
//
//                        String[] noviIzraz = new String[stariIzraz.length + 2];
//                        //Prepisi stari niz
//                        for (int i = 0; i < stariIzraz.length; i++) {
//                            noviIzraz[i] = stariIzraz[i];
//                        }
//
//                        String operacije = "+-/*";
//
//                        //Dodavanje novog elementa
//                        noviIzraz[stariIzraz.length] = broj;
//
//                        //Biranje operacije
//                        for (int i = 0; i < operacije.length(); i++) {
//                            String znak = operacije.charAt(i) + "";
//                            String[] temp = noviIzraz.clone();
//                            temp[stariIzraz.length + 1] = znak;
//
//                            int rezultat = RPN.racunaj(temp);
//
//                            //Pronasli smo resenje
//                            if (rezultat == target) {
//                                if (!resenja.contains(temp)) {
//                                    resenja.add(temp);
//                                }
//                                System.out.println(Arrays.toString(temp));
//                                reseno = true;
//                                break;
//                            }
//                            //Dodaj novi izraz
//                            if (rezultat != 0) {
//                                izrazi.putIfAbsent(temp, rezultat);
//                            }
//                        }
//
//                        //Mnozenje drugi slucaj
//                        if (noviIzraz[noviIzraz.length - 3].equals("+") || noviIzraz[noviIzraz.length - 3].equals("-")) {
//                            String slabijiZnak = noviIzraz[noviIzraz.length - 3];
//                            String jakiZnak = operacije.charAt(3) + "";
//                            String[] temp = noviIzraz.clone();
//                            temp[temp.length - 3] = temp[temp.length - 2];
//                            temp[temp.length - 2] = jakiZnak;
//                            temp[temp.length - 1] = slabijiZnak;
//
//                            int rezultat = RPN.racunaj(temp);
//
//                            //Pronasli smo resenje
//                            if (rezultat == target) {
//                                if (!resenja.contains(temp)) {
//                                    resenja.add(temp);
//                                }
//                                System.out.println(Arrays.toString(temp));
//                                reseno = true;
//                                break;
//
//                            }
//                            //Dodaj novi izraz
//                            if (rezultat != 0) {
//                                izrazi.putIfAbsent(temp, rezultat);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private static void getPodskupovi(ArrayList<Integer> numbers, int currSize, int k, ArrayList<Integer> partial) {
//        if (partial.size() == k) {
//            podskupovi.add(partial);
//        }
//
//        for (int i = 0; i < numbers.size(); i++) {
//            ArrayList<Integer> remaining = new ArrayList<Integer>();
//            int n = numbers.get(i);
//            for (int j = i + 1; j < numbers.size(); j++) remaining.add(numbers.get(j));
//            ArrayList<Integer> partial_rec = new ArrayList<Integer>(partial);
//            partial_rec.add(n);
//            getPodskupovi(remaining, currSize + 1, k, partial_rec);
//        }
//    }
//
//    private static void stampajResenja() {
//        //Stampanje resenja
//        if (resenja.size() > 0) {
//            for (String[] s : resenja) {
//                System.out.println(RPN.postToInfix(s) + " = " + target);
//            }
//        } else {
//            System.out.println("Ne postoji resenje, proverite da li ste uneli ispravne brojeve.");
//        }
//    }
//}