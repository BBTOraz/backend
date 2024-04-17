package com.bbt.lawyerclientservice.specification;

import com.bbt.lawyerclientservice.entity.Lawyer;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class LawyerSpecification {
    public static Specification<Lawyer> withName(String lastname, String firstname, String middlename) {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            if (lastname != null && !lastname.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("lastname"), "%" + lastname + "%"));
            }
            if (firstname != null && !firstname.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("firstname"), "%" + firstname + "%"));
            }
            if (middlename != null && !middlename.isEmpty()) {
                predicate = builder.and(predicate, builder.like(root.get("middlename"), "%" + middlename + "%"));
            }
            return predicate;
        };
    }

    public static Specification<Lawyer> fullNameContainsIgnoreCase(String searchTerm) {
        return (Root<Lawyer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            String[] searchTerms = searchTerm.toLowerCase().split("\\s+");
            Predicate[] predicates = new Predicate[searchTerms.length];
            for (int i = 0; i < searchTerms.length; i++) {
                String searchExpression = "%" + searchTerms[i] + "%";
                Predicate fullNamePredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstname")), searchExpression),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), searchExpression)
                );
                predicates[i] = fullNamePredicate;
            }
            return criteriaBuilder.and(predicates);
        };
    }


    public static Specification<Lawyer> withAgeBetween(Integer minAge, Integer maxAge) {
        return (root, query, builder) -> {
            if (minAge != null && maxAge != null) {
                return builder.between(root.get("age"), minAge, maxAge);
            }
            return builder.conjunction();
        };
    }

    public static Specification<Lawyer> withExperienceBetween(Integer minExperience, Integer maxExperience) {
        return (root, query, builder) -> {
            if (minExperience != null && maxExperience != null) {
                return builder.between(root.get("experience"), minExperience, maxExperience);
            }
            return builder.conjunction();
        };
    }
}
