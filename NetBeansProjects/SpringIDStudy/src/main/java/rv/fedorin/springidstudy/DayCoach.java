/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rv.fedorin.springidstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 *
 * @author wolf
 */
@Component
public class DayCoach {

    @Autowired
    @Qualifier("tennisCoach")
    private Coach mainCoach;

    public Coach getC() {
        return mainCoach;
    }
}
