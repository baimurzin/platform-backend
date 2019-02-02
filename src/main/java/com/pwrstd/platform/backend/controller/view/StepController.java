package com.pwrstd.platform.backend.controller.view;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StepController {


    @GetMapping
    public ResponseEntity next() {

        return null;
    }


}
