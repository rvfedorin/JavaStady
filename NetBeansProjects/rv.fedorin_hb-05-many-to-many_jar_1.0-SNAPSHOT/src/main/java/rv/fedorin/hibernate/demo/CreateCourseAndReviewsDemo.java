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
public class CreateCourseAndReviewsDemo {

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

            Course tempCourse = new Course("Pacman - how to score one million points.");
            tempCourse.add(new Review("Good courese!!"));
            tempCourse.add(new Review("COol courese, job well done!!"));
            tempCourse.add(new Review("What a dumb course!!!"));
            
            System.out.println("\n\n[*] ============================\n\n");
            System.out.println("[*] Saving the course.");
            System.out.println("[*] Courese: " + tempCourse);
            System.out.println("[*] reviews: " + tempCourse.getReviews());
            
            session.save(tempCourse);
            System.out.println("\n\n[*] ============================\n\n");
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
