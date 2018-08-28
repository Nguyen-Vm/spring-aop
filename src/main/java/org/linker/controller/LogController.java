package org.linker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RWM
 * @date 2018/8/28
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @GetMapping("/insert")
    public String insert(@RequestParam String logid) {
        return logid;
    }

    @GetMapping("/search")
    public String search() {
        return "search success";
    }
}
