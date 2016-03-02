package com.docudile.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by PaulRyan on 3/2/2016.
 */
@Controller
public class TrainController {
    @RequestMapping("/retraining")
    public String goRetrain() {
        return "retraining";
    }
}
