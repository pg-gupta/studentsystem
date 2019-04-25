/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.domain;

import edu.iit.sat.itmd4515.pgupta25.service.StudentService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.persistence.RollbackException;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author pooja gupta
 */
public class StudentTest extends AbstractJPATest {
   @EJB
    private StudentService studentSvc;
   
      private static final Logger LOG = Logger.getLogger(StudentTest.class.getName());
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    // testing my read operation.  I hope your javadoc comments are better than mine.
    @Test
    public void testFindExistingStudent() {
        // if knew the PK - easy as em.find
        // this lets us use named parameters and queries
        Student student = em.createNamedQuery("Student.findByName", Student.class)
                .setParameter("name", "studenta")
                .getSingleResult();
        LOG.log(Level.INFO, "TestFindExistingStudent: {0}", student);
        assertTrue("Name should match", "studenta".equals(student.getName()));
    }

    // test a removal by creating a new student, and then removing
    // assert that you can no longer find it from the database
    @Test
    public void testRemoveStudent(){
         Student student = new Student("tempstudent","email@email.com",new Date(),"program","major","aid");
         // create student
         tx.begin();
         em.persist(student);
         tx.commit();
         
         // check student is created and then remove it
         tx.begin();
         assertNotNull("ID should be populated after commit", student.getId());
         LOG.log(Level.INFO, "TestRemoveStudent: Before removal {0}", student.toString());
         em.remove(student);
         tx.commit();
        
         // assert student not present
         student= em.find(Student.class, student.getId());
         LOG.info("TestRemoveStudent: After removal student is: "+ student);
         assertNull(student);
    }
    
    // test an update to the seed data
    // first, find the seed data and assert it is what you expect (from @Before fixture)
    // then make an update and commit
    // remember, an update can be em.merge or you can call set methods on a managed entity, inside the transaction
    // read it back from the database
    // assert the update is what you expect
    @Test
    public void testUpdateStudent(){
        Student student = em.createNamedQuery("Student.findByName", Student.class)
                .setParameter("name", "studenta")
                .getSingleResult();
        
        assertTrue("Student with name studenta and aid 'aid' present", "aid".equals(student.getAid()));
        LOG.log(Level.INFO, "TestUpdateStudent: Before updating {0}", student.toString());

        tx.begin();
        student.setAid("aid100");
        tx.commit();
        
        Student updatedStudent = em.createNamedQuery("Student.findByName", Student.class)
                .setParameter("name", "studenta")
                .getSingleResult();
        LOG.log(Level.INFO, "TestUpdateStudent: After updating {0}", student.toString());
        assertTrue("AId for studenta has successfully updated", "aid100".equals(updatedStudent.getAid()));
        
    }
    
    
    @Test
    public void testCreateNewValidStudent() {
        
        Student student = new Student("studentb","email@email.com",new Date(),"program","major","aid");
              
        tx.begin();
        LOG.log(Level.INFO, "TestCreateNewValidStudent: Before creation {0}", student.toString());

        assertNull("ID should be null before em.persist and commit.", student.getId());
        em.persist(student);
        assertNull("How about now?  ID should be null after em.persist and before commit.", student.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", student.getId());
        LOG.log(Level.INFO, "TestCreateNewValidStudent: After creation {0}", student.toString());
        assertTrue("ID should be greater than 0", student.getId() > 0l);
        
        //clean up
        tx.begin();
        em.remove(student);
        tx.commit();
    }

    @Test(expected = RollbackException.class)
    public void testCreateInvalidStudentDatabaseFail() {
       
        Student student = new Student(null,"email@email.com", new Date(),"program","major","aid");
        tx.begin();
        LOG.log(Level.INFO, "TestCreateInvalidStudentDatabaseFail: Before creating student with null valued name {0}", student.toString());

        assertNull("ID should be null before em.persist and commit.", student.getId());
        em.persist(student);
        assertNull("How about now?  ID should be null after em.persist and before commit.", student.getId());
        tx.commit();
        assertNotNull("ID should be populated after commit", student.getId());
        LOG.info(student.toString());
        assertTrue("ID should be greater than 0", student.getId() > 0l);
        
    }
    
    @Test
    public void testStudentCourseManyToManyRelation() {
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
    
//    @Test
//    public void testStudents() {
//        
//           List<Grade>  students=null;
//       students = em.createNamedQuery("Grade.findAll", Grade.class).getResultList();
//                 
//        assertEquals(students.size(), 3);
//    }
}
