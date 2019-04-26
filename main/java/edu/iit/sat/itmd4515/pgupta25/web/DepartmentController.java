/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.web;

import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import edu.iit.sat.itmd4515.pgupta25.service.DepartmentService;
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
public class DepartmentController implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(DepartmentController.class.getName());
    @EJB
    private DepartmentService departmentSvc;
    
    private Department department;
    
    public DepartmentController() {
    }
    
    @PostConstruct
    public void postConstruct() {
        LOG.info("In department controller");
        department = new Department();
        getDepartments();
    }

    // action methods
    public String executeSaveDepartment() {
        LOG.log(Level.INFO, "Inside DepartmentController.executeSaveDepartment(){0}", department.toString());
        departmentSvc.create(department);
        department = new Department();
        return "/department.xhtml";
    }
    
    public void editDepartment(String id) {
        LOG.log(Level.INFO, "Inside DepartmentController.editDepartment(){0}", id);
        this.department = departmentSvc.find(Integer.parseInt(id));
    }

    public String updateDepartment() {
        LOG.log(Level.INFO, "Inside DepartmentController.updateDepartment()");
        departmentSvc.update(department);
        this.department= new Department();
        return "/department.xhtml";
    }
    
    public void removeDepartment(Department s) {
        LOG.info("Inside DepartmentController.removeDepartment()");
        departmentSvc.remove(s);
    }
    
    public List<Department> getDepartments() {
        LOG.log(Level.INFO, "Inside DepartmentController.getDepartments(){0}", department.toString());
        return departmentSvc.findAll();
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
}
