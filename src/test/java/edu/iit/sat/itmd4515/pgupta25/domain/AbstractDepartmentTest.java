/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import java.time.LocalDate;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
/**
 *
 * @author miteshpatekar
 */
public class AbstractDepartmentTest {
      private static EntityManagerFactory emf;
    protected EntityManager em;
    protected EntityTransaction tx;

    @BeforeClass
    public static void beforeClassTestFixtureRunsOncePerClass() {
        emf = Persistence.createEntityManagerFactory("itmd4515testPU");
    }

    @AfterClass
    public static void afterClassTestFixtureRunsOncePerClass() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void beforeEachTestFixture() {
        em = emf.createEntityManager();
        tx = em.getTransaction();

        // create my test data before each @Test case
        Department department = new Department("departmenta","loca");
        tx.begin();
        em.persist(department);
        tx.commit();
    }

    @After
    public void afterEachTestFixture() {
        
        // clean up my test data after each @Test case
        Department department = em.createNamedQuery("Department.findByName", Department.class)
                .setParameter("name", "departmenta")
                .getSingleResult();

        tx.begin();
        em.remove(department);
        tx.commit();
        
        if (em != null) {
            em.close();
        }
    }

}
