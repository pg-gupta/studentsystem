/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Teacher;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author pooja gupta
 */
@Stateless
public class TeacherService extends AbstractService<Teacher> {

    public TeacherService() {
        super(Teacher.class);
    }

    @Override
    public List<Teacher> findAll() {
        return em.createNamedQuery("Teacher.findAll", entityClass).getResultList();
    }

}
