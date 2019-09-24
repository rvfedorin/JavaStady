/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rv.fedorin.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wolf
 */
@Controller
public class SillyController {
    
    @RequestMapping("showForm")
    public String getTheForm(){
        return "silly";
    }
}
