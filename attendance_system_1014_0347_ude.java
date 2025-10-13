// 代码生成时间: 2025-10-14 03:47:38
package com.example.attendancesystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class AttendanceSystem {
    // Hibernate Session Factory
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + " There was an error with Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Close the SessionFactory when you're done
        sessionFactory.close();
    }

    public static Session openSession() {
        // Open a new Session
        return sessionFactory.openSession();
    }

    // Clock in method
    public static void clockIn(String userId) {
        try (Session session = openSession()) {
            // Start transaction
            Transaction transaction = session.beginTransaction();
            try {
                // Assuming a User entity with a clockIn method
                User user = session.get(User.class, userId);
                if (user == null) {
                    throw new IllegalArgumentException("User not found.");
                }
                user.clockIn();
                session.update(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    // Clock out method
    public static void clockOut(String userId) {
        try (Session session = openSession()) {
            // Start transaction
            Transaction transaction = session.beginTransaction();
            try {
                // Assuming a User entity with a clockOut method
                User user = session.get(User.class, userId);
                if (user == null) {
                    throw new IllegalArgumentException("User not found.");
                }
                user.clockOut();
                session.update(user);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        }
    }

    // Main method to test the system
    public static void main(String[] args) {
        // Clock in a user with ID 1
        clockIn("1");
        // Clock out the same user
        clockOut("1");
    }
}

/*
 * User.java
 * 
 * Entity representing a user in the attendance system.
 */
package com.example.attendancesystem;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    private String id;
    private Date clockInTime;
    private Date clockOutTime;

    public User() {}

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(Date clockInTime) {
        this.clockInTime = clockInTime;
    }

    public Date getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(Date clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    // Simulate clocking in
    public void clockIn() {
        // Check if the user has already clocked in
        if (clockOutTime != null) {
            throw new IllegalStateException("User has already clocked out or is not clocked in.");
        }
        clockInTime = new Date();
    }

    // Simulate clocking out
    public void clockOut() {
        // Check if the user has already clocked out or not clocked in
        if (clockInTime == null || clockOutTime != null) {
            throw new IllegalStateException("User has not clocked in or has already clocked out.");
        }
        clockOutTime = new Date();
    }
}
