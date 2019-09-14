package mech.mania;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // TODO: figure out directory
    @PostMapping("/game")
    public Response response(@RequestBody String name) {
        return new Response(counter.incrementAndGet(),
                String.format(template, name));
    }
}