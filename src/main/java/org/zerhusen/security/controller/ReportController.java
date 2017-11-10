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
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    private Map<String,  Function<String, BiFunction<String, Pageable, Page<Report>>>> searchCommands = new HashMap<>();

    {
        searchCommands.put("admin|null|null", (testee) -> (tester, pageable) -> reportRepository.findAll(pageable) );
        searchCommands.put("admin|testee|null", (testee) -> (tester, pageable) -> reportRepository.findByTesteeContaining(testee, pageable) );
        searchCommands.put("admin|null|tester", (testee) -> (tester, pageable) -> reportRepository.findByTesterContaining(tester, pageable) );
        searchCommands.put("admin|testee|tester", (testee) -> (tester, pageable) -> reportRepository.findByTesteeContainingAndTesterContaining(testee, tester, pageable) );
        searchCommands.put("nonAdmin|null|tester", (testee) -> (tester, pageable) -> reportRepository.findByTester(tester, pageable) );
        searchCommands.put("nonAdmin|testee|tester", (testee) -> (tester, pageable) -> reportRepository.findByTesteeContainingAndTester(testee, tester, pageable) );
    }

    @RequestMapping(value = "reports", method = RequestMethod.GET)
    public Page<Report> getReportList(HttpServletRequest request, String testee, String tester, Pageable pageable){

        UserDTO userDTO = userService.loadUserByToken(request);
        boolean isAdmin = userDTO.getRole().stream().reduce((a, b) -> {
            if("admin".equals(a) || "admin".equals(b)){
                return "admin";
            } else{
                return b;
            }
        }).map(role -> "admin".equals(role)).get();

        if(!isAdmin){
            tester = userDTO.getName();
        }

        String command = (isAdmin?"admin":"nonAdmin") +"|"
                + ((StringUtils.isEmpty(testee))?"null":"testee") + "|"
                + ((StringUtils.isEmpty(tester))?"null":"tester");
        return searchCommands.get(command).apply(testee).apply(tester, pageable);

        // before refactor
//        if(StringUtils.isEmpty(testee)){
//            if(isAdmin){
//                if(StringUtils.isEmpty(tester)) {
//                    return reportRepository.findAll(pageable);
//                }else{
//                    return reportRepository.findByTesterContaining(tester, pageable);
//                }
//            }else{
//                return reportRepository.findByTester(userDTO.getName(),pageable);
//            }
//        } else {
//            if(isAdmin){
//                if(StringUtils.isEmpty(tester)){
//                    return reportRepository.findByTesteeContaining(testee, pageable);
//                } else{
//                    return reportRepository.findByTesteeContainingAndTesterContaining(testee,tester, pageable);
//                }
//            }else{
//                return reportRepository.findByTesteeContainingAndTester(testee,userDTO.getName(),pageable);
//            }
//        }
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
