/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import rv.fedorin.hibernate.demo.entity.Course;
import rv.fedorin.hibernate.demo.entity.Instructor;
import rv.fedorin.hibernate.demo.entity.InstructorDetail;

/**
 *
 * @author R. V. Fedorin
 */
public class FetchJoinDemo {

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
            int instId = 1;

            Query<Instructor> query = 
                    session.createQuery("select i from Instructor i "
                            + "JOIN FETCH i.courses "
                            + "where i.id=:theInstructorId", Instructor.class);
            query.setParameter("theInstructorId", instId);
            Instructor instructor = query.getSingleResult();
            System.out.println("\n\n[*] ********************** ");
            System.out.println("[*] Instructor: " + instructor);

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
