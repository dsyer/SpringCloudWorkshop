package io.spring.cloud.samples.fortuneteller.ui.services.fortunes;

import org.springframework.beans.factory.annotation.Value;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class FortuneService {

    @Autowired
    RestTemplate restTemplate;
    
    @Value("${fortune-prefix:}")
    private String fortunePrefix;
    
    @HystrixCommand(fallbackMethod = "fallbackFortune")
    public Fortune randomFortune() {
    	
        Fortune fortune = restTemplate.getForObject("http://fortunes/random", Fortune.class);
        if(fortunePrefix != null)
        	fortune.setText(fortunePrefix + fortune.getText());
    			
        return fortune;
    }

    private Fortune fallbackFortune() {
        return new Fortune(42L, 
        		(fortunePrefix != null ? fortunePrefix : "") 
        		  + "Your future is unclear.");
    }
}