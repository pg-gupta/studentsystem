/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Course;
import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.domain.Grade;
import edu.iit.sat.itmd4515.pgupta25.domain.GradePK;
import edu.iit.sat.itmd4515.pgupta25.domain.Student;
import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import edu.iit.sat.itmd4515.pgupta25.service.CourseService;
import edu.iit.sat.itmd4515.pgupta25.service.DepartmentService;
import edu.iit.sat.itmd4515.pgupta25.service.GradeService;
import edu.iit.sat.itmd4515.pgupta25.service.StudentService;
import edu.iit.sat.itmd4515.pgupta25.service.TeacherService;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author pooja gupta
 */
@ViewScoped
@Named
public class GradeController implements Serializable {

    private static final Logger LOG = Logger.getLogger(GradeController.class.getName());
    @EJB
    private GradeService gradeSvc;
    @EJB
    private StudentService studentSvc;
    @EJB
    private CourseService courseSvc;

    private Grade grade;
    List<Course> courses;
    List<Student> students;
    List<Grade> grades;

    public GradeController() {
    }

    @PostConstruct
    public void postConstruct() {
        LOG.info("In grade controller");
        GradePK gradePK = new GradePK();
        grade = new Grade(gradePK);

        getGrades();
        courses = getCourses();
        students = getStudents();
    }

    // action methods
    public String executeSaveGrade() {
        LOG.log(Level.INFO, "Inside GradeController.executeSaveGrade(){0}", grade.toString());
        gradeSvc.create(grade);
        grade = new Grade();
        return "/grade.xhtml";
    }

    public void editGrade(String studentId, String courseId) {
        LOG.log(Level.INFO, "Inside GradeController.editGrade(){0}", studentId);
        this.grade = gradeSvc.findGrade(Integer.parseInt(studentId),Integer.parseInt(courseId));
    }

    public String updateGrade() {
        LOG.log(Level.INFO, "Inside GradeController.updateGrade()");
        gradeSvc.update(grade);
        
        this.grade = new Grade(new GradePK());
        return "/grade.xhtml";
    }

    public void removeGrade(Grade s) {
        LOG.info("Inside GradeController.removeGrade()");
        gradeSvc.remove(s);
    }

    public List<Grade> getGrades() {
        LOG.log(Level.INFO, "Inside GradeController.getGrades(){0}", grade.toString());
        grades = gradeSvc.findAll();
        grades.forEach((g) -> {
            Student student=studentSvc.find(g.getGradePK().getStudentId());
            Course course=courseSvc.find(g.getGradePK().getCourseId());
            g.setCourse(course);
            g.setStudent(student);
        });
        return grades;
    }

    public List<Student> getStudents() {
        LOG.info("Inside GradeController.getDepartments()");
        return studentSvc.findAll();
    }

    public List<Course> getCourses() {
        LOG.info("Inside GradeController.getTeachers()");
        return courseSvc.findAll();
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

}
