/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import edu.iit.sat.itmd4515.pgupta25.service.DepartmentService;
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
public class TeacherController implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(TeacherController.class.getName());
    @EJB
    private TeacherService teacherSvc;
    @EJB
    private DepartmentService departmentSvc;
    
    List<Department> departments;
    
    private Teacher teacher;
    
    public TeacherController() {
    }
    
    @PostConstruct
    public void postConstruct() {
        LOG.info("In teacher controller");
        teacher = new Teacher();
        getTeachers();
        departments=getDepartments();
    }

    // action methods
    public String executeSaveTeacher() {
        LOG.log(Level.INFO, "Inside TeacherController.executeSaveTeacher(){0}", teacher.toString());
        teacherSvc.create(teacher);
        teacher = new Teacher();
        return "/teacher.xhtml";
    }
    
    public void editTeacher(String id) {
        LOG.log(Level.INFO, "Inside TeacherController.editTeacher(){0}", id);
        this.teacher = teacherSvc.find(Integer.parseInt(id));
    }

    public String updateTeacher() {
        LOG.log(Level.INFO, "Inside TeacherController.updateTeacher()");
        teacherSvc.update(teacher);
        this.teacher= new Teacher();
        return "/teacher.xhtml";
    }
    
    public void removeTeacher(Teacher s) {
        LOG.info("Inside TeacherController.removeTeacher()");
        teacherSvc.remove(s);
    }
    
    public List<Teacher> getTeachers() {
        LOG.log(Level.INFO, "Inside TeacherController.getTeachers(){0}", teacher.toString());
        return teacherSvc.findAll();
    }
    
    public List<Department> getDepartments() {
        LOG.info("Inside TeacherController.getDepartments()");
        return departmentSvc.findAll();
    }
    
    public Teacher getTeacher() {
        return teacher;
    }
    
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
}
