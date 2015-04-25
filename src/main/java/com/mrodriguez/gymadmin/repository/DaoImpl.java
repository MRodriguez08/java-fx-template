package com.mrodriguez.gymadmin.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;


public abstract class DaoImpl<K, E> implements Dao<K, E>{

    protected Class<E> entityClass;

    protected Session session;

    @SuppressWarnings("unchecked")
    public DaoImpl() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>)genericSuperclass.getActualTypeArguments()[1];
    }

    public void flush() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.flush();
    }

    public void persist(E entity) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(entity);
    }

    public void merge(E entity) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(entity);
    }

    public void remove(E entity) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.getTransaction().begin();
        session.merge(entity);
        session.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
	public E findById(K id) {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        E e =  (E) session.get(entityClass, (Serializable)id);
        return e;
    }

    public void rollBack(){
        session.getTransaction().rollback();
    }
    
    @SuppressWarnings("unchecked")
	public List<E> findAll(){
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        List<E> list =  (List<E>)session.createCriteria(entityClass).list();
        return list;
    }
	
	
}