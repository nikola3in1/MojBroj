package com.nikola2934.Controller;

import com.nikola2934.Service.Solver.SolverService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiControllerTest {

    @Autowired
    private ApiController apiController;

    @MockBean
    private SolverService solverService;

    @Test
    public void solve() {
    }
}