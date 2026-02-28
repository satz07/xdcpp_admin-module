package com.adminremit.operations.repository;

import com.adminremit.operations.model.OperationFileUpload;
import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.model.WorkflowStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Component;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
public class OperationCustomSearch {

    private static final Logger LOG = LoggerFactory.getLogger(OperationCustomSearch.class);

    @Autowired
    OperationFileDetailsRepository operationFileDetailsRepository;


    public List<OperationFileUpload> isFileExist(String fileName) {
        Specification<OperationFileUpload> result = Specification.where(fileName(fileName));
        return operationFileDetailsRepository.findAll(result);
    }


    public List<OperationFileUpload> getAllList(String type, String user, String fileName,Date date) {

        LOG.info("Type :"+type+" User:"+user+" fileName:"+fileName+" Date:"+date);
        List<Specification<OperationFileUpload>> specifications = new ArrayList<>();

        if(fileName!=null && !fileName.isEmpty()) {
            specifications.add(fileName(fileName));
        }
        if(date!=null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR, 23);
            c.add(Calendar.MINUTE, 59);
            c.add(Calendar.SECOND, 59);
            Date endDate = c.getTime();
            specifications.add(dateSearch(date, endDate));
        }
        if(type!=null && !type.isEmpty()) {
            specifications.add(isUploadType(type));
        }
        if(user!=null && !user.isEmpty()) {
            specifications.add(isUserId(user));
        }

        Specification<OperationFileUpload> result = null;
        if (!specifications.isEmpty()) {
            int index = 0;
            result = specifications.get(index++);
            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).and(specifications.get(index));
            }
        }

        return operationFileDetailsRepository.findAll(result);
    }

    public static Specification<OperationFileUpload> isUploadType(String type) {
        return (root, query, builder) -> {
            return builder.equal(root.get("uploadType"), type);
        };
    }

    public static Specification<OperationFileUpload> isUserId(String userName) {
        return (root, query, builder) -> {
            return builder.equal(root.get("loginUser"), userName);
        };
    }

    public static Specification<OperationFileUpload> fileName(String fileName) {
        return (root, query, builder) -> {
            return builder.equal(root.join("fileInfo").get("name"), fileName);

        };
    }

    public static Specification<OperationFileUpload> dateSearch(Date dateSearch,Date endDate) {
        return (root, query, builder) -> {
            LOG.info("Date "+dateSearch);
            LOG.info("Date "+endDate);
            Predicate greaterThan =  builder.greaterThanOrEqualTo(root.get("createAt"), dateSearch);
            Predicate lessThan =  builder.lessThanOrEqualTo(root.get("createAt"), endDate);
            return builder.and(greaterThan,lessThan);
        };
    }

}
