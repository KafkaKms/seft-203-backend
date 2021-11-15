package com.kms.seft203.report;

import com.kms.seft203.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportApi {

    @Autowired
    private ReportService reportService;

    /*
    Count by field in a collection
    For example:
        - Number of each title (EM, TE, SE, BA) in Contact collection
        - Number of completed, not completed tasks in Task collection
    * */
    @GetMapping("_countBy/{collection}/{field}")
    public ResponseEntity<Map<String, Integer>> countBy(@PathVariable String collection, @PathVariable String field, Authentication authentication) {
        var user = User.of((org.springframework.security.core.userdetails.User) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reportService.countFieldsOfCollection(collection, field, user));
    }
}
