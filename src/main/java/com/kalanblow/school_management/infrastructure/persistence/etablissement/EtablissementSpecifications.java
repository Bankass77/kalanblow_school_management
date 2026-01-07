package com.kalanblow.school_management.infrastructure.persistence.etablissement;

import com.kalanblow.school_management.model.etablissement.Etablissement;
import com.kalanblow.school_management.model.shared.Address;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

public class EtablissementSpecifications {

    //nomEtablissement, identiantEtablissement, etablissementEmail, phoneNumber,address
    public static Specification<Etablissement> nometablissementContains(String nomEtablissement) {

        return (root, query, criteriaBuilder) ->

                nomEtablissement == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("nomEtablissement")), "%" + nomEtablissement.toLowerCase() + "%");
    }


    public static Specification<Etablissement> etablissementEmailContains(String email) {
        return ((root, query, criteriaBuilder) ->

                email == null ? null : criteriaBuilder.like(root.get("etablissementEmail"), "%" + email.toLowerCase() + "%"));
    }

    public static Specification<Etablissement> phoneNumberContains(String phoneNumber) {

        return ((root, query, criteriaBuilder) ->
                phoneNumber == null ? null : criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber.toLowerCase() + "%"));
    }

    public static Specification<Etablissement> identifiantEtablissementEquals(Long id) {

        return ((root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get("etablissement").get("etablisementScolaireId"), id));

    }

    public static Specification<Etablissement> addresseContains(String address) {

        return ((root, query, criteriaBuilder) -> {

            if (address == null || address.isBlank()) {

                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + address.toLowerCase() + "%";
            Path<Address> addressPath = root.get("address");

            return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(addressPath.get("street")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(addressPath.get("city")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(addressPath.get("country")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(addressPath.get("codePostale").as(String.class)), pattern));
        }
        );
    }
}
