package org.projects;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("starting Transaction");
        entityManager.getTransaction().begin();

        Employee employee = new Employee();
        employee.setName("Tom Brady");

        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        System.out.println("Generated Employee ID = " + employee.getEmployeeId());

        // get an object using primary key
        Employee emp = entityManager.find(Employee.class, employee.getEmployeeId());
        System.out.println("got object "+ emp.getName()+ " " + emp.getEmployeeId());

        // get all the objects from Employee table
        List<Employee> listEmployer = entityManager.createQuery("SELECT e FROM Employee e").getResultList();

        if (listEmployer == null){
            System.out.println("No employee found . ");
        } else {
            for (Employee empl: listEmployer){
                System.out.println("Employee name= " + empl.getName() + ", Employee id " + empl.getEmployeeId());
            }
        }

        // remove and entity
        entityManager.getTransaction().begin();
        System.out.println("Deleting Employee with ID = " + emp.getEmployeeId());
        entityManager.remove(emp);
        entityManager.getTransaction().commit();

        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }
}
