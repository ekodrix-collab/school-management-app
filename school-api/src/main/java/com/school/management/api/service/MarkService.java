package com.school.management.api.service;

import com.school.management.api.repository.MarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MarkService {

    @Autowired
    private MarkRepository markRepository;


}
