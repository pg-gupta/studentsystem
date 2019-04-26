/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.domain.Course;
import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import edu.iit.sat.itmd4515.pgupta25.service.DepartmentService;
import edu.iit.sat.itmd4515.pgupta25.service.CourseService;
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
public class CourseController implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(CourseController.class.getName());
    @EJB
    private CourseService courseSvc;
    @EJB
    private DepartmentService departmentSvc;
    @EJB
    private TeacherService teacherSvc;
    
    List<Department> departments;
    List<Teacher> teachers;
    
    private Course course;
    
    public CourseController() {
    }
    
    @PostConstruct
    public void postConstruct() {
        LOG.info("In course controller");
        course = new Course();
        getCourses();
        departments=getDepartments();
        teachers = getTeachers();
    }

    // action methods
    public String executeSaveCourse() {
        LOG.log(Level.INFO, "Inside CourseController.executeSaveCourse(){0}", course.toString());
        courseSvc.create(course);
        course = new Course();
        return "/course.xhtml";
    }
    
    public void editCourse(String id) {
        LOG.log(Level.INFO, "Inside CourseController.editCourse(){0}", id);
        this.course = courseSvc.find(Integer.parseInt(id));
    }

    public String updateCourse() {
        LOG.log(Level.INFO, "Inside CourseController.updateCourse()");
        courseSvc.update(course);
        this.course= new Course();
        return "/course.xhtml";
    }
    
    public void removeCourse(Course s) {
        LOG.info("Inside CourseController.removeCourse()");
        courseSvc.remove(s);
    }
    
    public List<Course> getCourses() {
        LOG.log(Level.INFO, "Inside CourseController.getCourses(){0}", course.toString());
        return courseSvc.findAll();
    }
    
    public List<Department> getDepartments() {
        LOG.info("Inside CourseController.getDepartments()");
        return departmentSvc.findAll();
    }
    
    public List<Teacher> getTeachers() {
        LOG.info("Inside CourseController.getTeachers()");
        return teacherSvc.findAll();
    }
    
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
}
