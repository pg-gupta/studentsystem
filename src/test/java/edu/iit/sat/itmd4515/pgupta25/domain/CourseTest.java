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
public class CourseTest extends AbstractCourseTest {
     private static final Logger LOG = Logger.getLogger(CourseTest.class.getName());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // testing my read operation.  I hope your javadoc comments are better than mine.
    @Test
    public void testFindExistingCourse() {
        // if knew the PK - easy as em.find
        // this lets us use named parameters and queries
        Course course = em.createNamedQuery("Course.findByName", Course.class)
                .setParameter("name", "coursea")
                .getSingleResult();
        LOG.log(Level.INFO, "TestFindExistingCourse: {0}", course);
        assertTrue("Name should match", "coursea".equals(course.getName()));
    }

    // test a removal by creating a new course, and then removing
    // assert that you can no longer find it from the database
    @Test
    public void testRemoveCourse(){
         Course course = new Course("tempcourse",3);
         // create course
         tx.begin();
         em.persist(course);
         tx.commit();
         
         // check course is created and then remove it
         tx.begin();
         assertNotNull("ID should be populated after commit", course.getId());
         LOG.log(Level.INFO, "TestRemoveCourse: Before removal {0}", course.toString());
         em.remove(course);
         tx.commit();
        
         // assert course not present
         course= em.find(Course.class, course.getId());
         LOG.info("TestRemoveCourse: After removal course is: "+ course);
         assertNull(course);
    }
    
    // test an update to the seed data
    // first, find the seed data and assert it is what you expect (from @Before fixture)
    // then make an update and commit
    // remember, an update can be em.merge or you can call set methods on a managed entity, inside the transaction
    // read it back from the database
    // assert the update is what you expect
    @Test
    public void testUpdateCourse(){
        Course course = em.createNamedQuery("Course.findByName", Course.class)
                .setParameter("name", "coursea")
                .getSingleResult();
        
        assertTrue("Course with name coursea and age 27 present", 3==course.getCredits());
        LOG.log(Level.INFO, "TestUpdateCourse: Before updating {0}", course.getCredits());

        tx.begin();
        course.setCredits(4);
        tx.commit();
        
        Course updatedCourse = em.createNamedQuery("Course.findByName", Course.class)
                .setParameter("name", "coursea")
                .getSingleResult();
        LOG.log(Level.INFO, "TestUpdateCourse: After updating {0}", course.toString());
        assertTrue("Age of coursea has successfully updated", 4==updatedCourse.getCredits());
        
    }
    
    
    @Test
    public void testCreateNewValidCourse() {
        
        Course course = new Course("courseb",3);
              
        tx.begin();
        LOG.log(Level.INFO, "TestCreateNewValidCourse: Before creation {0}", course.toString());

        assertNull("ID should be null before em.persist and commit.", course.getId());
        em.persist(course);
        assertNull("How about now?  ID should be null after em.persist and before commit.", course.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", course.getId());
        LOG.log(Level.INFO, "TestCreateNewValidCourse: After creation {0}", course.toString());
        assertTrue("ID should be greater than 0", course.getId() > 0l);
    }

    @Test(expected = RollbackException.class)
    public void testCreateInvalidCourseDatabaseFail() {
       
        Course course = new Course(null,3);
        tx.begin();
        LOG.log(Level.INFO, "TestCreateInvalidCourseDatabaseFail: Before creating course with null valued name {0}", course.toString());

        assertNull("ID should be null before em.persist and commit.", course.getId());
        em.persist(course);
        assertNull("How about now?  ID should be null after em.persist and before commit.", course.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", course.getId());
        LOG.info(course.toString());
        assertTrue("ID should be greater than 0", course.getId() > 0l);
        
    }
    
    @Test
    public void testCourseStudentManyToManyRelation() {
        Student student= new Student("Brian","brian@iit.edu",new Date(),"Masters","ITM","A20413654");
        Course course= new Course("ITMD511",3);
        
        student.getCourseCollection().add(course);
        course.getStudentCollection().add(student);
        tx.begin();
        em.persist(student);
        em.persist(course);
        tx.commit();
        
        Course findcourse=em.find(Course.class,course.getId());
        
        Student findStudent=em.find(Student.class, student.getId());
        System.out.println("Course name is: "+ findcourse.getName());
        assertEquals(course.getName(),findcourse.getName());
        System.out.println("Student name is: "+ findcourse.getStudentCollection().toString());
        assertEquals(student.getCourseCollection().iterator().next().getName(),findStudent.getCourseCollection().iterator().next().getName());

        
        // clean up
        tx.begin();
        em.remove(student);
        em.remove(course);
        tx.commit();
    }   
}
