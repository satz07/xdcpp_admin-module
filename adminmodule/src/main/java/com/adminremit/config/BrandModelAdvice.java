package com.adminremit.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class BrandModelAdvice {

    private final BrandProperties brandProperties;

    public BrandModelAdvice(BrandProperties brandProperties) {
        this.brandProperties = brandProperties;
    }

    @ModelAttribute("brand")
    public BrandProperties brand() {
        return brandProperties;
    }
}

