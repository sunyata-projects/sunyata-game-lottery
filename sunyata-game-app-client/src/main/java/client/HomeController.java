package client;

import org.springframework.web.bind.annotation.RestController;

/**
 * Created by leo on 17/11/24.
 */
@RestController
public class HomeController {
    public String index(){
        return "lclc";
    }
}
