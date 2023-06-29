package com.example.employee.service;

import com.example.employee.mapper.PositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {
    @Autowired
    PositionMapper positionMapper;



}
