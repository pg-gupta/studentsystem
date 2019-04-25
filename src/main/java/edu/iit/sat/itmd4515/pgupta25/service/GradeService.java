/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Grade;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author pooja gupta
 */
@Stateless
public class GradeService extends AbstractService<Grade>{

    public GradeService() {
        super(Grade.class);
    }
    
    @Override
    public List<Grade> findAll() {
        return em.createNamedQuery("Grade.findAll",entityClass).getResultList();
    }
    
    public Grade findGrade(int studentId, int courseId) {
        return em.createNamedQuery("Grade.findByStudentIdAndCourse",entityClass)
                .setParameter("studentId",studentId )
                .setParameter("courseId", courseId).getSingleResult();
    }
    
}
