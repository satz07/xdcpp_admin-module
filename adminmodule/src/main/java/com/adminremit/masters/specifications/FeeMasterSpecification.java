package com.adminremit.masters.specifications;

import com.adminremit.masters.models.FeeMaster;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

public class FeeMasterSpecification implements Specification<FeeMaster> {

    @Override
    public Predicate toPredicate(Root<FeeMaster> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return  null;
    }
}
