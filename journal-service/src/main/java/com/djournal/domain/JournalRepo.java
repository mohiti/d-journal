package com.djournal.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The JournalRepo implementation is used to find and managed journal recoeds.
 */
@ApplicationScoped
@Transactional(Transactional.TxType.REQUIRED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalRepo {

    @Inject
    private EntityManager entityManager;


    @Inject
    private Logger logger;

    
    /**
     * Create journal for user.
     *
     * @param userId the user ID
     * @param journal the journal info
     */
    public void createJournal(String userId, Journal journal) {
        logger.log(Level.INFO, "Add food to journal for user {0} on {1}.", new Object[]{userId, journal});
        User user = entityManager.getReference(User.class, userId);
        user.addJournal(journal);
    }
    
    /**
     * Get the journal identified by its ID.
     *
     * @param id the Journal ID
     * @return the journal
     */
    public Journal journalInfo(String id) {
        logger.log(Level.INFO, "Getting journal with ID {0}.", id);
        return entityManager.getReference(Journal.class, id);
    }
    
    
    /**
     * Remove a journal from user journal with given user Id on given journal.
     *
     * @param userId   the user Id
     * @param journalId the journal ID
     */
    public void removeJournal(String userId, String journalId) {
        logger.log(Level.INFO, "Rmoving journal with user Id {0} on journal ID {1}.", new Object[]{userId, journalId});
        User user = entityManager.getReference(User.class, userId);
        user.removeJournal(new Journal(journalId));
    }

}
