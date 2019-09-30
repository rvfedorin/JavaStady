/*
 * Proprietary software.
 * 
 */
package rv.fedorin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author R. V. Fedorin
 */
@Controller
@RequestMapping(name="/")
public class RootController {
    
    @GetMapping("/")
    public String toRoot(Model model) {
        return "index";
    }
}
