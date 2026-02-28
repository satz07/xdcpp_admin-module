package com.adminremit.operations.service;


import com.adminremit.operations.model.UserCalculatorMapping;
import com.adminremit.operations.repository.UserCalculatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemitTransactionServiceImpl implements RemitTransactionService{

    @Autowired
    UserCalculatorRepository userCalculatorRepository;


    @Override
    public List<UserCalculatorMapping> getAllTransaction() {

            return userCalculatorRepository.findAllByStatus("CONFIRMED");
    }
}
