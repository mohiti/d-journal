package com.djournal.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TblUser")
@NamedQuery(name = User.FIND_ALL_USER, query = "SELECT b FROM User b")
@JsonbPropertyOrder({"id", "name"})
public class User {
    static final String FIND_ALL_USER = "User.findAll";

    @Id
    @Column(name = "id", unique = true)
    private String id;


    @Column(name = "name", nullable = false)
    private String name;

    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journal> journals = new ArrayList<>();
    
    
    public Collection<Journal> getJournals() {
        return journals;
    }

    public void setJournals(Collection<Journal> journals) {
        this.journals.clear();
        this.journals.addAll(journals);
    }

    public void addJournal(Journal journal) {
    	journal.setUser(this);
    	journals.add(journal);
    }

    public void removeJournal(Journal journal) {
        int index = journals.indexOf(journal);
        if (index > -1) {
            Journal j = journals.remove(index);
            j.setUser(null);
        }
    }
    
    @Override
    public String toString() {
        return "User{id=" + id  + ", name='"+  name + "'}";
    }


	


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User(String name) {
		this.name = name;
	}
	
	public User() {
	this(UUID.randomUUID().toString());
		
	}

 
}
