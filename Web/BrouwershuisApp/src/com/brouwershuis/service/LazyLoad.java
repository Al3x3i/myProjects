package com.brouwershuis.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class LazyLoad {

	public static EntityManager getEnityManager(){
		
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("BrouwershuisApp");
		EntityManager entityManager = emfactory.createEntityManager();
		return entityManager;
	}
}
