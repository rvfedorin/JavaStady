/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.tutorial;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author R. V. Fedorin
 */
public class TestJdbc {
    
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/hb_student_tracker?zeroDateTimeBehavior=convertToNull" ;
        String user = "hbstudent";
        String pass = "hbstudent";
        
        System.out.println("[*] Connecting to database: " + jdbcUrl);
        
        try {
            Connection myCon = DriverManager.getConnection(jdbcUrl, user, pass);
            System.out.println("[*] Connection successful.");
            
        } catch (SQLException ex) {
            Logger.getLogger(TestJdbc.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
