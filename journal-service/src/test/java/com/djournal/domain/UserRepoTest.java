package com.djournal.domain;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JPA unit test for the UserRepo.
 */
public class UserRepoTest {
	 	private UserRepo userRepo;
	    private EntityManagerFactory entityManagerFactory;
	    private EntityManager entityManager;
	    
	    
	    @Before
	    public void setUp() throws Exception {
	        entityManagerFactory = Persistence.createEntityManagerFactory("users-test");
	        entityManager = entityManagerFactory.createEntityManager();
	        userRepo = new UserRepo();
	    }

	    @After
	    public void tearDown() throws Exception {
	        entityManager.close();
	        entityManagerFactory.close();
	    }
	    
	    

	    @Test
	    public void findAll() {
	        Collection<User> users = userRepo.findAll();
	        assertThat(users, hasSize(4));
	    }
	    
	    @Test
	    public void findByName() {
	        User jackNilsoon = userRepo.findByID("31d8dbdc-82c0-4c19-ab9b-b830b87472c4");
	        assertThat(jackNilsoon, is(notNullValue()));
	        assertThat(jackNilsoon.getName(), equalTo("Jack Nilsoon"));
	    }
	    
	    @Test
	    public void createUser() {
	    	
	        User user = new User();
	        user.setName("Jimi Dankerk");
	        userRepo.create(user);

	        User created = userRepo.findByID(user.getId());
	        assertThat(user, equalTo(created));
	    }
}
