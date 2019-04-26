/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import java.util.Date;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import static junit.framework.Assert.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author miteshpatekar
 */
public class DepartmentValidationTest {
     private static Validator validator;
    
    @BeforeClass
    public static void oneTimeSetupForTestCityClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void beforeEachTest() {
        
    }

    // failure test - or rainy day scenario
    @Test
    public void testFailureBecauseNameIsNull() {
        Department department = new Department(null,"Morve ave");

        Set<ConstraintViolation<Department>> constraintViolations = validator.validate(department);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "must not be blank",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testSuccessBecauseNameIsLongEnough(){
        
        Department department = new Department("Brad","Morse ave");
        Set<ConstraintViolation<Department>> constraintViolations = validator.validate(department);
        assertEquals(0, constraintViolations.size());
    }
    
    @After
    public void afterEachTest() {

    }

    @AfterClass
    public static void oneTimeTeardownForTestClass() {
        // expensive or one-time operations go here
    }

    
}
