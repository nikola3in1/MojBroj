package com.nikola2934.RequestModel;

import java.util.ArrayList;

public class SolveRequest {
    private ArrayList<Integer> numbers;
    private Integer target;

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    public Integer getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "SolveRequest{" +
                "numbers=" + numbers +
                ", target=" + target +
                '}';
    }
}
