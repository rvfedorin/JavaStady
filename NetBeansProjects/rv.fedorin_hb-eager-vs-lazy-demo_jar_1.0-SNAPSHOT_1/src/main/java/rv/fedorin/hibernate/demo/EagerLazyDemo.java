/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rv.fedorin.hibernate.demo.entity.Course;
import rv.fedorin.hibernate.demo.entity.Instructor;
import rv.fedorin.hibernate.demo.entity.InstructorDetail;

/**
 *
 * @author R. V. Fedorin
 */
public class EagerLazyDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();

            // start a transaction
            session.beginTransaction();

            Instructor instructor = session.get(Instructor.class, 1);
            System.out.println("\n\n[*] ********************** ");
            System.out.println("[*] Instructor: " + instructor);
            System.out.println("[*] Courses: " + instructor.getCourses());

            // commit transaction
            session.getTransaction().commit();
            session.close();
            System.out.println("\n[!] Session is closed.\n");

            System.out.println("[*] Courses: " + instructor.getCourses());

            System.out.println("[*] Done");
            System.out.println("[*] ********************** \n\n");
        }
    } // ** main()
} // ** class CreateStudentDemo
