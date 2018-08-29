package org.linker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RWM
 * @date 2018/8/27
 */
@Api(description = "AOP相关接口")
@RestController
public class AopController {

    @ApiOperation(value = "测试AOP接口", notes = "false", httpMethod = "GET")
    @GetMapping("/aop")
    public Object aop() {
        return "aop controller";
    }

    @ApiOperation("异常接口")
    @GetMapping("/doError")
    public Object error() {
        return 1/0;
    }
}
