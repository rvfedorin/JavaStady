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
public class GetInstructorDetailDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();
              Session session = factory.getCurrentSession()) {

            // start a transaction
            session.beginTransaction();

            int id = 2;
            InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);
            System.out.println("\n\n[*] ****************************************\n\n");
            System.out.println("[*] Detail: " + instructorDetail);
            // commit transaction
            session.getTransaction().commit();

            if (instructorDetail != null) {
                Instructor instructor = instructorDetail.getInstructor();
                System.out.println("[*] Instructor: " + instructor);
            }

            System.out.println("[*] Done");
            System.out.println("\n\n[*] ****************************************\n\n");
        }
    } // ** main()
} // ** class CreateStudentDemo
