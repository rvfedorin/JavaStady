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
public class ReadStudentDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();
            System.out.println("[*] Creating new student object...");
            
            
            // create a student java object
            Student newStudent = new Student("Daffy", "Duck", "daffy@email.ru");

            // start a transaction
            session.beginTransaction();
            
            // save the student java object
            System.out.println("[*] Saving the student...");
            System.out.println(newStudent);
            session.save(newStudent);
            
            // commit transaction
            session.getTransaction().commit();
            
            // NEW CODE
            // find out new primary key of the student
            System.out.println("[*] Saved student has id: " + newStudent.getId());
            
            // now get a new session and start transaction
            session = factory.getCurrentSession();
            session.beginTransaction();
            
            // retrieved student based on the id
            System.out.println("[*] Getting student with id: " + newStudent.getId());
            
            Student myStudent = session.get(Student.class, newStudent.getId());
            
            System.out.println("[*] Get complete: " + myStudent);
            
            // commit the transaction
            session.getTransaction().commit();
            
                        
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
