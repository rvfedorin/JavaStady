/*
 * Proprietary software.
 * 
 */
package rv.fedorin.hibernate.demo;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import rv.fedorin.hibernate.demo.entity.Student;

/**
 *
 * @author R. V. Fedorin
 */
public class QueryStudentDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

            // create ssession
            Session session = factory.getCurrentSession();
            System.out.println("[*] Creating new student object...");
            
            // start a transaction
            session.beginTransaction();
            
            // query students
            List<Student> students = session.createQuery("from Student").list();
            
            displayStudents(students);
            
            // qery students: lastName = "Do"
            students = session.createQuery("from Student s where s.lastName='Do'").list();
            
            // display the students
            System.out.println("\n\n[*] Students who have lastname of Do");
            displayStudents(students);
            
            // query the students who have last name Do or first name Daffy
            students = session.createQuery("from Student s "
                    + "where s.lastName='Do' or s.firstName='Daffy'").list();
            System.out.println("\n[*] Students have names Do or Daffy");
            displayStudents(students);
            
            students = session.createQuery("from Student s "
                    + "where s.email like '%y@email.ru'").list();
            System.out.println("\n[*] Students have email with ends with y@email.ru");
            displayStudents(students);
            
            // commit transaction
            session.getTransaction().commit();
            
            System.out.println("[*] Done");
        }
    } // ** main()

    private static void displayStudents(List<Student> students) {
        // display the students
        for(Student student: students) {
            System.out.println("[*] ->> " + student);
        }
    }
} // ** class CreateStudentDemo
