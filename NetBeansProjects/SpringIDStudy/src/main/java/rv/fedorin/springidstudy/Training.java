/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rv.fedorin.springidstudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author wolf
 */
@Component
public class Training {
       
    
    public static void main(String[] args) {
        System.out.println("START");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TrainConfig.class);
        DayCoach tr = context.getBean("dayCoach", DayCoach.class);
        
        System.out.println(tr.getC().getAdvise());
    }
    
}
