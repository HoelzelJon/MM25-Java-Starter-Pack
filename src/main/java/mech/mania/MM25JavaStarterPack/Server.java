package mech.mania.MM25JavaStarterPack;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;

@RestController
public class Server {

    private Gson gson = new Gson();

    @RequestMapping(value = "/game" , method = RequestMethod.POST)
    public @ResponseBody String respond(@RequestBody String jsonString) {
        TestJsonStructure test = gson.fromJson(jsonString, TestJsonStructure.class);
        System.out.println(test.id + ":" + test.status);
        return "Response";
    }
}
