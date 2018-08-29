package org.linker.controller;

import io.swagger.annotations.ApiOperation;
import org.linker.model.AccessLog;
import org.linker.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RWM
 * @date 2018/8/28
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    AccessLogService accessLogService;

    @ApiOperation("查询操作日志")
    @GetMapping("/find")
    public List<AccessLog> find(@RequestParam Integer page,
                                @RequestParam Integer size) {
        return accessLogService.find(page, size);
    }
}
