package com.adminremit.common.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.adminremit.common.models.Constants;
import com.adminremit.common.models.FilterAndSort;
import com.adminremit.common.models.SearchCriteria;

@Service
public class PredicateService<T> {
	
	@Autowired
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Page<T> loadDataByPage(Pageable page, String query, String selectQuery, FilterAndSort filter, Map<String, String> params, boolean existsWhere){
		
		if(page == null) {
			return null;
		}
		
		query = addSpecificationToQuery(filter, query, existsWhere);
		Long totalRows = getCountQuery(query, params);
		
		if(page.getSort()!=null) {
			String order = StringUtils.collectionToCommaDelimitedString(StreamSupport.stream(page.getSort().spliterator(), false)
					       .map(o->o.getProperty() + " "+o.getDirection()).collect(Collectors.toList()));
			
			query = query + " ORDER BY "+ order;					
		}
		
		TypedQuery<T> typed = (TypedQuery<T>)entityManager.createQuery(selectQuery.concat(query));
		if(params!=null) {
			params.forEach(typed::setParameter);
		}
		
		typed.setFirstResult(page.getPageNumber() * page.getPageSize());
		typed.setMaxResults(page.getPageSize());		
		
		return new PageImpl<T>((List<T>)typed.getResultList(), page, totalRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadAllData(Pageable page, String query, String selectQuery, FilterAndSort filter, Map<String, String> params, boolean existsWhere){
		
		if(page == null) {
			return null;
		}
		
		query = addSpecificationToQuery(filter, query, existsWhere);
		
		if(page.getSort()!=null) {
			String order = StringUtils.collectionToCommaDelimitedString(StreamSupport.stream(page.getSort().spliterator(), false)
					       .map(o->o.getProperty() + " "+o.getDirection()).collect(Collectors.toList()));
			
			query = query + " ORDER BY "+ order;					
		}
		
		TypedQuery<T> typed = (TypedQuery<T>)entityManager.createQuery(selectQuery.concat(query));
		if(params!=null) {
			params.forEach(typed::setParameter);
		}
		
		return (List<T>)typed.getResultList();
	}
	
	public Long getTotalRecords(String query, Map<String, String> params){
		return getCountQuery(query, params);
	}
						   

	private Long getCountQuery(String query, Map<String, String> params) {
		String countQuery = "select count(*)" + query;
		TypedQuery<Long> typed = entityManager.createQuery(countQuery, Long.class);
		
		if(params!=null) {
			params.forEach(typed::setParameter);
		}
		
		return typed.getSingleResult();
	}

	private String addSpecificationToQuery(FilterAndSort filter, String query, boolean existsWhere) {
		String whereQuery = "";
		List<SearchCriteria> criterias = new ArrayList<>();
		
		if(filter!=null) {
			criterias.addAll(filter.getCriterias());
			if(criterias!=null && !criterias.isEmpty()) {
				for(SearchCriteria c : criterias) {
					String sqlCdt = loadSql(c);
					whereQuery = setWhereClause(whereQuery, sqlCdt);
				}
			}
			
			if(!whereQuery.isEmpty()) {
				if(existsWhere) { 
					query+=Constants.AND_STR + whereQuery;
				}else {
					query+=" where " + whereQuery;
				}
			}
		}
		
		return query;
	}

	private String setWhereClause(String whereQuery, String condition) {
		if(whereQuery.isEmpty()) {
			whereQuery += condition;
		}else {
			whereQuery += Constants.AND_STR + condition;
		}
		return whereQuery;
	}

	@SuppressWarnings("unchecked")
	private String loadSql(SearchCriteria criteria) {
		if(criteria.getValue() == null) {
			return "";
		}
		
		String op = null;
		String key = " lower ( " + criteria.getKey() + " ) ";
		
		String value = criteria.getValue().toString().toLowerCase();
		
		switch(criteria.getOperation()) {
		case "equals":
			if(value.isEmpty()) {
				return key + " is NULL";
			}
			
			return key + " ='" + value + "'";
		case "notEquals":
			return "( " + key + " <> '" + value + "' or " + key + " is NULL ) ";
		case "startsWith":
			return key + " like '" + value + "%'";
		case "endsWith":
			return key + " like '%" + value + "%'";
		case "contains":
			return key + " like '%" + value + "'";
		case "notContains":
			return key + " not like '%" + value + "%'";
		case "in":
			String inList = ((List<String>) criteria.getValue()).stream().map(c-> "'" + c + "'").collect(Collectors.joining(","));
			return key + " in (" + inList + ")";
		case "greaterThanOrEqualTo":
			return criteria.getKey() + " >= '" + value + "'";
		case "lessThanOrEqualTo":
			return criteria.getKey() + " <= '" + value + "'"; 
		case "lessThan":
			return criteria.getKey() + " < '" + value + "'"; 
		case "greaterThan":
			return criteria.getKey() + " > '" + value + "'"; 
		case "greaterThanOrEqualToDate":
			return criteria.getKey() + " >= '" + value + "'";
		case "lessThanOrEqualToDate":
			return criteria.getKey() + " <= '" + value + "'"; 
		case "between":
			String value2 = criteria.getValue2().toString().toLowerCase();
			return criteria.getKey() + " between '" + value + "'" + "and "+"'" + value2 +"'"; 						  
		default:
			return op;
		
		}
	}
}
