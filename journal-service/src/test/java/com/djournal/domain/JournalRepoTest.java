package com.djournal.domain;

import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JournalRepoTest {
  private JournalRepo journalRepo;
  private EntityManagerFactory entityManagerFactory;
  private EntityManager entityManager;
  
  private UserRepo userRepo;

  @Before
  public void setUp() throws Exception {
      entityManagerFactory = Persistence.createEntityManagerFactory("users-test");
      entityManager = entityManagerFactory.createEntityManager();
      journalRepo = new JournalRepo();
      userRepo = new UserRepo();
  }

  @After
  public void tearDown() throws Exception {
      entityManager.close();
      entityManagerFactory.close();
  }

 
  @Test
  public void createJournal() {
	  String userId = "31d8dbdc-82c0-4c19-ab9b-b830b87472c4";
	  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	  User user = userRepo.findByID("31d8dbdc-82c0-4c19-ab9b-b830b87472c4");
      Journal journal = new Journal("milk", new Integer("150"), timestamp, user);
    		  journalRepo.createJournal("31d8dbdc-82c0-4c19-ab9b-b830b87472c4", journal);

      boolean isCreated = userRepo.findByID("31d8dbdc-82c0-4c19-ab9b-b830b87472c4").getJournals().contains(journal);
      
      assertTrue(isCreated);
  }
}

