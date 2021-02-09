package com.djournal.domain;

import java.sql.Timestamp;
import java.util.UUID;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 * The main journal entity.
 */

@Entity
@Table(name = "TblJournal")
@NamedQuery(name = Journal.FIND_ALL_JOURNAL, query = "SELECT b FROM Journal b")
@JsonbPropertyOrder({"id", "foodName", "amount", "eatTime","user"})
public class Journal {

    static final String FIND_ALL_JOURNAL = "Journal.findAll";
    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "eat_time", nullable = false)
    private Timestamp eatTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonbTransient
    private User user;
    
    @Override
    public String toString() {
        return "Journal{id='" + id + '\'' + ", foodName='" + foodName + '\'' + ", amount="+ '\'' + amount 
        		+ '\''+ ", eatTime='" + eatTime + '\'' +'}';
    }

	

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Timestamp getEatTime() {
		return eatTime;
	}

	public void setEatTime(Timestamp eatTime) {
		this.eatTime = eatTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Journal( String foodName, Integer amount, Timestamp eatTime, User user) {
		
		
		this.foodName = foodName;
		this.amount = amount;
		this.eatTime = eatTime;
		this.user = user;
	}
	
	public Journal(String id) {
		
		this.id = id;
		
	}
	
	public Journal() {
		  this(UUID.randomUUID().toString());
	}
    
    
    
}
