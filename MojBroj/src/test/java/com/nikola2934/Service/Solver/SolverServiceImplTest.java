package com.nikola2934.Service.Solver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SolverServiceImpl.class)
@AutoConfigureMockMvc
public class SolverServiceImplTest {

    private long started;
    private String line = "+++++++++++++++++++++++++++++++++++=";

    @Autowired
    private SolverService service;

    @Before
    public void start(){
        started = System.currentTimeMillis();
    }

    @After
    public void end() {
        System.out.println("\n > Ms taken: " + (System.currentTimeMillis() - started));
        System.out.println(line);
    }

    @Test
    public void findSolution_usualInput(){
        String testName = "Usual input";
        System.out.println("Test name: "+testName);
        Integer [] numbersArr = new Integer[]{3,3,5,9,15,25};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
        Integer target = 397;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
            assertTrue(true);
        } catch (InvalidParameterException e) {
            fail();
        }
    }

    @Test
    public void findSolution_emptyElementList(){
        String testName = "Empty element list";
        System.out.println("Test name: "+testName);
        Integer [] numbersArr = new Integer[]{};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
        Integer target = 397;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
            fail();
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findSolution_tooMuchElements(){
        String testName = "Too much elements";
        System.out.println("Test name: "+testName);
        Integer [] numbersArr = new Integer[]{3,3,5,9,15,25,3};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
        Integer target = 397;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
            fail();
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findSolution_listIsNull(){
        String testName = "List is null";
        System.out.println("Test name: "+testName);

        ArrayList<Integer> numbers = null;
        Integer target = 397;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }

    }

    @Test
    public void findSolution_targetIsNull(){
        String testName = "Target number is null";
        System.out.println("Test name: "+testName);
        Integer [] numbersArr = new Integer[]{3,3,5,9,15,25};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
        Integer target = null;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findSolution_negativeTargetNumber(){
        String testName = "Negative target number";
        System.out.println("Test name: "+testName);;
        Integer [] numbersArr = new Integer[]{3,3,5,9,15,25};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
        Integer target = -1;
        try {
            ArrayList<String> result = service.findSolution(numbers, target);
        } catch (InvalidParameterException e) {
            assertTrue(true);
        }
    }

    @Test
    public void findSolution_stressTestValidInput(){
        String testName = "Stress test with valid input";
        System.out.println("Test name: "+testName);
        for (int i = 0; i < 100; i++) {
                Integer [] numbersArr = new Integer[]{3,3,5,9,15,25};
                ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArr));
                Integer target = 397;
                try {
                    service.findSolution(numbers, target);
                    assertTrue(true);
                } catch (InvalidParameterException e) {
                    fail();
                }
        }
    }

}