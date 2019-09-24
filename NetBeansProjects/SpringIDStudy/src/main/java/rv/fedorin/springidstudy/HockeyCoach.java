/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rv.fedorin.springidstudy;

import org.springframework.stereotype.Component;

/**
 *
 * @author wolf
 */
@Component
public class HockeyCoach implements Coach{

    @Override
    public String getAdvise() {
        return "Go on ice";
    }
    
}
