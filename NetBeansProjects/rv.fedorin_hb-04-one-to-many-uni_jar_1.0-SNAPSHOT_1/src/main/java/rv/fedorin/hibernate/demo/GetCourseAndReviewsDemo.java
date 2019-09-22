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
import rv.fedorin.hibernate.demo.entity.Review;

/**
 *
 * @author R. V. Fedorin
 */
public class GetCourseAndReviewsDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            System.out.println("\n\n[*] ============================\n\n");

            int id = 10;
            Course tempCourse = session.get(Course.class, id);
            System.out.println("course: " + tempCourse);
            System.out.println("reviews: " + tempCourse.getReviews());
            
            System.out.println("\n\n[*] ============================\n\n");
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
