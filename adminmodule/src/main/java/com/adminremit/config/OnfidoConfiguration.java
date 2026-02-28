package com.adminremit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.onfido.Onfido;

@Configuration
public class OnfidoConfiguration {
	
	@Value("${onfido.token}")
    private String token;

    public Onfido getOnfidoConfig() {
        Onfido onfido = null;

        try {
            onfido = Onfido.builder()
                    .apiToken(token)
                    .build();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return onfido;
    }
}
