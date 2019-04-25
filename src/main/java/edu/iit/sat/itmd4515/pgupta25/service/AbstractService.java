/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.iit.sat.itmd4515.pgupta25.service;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author pooja gupta
 */
public abstract class AbstractService<T> {

    private static final Logger LOG = Logger.getLogger(AbstractService.class.getName());

    @PersistenceContext(name = "itmd4515PU")
    protected EntityManager em;

    protected Class<T> entityClass;

    protected AbstractService(Class entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        em.remove(em.merge(entity));
    }

    /**
     * Find an entity
     *
     * @param id The PK of an entity to find
     * @return returns found entity or null
     */
    public T find(Object id) {
        LOG.info("find "+ id);
        return em.find(entityClass, id);
    }

    public abstract List<T> findAll();

    @PreDestroy
    public void destruct() {
        em.close();
    }
}
