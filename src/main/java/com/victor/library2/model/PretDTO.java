package com.victor.library2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PretDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

/*    @OneToOne
    @JoinColumn(name = "livre_id", referencedColumnName = "id")
    private Livre livre;

    @ManyToOne
    @JoinColumn(name="utilisateur_id", referencedColumnName = "id")
    private Utilisateur utilisateur;*/

    private Boolean emprunte;

    private Boolean prolonge;

    private Boolean retourne;

    private Integer nombreProlonge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
