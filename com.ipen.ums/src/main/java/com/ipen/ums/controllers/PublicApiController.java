package com.ipen.ums.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicApiController {

    @GetMapping("/")
    public String geteUserRoleTest() {
        return "api for public user";
    }

    @GetMapping("/home")
    public String getUserRoleTestHome() {
        return "api for public user home";
    }
}

