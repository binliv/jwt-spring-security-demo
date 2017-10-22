package org.zerhusen.security.controller;

import com.yuyan.web.dto.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.model.security.Report;
import org.zerhusen.security.repository.ReportRepository;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(path = "/reports")
public class ReportController {

    @Autowired
    ReportRepository reportRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Report> getReportList(String testee, Pageable pageable){
        if(StringUtils.isEmpty(testee)){
            return reportRepository.findAll(pageable);
        } else {
            return reportRepository.findByTesteeContaining(testee, pageable);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResultDTO save(@RequestBody Report report){
        report.setTime(new Date());
        reportRepository.save(report);
        return new ResultDTO("ok", 0);
    }
}
