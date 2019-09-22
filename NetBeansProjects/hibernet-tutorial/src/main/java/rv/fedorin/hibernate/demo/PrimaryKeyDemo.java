/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rv.fedorin.hibernate.demo.entity.Student;

/**
 *
 * @author R. V. Fedorin
 */
public class PrimaryKeyDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();
            System.out.println("[*] Creating new student object...");
            
            
            // create 3 students java object
            Student newStudent1 = new Student("John", "Do", "john@email.ru");
            Student newStudent2 = new Student("Mary", "Pablic", "mary@email.ru");
            Student newStudent3 = new Student("Bonita", "Applebum", "bonita@email.ru");

            // start a transaction
            session.beginTransaction();
            
            // save the student java object
            System.out.println("[*] Saving the student...");
            session.save(newStudent1);
            session.save(newStudent2);
            session.save(newStudent3);
            
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()
}
