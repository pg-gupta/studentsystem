
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
public class TeacherTest extends AbstractTeacherTest {
     private static final Logger LOG = Logger.getLogger(TeacherTest.class.getName());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // testing my read operation.  I hope your javadoc comments are better than mine.
    @Test
    public void testFindExistingTeacher() {
        // if knew the PK - easy as em.find
        // this lets us use named parameters and queries
        Teacher teacher = em.createNamedQuery("Teacher.findByName", Teacher.class)
                .setParameter("name", "teachera")
                .getSingleResult();
        LOG.log(Level.INFO, "TestFindExistingTeacher: {0}", teacher);
        assertTrue("Name should match", "teachera".equals(teacher.getName()));
    }

    // test a removal by creating a new teacher, and then removing
    // assert that you can no longer find it from the database
    @Test
    public void testRemoveTeacher(){
         Teacher teacher = new Teacher("tempteacher",29);
         // create teacher
         tx.begin();
         em.persist(teacher);
         tx.commit();
         
         // check teacher is created and then remove it
         tx.begin();
         assertNotNull("ID should be populated after commit", teacher.getId());
         LOG.log(Level.INFO, "TestRemoveTeacher: Before removal {0}", teacher.toString());
         em.remove(teacher);
         tx.commit();
        
         // assert teacher not present
         teacher= em.find(Teacher.class, teacher.getId());
         LOG.info("TestRemoveTeacher: After removal teacher is: "+ teacher);
         assertNull(teacher);
    }
    
    // test an update to the seed data
    // first, find the seed data and assert it is what you expect (from @Before fixture)
    // then make an update and commit
    // remember, an update can be em.merge or you can call set methods on a managed entity, inside the transaction
    // read it back from the database
    // assert the update is what you expect
    @Test
    public void testUpdateTeacher(){
        Teacher teacher = em.createNamedQuery("Teacher.findByName", Teacher.class)
                .setParameter("name", "teachera")
                .getSingleResult();
        
        assertTrue("Teacher with name teachera and age 27 present", 27==teacher.getAge());
        LOG.log(Level.INFO, "TestUpdateTeacher: Before updating {0}", teacher.toString());

        tx.begin();
        teacher.setAge(30);
        tx.commit();
        
        Teacher updatedTeacher = em.createNamedQuery("Teacher.findByName", Teacher.class)
                .setParameter("name", "teachera")
                .getSingleResult();
        LOG.log(Level.INFO, "TestUpdateTeacher: After updating {0}", teacher.toString());
        assertTrue("Age of teachera has successfully updated", 30==updatedTeacher.getAge());
        
    }
    
    
    @Test
    public void testCreateNewValidTeacher() {
        
        Teacher teacher = new Teacher("teacherb",32);
              
        tx.begin();
        LOG.log(Level.INFO, "TestCreateNewValidTeacher: Before creation {0}", teacher.toString());

        assertNull("ID should be null before em.persist and commit.", teacher.getId());
        em.persist(teacher);
        assertNull("How about now?  ID should be null after em.persist and before commit.", teacher.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", teacher.getId());
        LOG.log(Level.INFO, "TestCreateNewValidTeacher: After creation {0}", teacher.toString());
        assertTrue("ID should be greater than 0", teacher.getId() > 0l);
    }

    @Test(expected = RollbackException.class)
    public void testCreateInvalidTeacherDatabaseFail() {
       
        Teacher teacher = new Teacher(null,30);
        tx.begin();
        LOG.log(Level.INFO, "TestCreateInvalidTeacherDatabaseFail: Before creating teacher with null valued name {0}", teacher.toString());

        assertNull("ID should be null before em.persist and commit.", teacher.getId());
        em.persist(teacher);
        assertNull("How about now?  ID should be null after em.persist and before commit.", teacher.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", teacher.getId());
        LOG.info(teacher.toString());
        assertTrue("ID should be greater than 0", teacher.getId() > 0l);
        
    }    
}
