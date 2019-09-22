/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rv.fedorin.hibernate.demo.entity.Instructor;
import rv.fedorin.hibernate.demo.entity.InstructorDetail;

/**
 *
 * @author R. V. Fedorin
 */
public class DeleteDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();

            // start a transaction
            session.beginTransaction();
            
            int id = 1;
            Instructor instructor = session.get(Instructor.class, id);
            System.out.println("Found instructor: " + instructor);
            
            if(instructor != null) {
                System.out.println("Deleting " + instructor);
                session.delete(instructor);
            }
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
