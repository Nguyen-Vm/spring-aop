package org.linker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RWM
 * @date 2018/8/27
 */
@RestController
public class AopController {

    @RequestMapping("/aop")
    public Object aop() {
        return "aop controller";
    }

    @RequestMapping("/doError")
    public Object error() {
        return 1/0;
    }
}
