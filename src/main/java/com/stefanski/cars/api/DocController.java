package com.stefanski.cars.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author Dariusz Stefanski
 */
@Controller
class DocController {

    @RequestMapping(value = "", method = GET)
    public String docs() {
        return "redirect:/docs/index.html";
    }
}
