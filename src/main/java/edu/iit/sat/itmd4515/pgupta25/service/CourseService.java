/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Course;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author pooja gupta
 */
@Stateless
public class CourseService extends AbstractService<Course> {

    public CourseService() {
        super(Course.class);
    }

    @Override
    public List<Course> findAll() {
        return em.createNamedQuery("Course.findAll", entityClass).getResultList();
    }

}
