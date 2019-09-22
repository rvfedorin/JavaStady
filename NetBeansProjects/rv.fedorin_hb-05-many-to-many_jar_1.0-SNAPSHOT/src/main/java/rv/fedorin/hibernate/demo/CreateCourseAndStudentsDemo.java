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
import rv.fedorin.hibernate.demo.entity.Student;

/**
 *
 * @author R. V. Fedorin
 */
public class CreateCourseAndStudentsDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Instructor.class)
                .addAnnotatedClass(InstructorDetail.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Review.class)
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            Course tempCourse = new Course("Pacman - how to score one million points.");
            
            System.out.println("\n\n[*] ============================\n\n");
            System.out.println("[*] Saving the course.");
            session.save(tempCourse);
            System.out.println("[*] Courese: " + tempCourse);
            
            Student s1 = new Student("Jon", "Doe", "jon@mail.com");
            Student s2 = new Student("Mary", "Public", "mary@mail.com");
            
            tempCourse.add(s1);
            tempCourse.add(s2);
            
            System.out.println("Saving students");
            session.save(s1);
            session.save(s2);
            System.out.println("Students have been saved");
            
            System.out.println("\n\n[*] ============================\n\n");
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
