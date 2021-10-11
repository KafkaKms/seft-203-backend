package com.kms.seft203.report;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportApi {

    /*
    Count by field in a collection
    For example:
        - Number of each title (EM, TE, SE, BA) in Contact collection
        - Number of completed, not completed tasks in Task collection
    * */
    @GetMapping("_countBy/{collection}/{field}")
    public Map<String, Integer> countBy(@PathVariable String collection, @PathVariable String field) {
        Map<String, Integer> data = new HashMap<>();

        data.put("EM", 10);
        data.put("TE", 100);
        data.put("SE", 988);
        data.put("BA", 14);

        return data;
    }
}
