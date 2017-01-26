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
        LOGGER.info("Called /transit endpoint.");
        restTemplate.getForObject(url + "terminal", String.class);
        if (new Random().nextInt(5) > 0) {
            return this.microserviceName + " -> " + restTemplate.getForObject(url + "transit", String.class);
        } else {
            return this.microserviceName;
        }

    }

    @GetMapping("/terminal")
    public String terminal() {
        LOGGER.info("Called /terminal endpoint.");
        return this.microserviceName;
    }
}