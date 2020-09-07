package com.victor.library2.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Livres")
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titre;

    private String auteur;

    private String collection;

    private String editeur ;

    private String categorie;

    private String description;

    private String isbn;

/*    @ManyToOne
    @JoinColumn(name="stock_id", referencedColumnName = "id")
    private Stock stock;

    @OneToOne
    @JoinColumn(name = "pret_id", referencedColumnName = "id")
    private Pret pret;*/

    @CreatedDate
    @Column(name = "createdAt")
    private Date createdAt;


    @LastModifiedDate
    @Column(name = "updatedAt")
    private Date updatedAt;

    private Date dateParution;

    public Livre(Long id, String auteur, String collection, String editeur, String categorie, String description, Date createdAt, Date updatedAt,
                 Date dateParution, String titre) {
        this.id = id;
        this.auteur = auteur;
        this.collection = collection;
        this.editeur = editeur;
        this.categorie = categorie;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.dateParution = dateParution;
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDateParution() {
        return dateParution;
    }

    public void setDateParution(Date dateParution) {
        this.dateParution = dateParution;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

/*    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }*/

    @Override
    public String toString() {
        return "Livre{" +
                "id=" + id +
                ", auteur='" + auteur + '\'' +
                ", collection='" + collection + '\'' +
                ", editeur='" + editeur + '\'' +
                ", categorie='" + categorie + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", dateParution='" + dateParution + '\'' +
                '}';
    }
}
