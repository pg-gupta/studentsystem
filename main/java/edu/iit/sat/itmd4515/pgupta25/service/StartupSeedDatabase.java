/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Course;
import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.domain.Student;
import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import edu.iit.sat.itmd4515.pgupta25.domain.security.Group;
import edu.iit.sat.itmd4515.pgupta25.domain.security.User;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author miteshpatekar
 */
@Startup
@Singleton
public class StartupSeedDatabase {

    private static final Logger LOG = Logger.getLogger(StartupSeedDatabase.class.getName());

//    @PersistenceContext(name = "itmd4515PU")
//    protected EntityManager em;
    @EJB
    private StudentService studentSvc;

    @EJB
    private TeacherService teacherSvc;

    @EJB
    private CourseService courseSvc;

    @EJB
    private DepartmentService deptSvc;

    @EJB
    private UserService userSvc;
    @EJB
    private GroupService groupSvc;

    // don't use - the container manages EJB instances
    public StartupSeedDatabase() {
    }

    @PostConstruct
    private void seedDatabase() {
        // create admin user and group
        User adminUser = new User("admin", "admin", true);
        Group adminGroup = new Group("ADMIN_GROUP", "This is the identity store group for administrators.  It is managed by security and operations folks who care deeply about security.");
        adminUser.addGroup(adminGroup);
        groupSvc.create(adminGroup);
        userSvc.create(adminUser);
//        
//        // create business users and groups
        Group studentGroup = new Group("STUDENT_GROUP", "Identity Store group of students");
        Group teacherGroup = new Group("TEACHER_GROUP", "Identity Store group of teachers");
        User teacher1 = new User("teacher1", "teacher1", true);
        teacher1.addGroup(teacherGroup);
        User teacher2 = new User("teacher2", "teacher2", true);
        teacher2.addGroup(teacherGroup);
//        teacher2.addGroup(studentGroup);
        User student1 = new User("student1", "student1", true);
        student1.addGroup(studentGroup);
        User student2 = new User("student2", "student2", true);
        student2.addGroup(studentGroup);
        groupSvc.create(teacherGroup);
        groupSvc.create(studentGroup);
        userSvc.create(teacher1);
        userSvc.create(teacher2);
        userSvc.create(student1);
        userSvc.create(student2);

        Student student_one = new Student("StudentOne", "student_one@email.com", new Date(), "program_one", "major_one", "aid_1");
        student_one.setUser(student1);
        Student student_two = new Student("StudentTwo", "student_two@email.com", new Date(), "program_two", "major_two", "aid_2");
        student_two.setUser(student2);
        Teacher teacher_one = new Teacher("Teacher_one", 29);
        teacher_one.setUser(teacher1);
        Teacher teacher_two = new Teacher("Teacher_two", 29);
        teacher_two.setUser(teacher2);

        Course course_one = new Course("course_one", 3);
        Course course_two = new Course("course_two", 3);
        Department dept_one = new Department("dept_one", "State Street");
        Department dept_two = new Department("dept_two", "Wacker Street");

        dept_one.addCourse(course_one);
        dept_one.addTeacher(teacher_one);
        dept_two.addCourse(course_two);
        dept_two.addTeacher(teacher_two);

        teacher_one.addCourse(course_one);
        teacher_two.addCourse(course_two);

        course_one.addStudent(student_one);
        course_two.addStudent(student_two);

        studentSvc.create(student_one);
        studentSvc.create(student_two);

        courseSvc.create(course_one);
        courseSvc.create(course_two);

        teacherSvc.create(teacher_one);
        teacherSvc.create(teacher_two);

        deptSvc.create(dept_one);
        deptSvc.create(dept_two);

//        courseSvc.findAll().stream().map((Course c) -> {
//            LOG.info(c.toString());
//            return c;
//        }).forEachOrdered((c) -> {
//            c.getStudentCollection().forEach((Student s) -> {
//                LOG.log(Level.INFO, "\t{0}", s.toString());
//            });
//        });
    }

}
