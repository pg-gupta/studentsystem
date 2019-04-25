/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Student;
import java.util.List;
import javax.ejb.Stateless;
import java.util.logging.Logger;

/**
 *
 * @author pooja gupta
 */
@Stateless
public class StudentService extends AbstractService<Student>{

    private static final Logger LOG = Logger.getLogger(StudentService.class.getName());
    public StudentService() {
        super(Student.class);
    }

    @Override
    public List<Student> findAll() {
        return em.createNamedQuery("Student.findAll",entityClass).getResultList();
    }
    
}
