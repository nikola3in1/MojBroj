package com.nikola2934.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/*")
public class ApiController {

    @PostMapping("solve")
    public Map<String, Object> solve(@RequestBody Map<String, Object> body) {
        ArrayList<Integer> numbers = (ArrayList<Integer>) body.get("numbers");
        return body;
    }

    @GetMapping("testin")
    public String testing() {
        return "WORKS!";
    }
}
