// 代码生成时间: 2025-10-08 16:35:01
package com.onlinelearning.platform;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

// Represents a Course in the online learning platform
class Course {
    private Long id;
    private String name;
    private String description;

    // Constructors, getters and setters
    public Course() {}
    public Course(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

// Data Access Object (DAO) for Course operations
class CourseDAO {
    private SessionFactory sessionFactory;

    public CourseDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to add a new course to the platform
    public void addCourse(Course course) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to retrieve all courses from the platform
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Course> query = session.createQuery("FROM Course", Course.class);
            courses = query.getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return courses;
    }
}

// Main class to interact with the online learning platform
public class OnlineLearningPlatform {
    public static void main(String[] args) {
        CourseDAO courseDAO = new CourseDAO();
        Course newCourse = new Course(null, "Java Programming", "Learn Java from scratch");
        courseDAO.addCourse(newCourse);
        List<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getId() + ", Name: " + course.getName() + ", Description: " + course.getDescription());
        }
    }
}