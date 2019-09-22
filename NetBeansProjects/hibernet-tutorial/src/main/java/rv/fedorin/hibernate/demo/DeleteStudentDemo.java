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
public class DeleteStudentDemo {

    public static void main(String[] args) {

        try ( SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory()) {

//            int studentId = 6;
            
            // create ssession
            Session session = factory.getCurrentSession();
            System.out.println("[*] Deleting new student object...");
            
            session.beginTransaction();
            
//            Student student = session.get(Student.class, studentId);
//            session.delete(student);
//            session.getTransaction().commit();
            
            session.createQuery("delete from Student where id=1")
                    .executeUpdate();
            
            session.getTransaction().commit();
                        
            System.out.println("[*] Done");
        }
    } // ** main()
} // ** class CreateStudentDemo
