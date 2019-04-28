package com.nikola2934.Service.Solver;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public interface SolverService {
    ArrayList<String> findSolution(ArrayList<Integer> numbers, Integer target) throws InvalidParameterException;
}
