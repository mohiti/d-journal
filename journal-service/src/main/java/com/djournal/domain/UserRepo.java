package com.djournal.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


/**
 * The UserRepo implementation is used to find and managed users.
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRepo {
	 	
		@Inject
	    private EntityManager entityManager;
	    @Inject
	    private Logger logger;

	    /**
	     * Returns the list of all users.
	     *
	     * @return a collection of users
	     */
	    public Collection<User> findAll() {
	        logger.log(Level.INFO, "Find all users.");
	        TypedQuery<User> findAll = entityManager.createNamedQuery(User.FIND_ALL_USER, User.class);
	        return Collections.unmodifiableCollection(findAll.getResultList());
	    }
	    
	    
	    /**
	     * Find the user by its ID and return a reference to it.
	     *
	     * @param id the ID
	     * @return the user
	     */
	    public User findByID(String id) {
	        logger.log(Level.INFO, "Find user with ID {0}.", id);
	        return entityManager.getReference(User.class, Objects.requireNonNull(id));
	    }
	    
	    
	    /**
	     * Check if user under given Name already exists.
	     *
	     * @param name the Name
	     * @return true of exists, otherwise false
	     */
	    public boolean exists(String name) {
	        logger.log(Level.INFO, "Find user with Name {0}.", name);
	        
	        List<User> results = entityManager.createQuery("SELECT b FROM User b where b.name = :name1")
                    .setParameter("name1", name).getResultList();
	        
	        return !results.isEmpty();
	    }
	    
	    
	    /**
	     * Creates a new user.
	     *
	     * @param user a user to create
	     */
	    public void create(User user) {
	        Objects.requireNonNull(user);
	        logger.log(Level.INFO, "Creating {0}.", user);
	        entityManager.persist(user);
	    }
	    
	    
	    /**
	     * Updates a exist user .
	     *
	     * @param user a user to update
	     */
	    public void update(String id, User user) {
	        Objects.requireNonNull(user);
	        logger.log(Level.INFO, "Updating {0} using ID {1}.", new Object[]{user, id});
	        entityManager.merge(user);
	    }
	    
	    
	    /**
	     * Deletes a user via ID.
	     *
	     * @param id the ID
	     */
	    public void delete(String id) {
	        Objects.requireNonNull(id);
	        logger.log(Level.INFO, "Deleting user with ID {0}.", id);
	        User reference = entityManager.getReference(User.class, id);
	        entityManager.remove(reference);
	    }

	    
	    
}
