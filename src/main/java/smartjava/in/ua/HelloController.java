package smartjava.in.ua;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @Value("${service.name}")
    private String microserviceName;

    @Value("${service.url.call}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/transit")
    public String transit() {
//        Add random to this endpoint
        String response;
        if (new Random().nextInt(4) > 0) {
            response  = restTemplate.getForObject(url, String.class);
            LOGGER.info("Called /transit endpoint");
        } else  {
            response = "Breaking the loop";
        }
        return this.microserviceName + response;
    }

    @GetMapping("/terminal")
    public String terminal() {
        LOGGER.info("Called /terminal endpoint. Calling /endpoint.");
        return microserviceName;
    }
}