// 代码生成时间: 2025-10-12 01:43:29
 * This Java class uses the Hibernate framework to interact with a database,
 * storing and retrieving option pricing data. It includes basic error handling
 * and follows Java best practices for maintainability and extensibility.
 */
package com.example.optionpricing;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class OptionPricingModel {

    // Hibernate Session Factory
    private static SessionFactory sessionFactory;
    private static final String CONFIG_FILE_LOCATION = "hibernate.cfg.xml";

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure(CONFIG_FILE_LOCATION).buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Get the Session Factory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Close the Session Factory
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Persist an Option entity to the database
    public static void saveOption(Option option) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(option);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // Retrieve all Option entities from the database
    public static List<Option> getAllOptions() {
        List<Option> options = null;
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            Query query = session.createQuery("FROM Option");
            options = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return options;
    }

    // Define the Option entity class
    public static class Option {
        // Option attributes
        private Long id;
        private String symbol;
        private double strikePrice;
        private double timeToMaturity;
        private double volatility;
        private double riskFreeRate;

        // Getters and Setters
        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getSymbol() {
            return symbol;
        }
        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }
        public double getStrikePrice() {
            return strikePrice;
        }
        public void setStrikePrice(double strikePrice) {
            this.strikePrice = strikePrice;
        }
        public double getTimeToMaturity() {
            return timeToMaturity;
        }
        public void setTimeToMaturity(double timeToMaturity) {
            this.timeToMaturity = timeToMaturity;
        }
        public double getVolatility() {
            return volatility;
        }
        public void setVolatility(double volatility) {
            this.volatility = volatility;
        }
        public double getRiskFreeRate() {
            return riskFreeRate;
        }
        public void setRiskFreeRate(double riskFreeRate) {
            this.riskFreeRate = riskFreeRate;
        }
    }
}
