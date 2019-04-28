package com.nikola2934.Controller;

import com.nikola2934.RequestModel.SolveRequest;
import com.nikola2934.Service.Solver.SolverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/*")
public class ApiController {

    private final SolverService solverService;

    public ApiController(SolverService solverService) {
        this.solverService = solverService;
    }

    @PostMapping("solve")
    public ResponseEntity<Map<String,Object>> solve(@RequestBody SolveRequest body) {
        ArrayList<Integer> numbers = body.getNumbers();
        Integer target = body.getTarget();

        Map<String, Object> respData = new HashMap<>();
        try {
            ArrayList<String> solutions = solverService.findSolution(numbers, target);
            respData.put("solutions", solutions);
            respData.put("success", true);
            return new ResponseEntity<>(respData, HttpStatus.OK);
        }catch (Exception e) {
            respData.put("success", false);
            respData.put("message", e.getMessage());
            return new ResponseEntity<>(respData, HttpStatus.BAD_REQUEST);
        }
    }
}
