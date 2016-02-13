package com.etherblood.cardsmasterserver.core;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.EntityPath;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Philipp
 */
public abstract class AbstractRepository<T> {
    @PersistenceContext
    private EntityManager em;

    public void persist(T entity) {
        em.persist(entity);
    }
    
    public void remove(T entity) {
        em.remove(entity);
    }
    
    protected JPAQuery from(EntityPath... paths) {
        return new JPAQuery(em).from(paths);
    }
}
