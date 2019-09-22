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
public class UpdateStudentDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            int studentId = 1;
            
            // create ssession
            Session session = factory.getCurrentSession();
            System.out.println("[*] Creating new student object...");
            
            // now get a new session and start transaction
            session = factory.getCurrentSession();
            session.beginTransaction();
            
            // retrieved student based on the id
            System.out.println("[*] Getting student with id: " + studentId);
            
            Student myStudent = session.get(Student.class, studentId);
            
            System.out.println("[*] Updating student");
            
            myStudent.setFirstName("Scooby");
            
            // commit the transaction
            session.getTransaction().commit();
            
            // NEW CODE
            
            session = factory.getCurrentSession();
            session.beginTransaction();
            
            // update email for all students
            System.out.println("Update emails");
            session.createQuery("update Student set email='foo@gmail.com'")
                    .executeUpdate();
            
            session.getTransaction().commit();
                        
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
