/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.RollbackException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author miteshpatekar
 */
public class DepartmentTest extends AbstractDepartmentTest {
     private static final Logger LOG = Logger.getLogger(DepartmentTest.class.getName());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // testing my read operation.  I hope your javadoc comments are better than mine.
    @Test
    public void testFindExistingDepartment() {
        // if knew the PK - easy as em.find
        // this lets us use named parameters and queries
        Department department = em.createNamedQuery("Department.findByName", Department.class)
                .setParameter("name", "departmenta")
                .getSingleResult();
        LOG.log(Level.INFO, "TestFindExistingDepartment: {0}", department);
        assertTrue("Name should match", "departmenta".equals(department.getName()));
    }

    // test a removal by creating a new department, and then removing
    // assert that you can no longer find it from the database
    @Test
    public void testRemoveDepartment(){
         Department department = new Department("tempdepartment","loca");
         // create department
         tx.begin();
         em.persist(department);
         tx.commit();
         
         // check department is created and then remove it
         tx.begin();
         assertNotNull("ID should be populated after commit", department.getId());
         LOG.log(Level.INFO, "TestRemoveDepartment: Before removal {0}", department.toString());
         em.remove(department);
         tx.commit();
        
         // assert department not present
         department= em.find(Department.class, department.getId());
         LOG.info("TestRemoveDepartment: After removal department is: "+ department);
         assertNull(department);
    }
    
    // test an update to the seed data
    // first, find the seed data and assert it is what you expect (from @Before fixture)
    // then make an update and commit
    // remember, an update can be em.merge or you can call set methods on a managed entity, inside the transaction
    // read it back from the database
    // assert the update is what you expect
    @Test
    public void testUpdateDepartment(){
        Department department = em.createNamedQuery("Department.findByName", Department.class)
                .setParameter("name", "departmenta")
                .getSingleResult();
        
        assertTrue("Department with name departmenta and age 27 present", "loca".equals(department.getLocation()));
        LOG.log(Level.INFO, "TestUpdateDepartment: Before updating {0}", department.getLocation());

        tx.begin();
        department.setLocation("locb");
        tx.commit();
        
        Department updatedDepartment = em.createNamedQuery("Department.findByName", Department.class)
                .setParameter("name", "departmenta")
                .getSingleResult();
        LOG.log(Level.INFO, "TestUpdateDepartment: After updating {0}", department.toString());
        assertTrue("Age of departmenta has successfully updated", "locb".equals(updatedDepartment.getLocation()));
        
    }
    
    
    @Test
    public void testCreateNewValidDepartment() {
        
        Department department = new Department("departmentb","locb");
              
        tx.begin();
        LOG.log(Level.INFO, "TestCreateNewValidDepartment: Before creation {0}", department.toString());

        assertNull("ID should be null before em.persist and commit.", department.getId());
        em.persist(department);
        assertNull("How about now?  ID should be null after em.persist and before commit.", department.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", department.getId());
        LOG.log(Level.INFO, "TestCreateNewValidDepartment: After creation {0}", department.toString());
        assertTrue("ID should be greater than 0", department.getId() > 0l);
    }

    @Test(expected = RollbackException.class)
    public void testCreateInvalidDepartmentDatabaseFail() {
       
        Department department = new Department("dep",null);
        tx.begin();
        LOG.log(Level.INFO, "TestCreateInvalidDepartmentDatabaseFail: Before creating department with null valued name {0}", department.toString());

        assertNull("ID should be null before em.persist and commit.", department.getId());
        em.persist(department);
        assertNull("How about now?  ID should be null after em.persist and before commit.", department.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", department.getId());
        LOG.info(department.toString());
        assertTrue("ID should be greater than 0", department.getId() > 0l);
        
    }

    @Test
    public void testDepartmentTeacherOneToManyRelation() {
        Department department = new Department("departmentb","locb");
        Teacher teacher= new Teacher("teachera",23);
        
      
        department.getTeacherCollection().add(teacher);
       
        teacher.setDepartmentId(department);
       
        tx.begin();
        em.persist(department);
        em.persist(teacher);
        tx.commit();
        
        Department findDept=em.find(Department.class,department.getId());
        
        Teacher findTeacher=em.find(Teacher.class, teacher.getId());
       
        assertEquals(department.getName(),findDept.getName());
        assertEquals(teacher.getDepartmentId(),findTeacher.getDepartmentId());

        
        // clean up
        tx.begin();
        em.remove(department);
        em.remove(teacher);
        tx.commit();
    }
    
    @Test
    public void testDepartmentCourseOneToManyRelation() {
        Department department = new Department("departmentb","locb");
        Course course= new Course("course",3);
        
      
        department.getCourseCollection().add(course);
       
        course.setDepartmentId(department);
       
        tx.begin();
        em.persist(department);
        em.persist(course);
        tx.commit();
        
        Department findDept=em.find(Department.class,department.getId());
        
        Course findCourse=em.find(Course.class, course.getId());
       
        assertEquals(department.getName(),findDept.getName());
        assertEquals(course.getDepartmentId(),findCourse.getDepartmentId());

        
        // clean up
        tx.begin();
        em.remove(department);
        em.remove(course);
        tx.commit();
    }
}
