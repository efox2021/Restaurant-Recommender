package com.mc_03.Backend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false,defaultValue = "") String name){
            return("Hello, " + name + "!");
    }
}
