package com.victor.library2.model;

import javax.persistence.*;

@Entity
@Table(name = "Pret")
public class Pret {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

/*    @OneToOne
    @JoinColumn(name = "livre_id", referencedColumnName = "id")
    private Livre livre;

    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

    private Boolean isborrowed;

    private Boolean isextended;

    private Boolean isreturned;

    private Integer nombreProlonge;

    public Pret() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

/*    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }*/

    public Boolean getIsborrowed() {
        return isborrowed;
    }

    public void setIsborrowed(Boolean isborrowed) {
        this.isborrowed = isborrowed;
    }

    public Boolean getIsextended() {
        return isextended;
    }

    public void setIsextended(Boolean isextended) {
        this.isextended = isextended;
    }

    public Boolean getIsreturned() {
        return isreturned;
    }

    public void setIsreturned(Boolean isreturned) {
        this.isreturned = isreturned;
    }

    public Integer getNombreProlonge() {
        return nombreProlonge;
    }

    public void setNombreProlonge(Integer nombreProlonge) {
        this.nombreProlonge = nombreProlonge;
    }

    @Override
    public String toString() {
        return "Pret{" +
                "id=" + id +
                ", isborrowed=" + isborrowed +
                ", isextended=" + isextended +
                ", isreturned=" + isreturned +
                ", nombreProlonge=" + nombreProlonge +
                '}';
    }
}
