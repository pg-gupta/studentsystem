/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import edu.iit.sat.itmd4515.pgupta25.domain.Department;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author miteshpatekar
 */
@Stateless
public class DepartmentService extends AbstractService<Department> {

    public DepartmentService() {
        super(Department.class);
    }

    @Override
    public List<Department> findAll() {
        return em.createNamedQuery("Department.findAll", entityClass).getResultList();
    }

}
