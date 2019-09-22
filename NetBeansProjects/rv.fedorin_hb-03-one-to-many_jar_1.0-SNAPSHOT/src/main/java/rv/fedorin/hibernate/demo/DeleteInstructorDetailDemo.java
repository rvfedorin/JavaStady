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
public class DeleteInstructorDetailDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .buildSessionFactory();
              Session session = factory.getCurrentSession()) {

            // start a transaction
            session.beginTransaction();

            int id = 3;
            InstructorDetail instructorDetail = session.get(InstructorDetail.class, id);
            System.out.println("\n\n[*] ****************************************\n\n");
            System.out.println("[*] Detail: " + instructorDetail);
            // commit transaction

            if (instructorDetail != null) {
                Instructor instructor = instructorDetail.getInstructor();
                System.out.println("[*] Instructor: " + instructor);
                instructor.setInstructorDetail(null);
            }
            
            System.out.println("[*] Deleting detail...");
            session.delete(instructorDetail);
            session.getTransaction().commit();

            System.out.println("[*] Done");
            System.out.println("\n\n[*] ****************************************\n\n");
        }
    } // ** main()
} // ** class CreateStudentDemo
