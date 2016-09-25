package com.roms.library.dao;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("GenericDaoInterface")
public class GenericDaoImplementation<T> implements GenericDaoInterface<T> {

    protected EntityManager entityManager;

    private Class<T> type;

    public GenericDaoImplementation() {

    }

    public GenericDaoImplementation(Class<T> type) {
        this.type = type;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T save(T emp) {
        entityManager.persist(emp);
        entityManager.flush();
        return emp;
    }

    @Override
    public Boolean delete(T emp) {
        try {
            entityManager.remove(emp);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public T update(T emp) {
        try{
            return entityManager.merge(emp);
        } catch(Exception ex) {
            return null;
        }
    }

    @Override
    public T find(Long empId) {
        return entityManager.find(this.type, empId);
    }

    public List<T> findAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT d FROM " + this.type.getName() + " d", this.type);
        return query.getResultList();
    }

}