package org.zerhusen.security.controller;

import com.yuyan.web.dto.ResultDTO;
import com.yuyan.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.model.security.Report;
import org.zerhusen.security.repository.ReportRepository;
import org.zerhusen.security.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@CrossOrigin
//@RequestMapping(path = "/reports")
public class ReportController {

    @Autowired
    private UserService userService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "reports", method = RequestMethod.GET)
    public Page<Report> getReportList(HttpServletRequest request, String testee, Pageable pageable){

        UserDTO userDTO = userService.loadUserByToken(request);
        boolean isAdmin = userDTO.getRole().stream().reduce((a, b) -> {
            if("admin".equals(a) || "admin".equals(b)){
                return "admin";
            } else{
                return b;
            }
        }).map(role -> "admin".equals(role)).get();
        if(StringUtils.isEmpty(testee)){
            if(isAdmin){
                return reportRepository.findAll(pageable);
            }else{
                return reportRepository.findByTester(userDTO.getName(),pageable);
            }
        } else {
            if(isAdmin){
                return reportRepository.findByTesteeContaining(testee, pageable);
            }else{
                return reportRepository.findByTesteeContainingAndTester(testee,userDTO.getName(),pageable);
            }
        }
    }

    @RequestMapping(value = "reports", method = RequestMethod.POST)
    public ResultDTO save(@RequestBody Report report){
        report.setTime(new Date());
        reportRepository.save(report);
        return new ResultDTO("ok", 0);
    }

    @RequestMapping(value = "pad/reports", method = RequestMethod.POST)
    public ResultDTO padSave(@RequestBody Report report, @RequestParam String name, @RequestParam String password){
                UsernamePasswordAuthenticationToken namePassToken = new UsernamePasswordAuthenticationToken(
                name, password
        );
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(namePassToken
        );
        if(report.getTime() == null){
            report.setTime(new Date());
        }
        reportRepository.save(report);
        return new ResultDTO("ok", 0);
    }
}
