package org.zerhusen.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.model.security.Report;
import org.zerhusen.security.repository.ReportRepository;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/reports")
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Report> getReportList(String search, Pageable pageable){
        return reportRepository.findAll(pageable);
    }
}
