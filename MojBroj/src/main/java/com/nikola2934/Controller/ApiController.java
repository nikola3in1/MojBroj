package com.nikola2934.Controller;

import com.nikola2934.Service.Solver.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/*")
public class ApiController {

    @Autowired
    private SolverService solverService;

    @PostMapping("solve")
    public ArrayList<String> solve(@RequestBody Map<String, Object> body) {
        ArrayList<Integer> numbersRaw = (ArrayList<Integer>) body.get("numbers");
        System.out.println(Arrays.toString(numbersRaw.toArray()));
        Integer target = (Integer) body.get("target");
        ArrayList<String> numbers = new ArrayList<>();
        for (Integer i : numbersRaw) {
            numbers.add(i + "");
        }
        ArrayList<String> solutions = solverService.findSolution(numbers, target);
        return solutions;
    }

    @GetMapping("testin")
    public String testing() {
        return "WORKS!";
    }
}
