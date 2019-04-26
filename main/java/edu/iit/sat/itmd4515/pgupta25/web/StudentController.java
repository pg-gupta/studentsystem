/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Student;
import edu.iit.sat.itmd4515.pgupta25.service.StudentService;
import java.io.Serializable;
import java.util.List;
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
public class StudentController implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(StudentController.class.getName());
    @EJB
    private StudentService studentSvc;
    
    private Student student;
    
    public StudentController() {
    }
    
    @PostConstruct
    public void postConstruct() {
        LOG.info("In student controller");
        student = new Student();
        getStudents();
    }

    // action methods
    public String executeSaveStudent() {
        LOG.info("Inside StudentController.executeSaveStudent()" + student.toString());
        studentSvc.create(student);
        student = new Student();
        return "/student.xhtml";
    }
    
    public void editStudent(String id) {
        LOG.info("Inside StudentController.editStudent()" + id);
        this.student = studentSvc.find(Integer.parseInt(id));

    }

    public String updateStudent() {
        LOG.info("Inside StudentController.updateStudent()" + student.toString());
        studentSvc.update(student);
        this.student = new Student();
        return "/student.xhtml";
    }
    
    public void removeStudent(Student s) {
        LOG.info("Inside StudentController.removeStudent()");
        studentSvc.remove(s);
    }
    
    public List<Student> getStudents() {
        LOG.info("Inside StudentController.getStudents()" + student.toString());
        return studentSvc.findAll();
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
}
