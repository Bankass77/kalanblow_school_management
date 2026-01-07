package com.kalanblow.school_management.infrastructure.persistence.anneescolaire;

import com.kalanblow.school_management.model.anneescolaire.AnneeScolaire;
import org.springframework.data.jpa.domain.Specification;

public class AnneeScolaireSpecifications {

    public  static Specification<AnneeScolaire> anneeScolaireDebutContains(Integer debut){
        return ((root, query, criteriaBuilder) -> debut==null? null: criteriaBuilder.equal(root.get("anneSclaire").get("anneDebut"), debut));
    }

    public  static Specification<AnneeScolaire> anneeScolaireFinContains(Integer fin){

        return ((root, query, criteriaBuilder) -> fin==null? null: criteriaBuilder.equal(root.get("anneSclaire").get("anneFin"), fin));
    }

    public  static Specification<AnneeScolaire> eanneeScolaireIdContains(Long id){
        return ((root, query, criteriaBuilder) -> id==null? null: criteriaBuilder.equal(root.get("anneSclaire").get("anneeScolaireId"), id));
    }
}
