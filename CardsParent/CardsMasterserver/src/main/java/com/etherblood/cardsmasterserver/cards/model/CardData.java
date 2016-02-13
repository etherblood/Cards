//package com.etherblood.cardsmasterserver.cards.model;
//
//import com.etherblood.cardsmasterserver.core.MutableEntity;
//import com.etherblood.cardsmasterserver.users.model.UserAccount;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//import javax.validation.constraints.NotNull;
//
///**
// *
// * @author Philipp
// */
//@Entity
//public class CardData extends MutableEntity {
//    @Id
//    @GeneratedValue
//    private Long id;
//    @NotNull
//    @ManyToOne
//    private UserAccount owner;
//    @NotNull
//    private String cardname;
//    private int amount;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public UserAccount getOwner() {
//        return owner;
//    }
//
//    public void setOwner(UserAccount owner) {
//        this.owner = owner;
//    }
//
//    public int getAmount() {
//        return amount;
//    }
//
//    public void setAmount(int amount) {
//        this.amount = amount;
//    }
//    
//    public void incAmount(int value) {
//        amount += value;
//    }
//
//    public String getCardname() {
//        return cardname;
//    }
//
//    public void setCardname(String cardname) {
//        this.cardname = cardname;
//    }
//}
