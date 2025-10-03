// 代码生成时间: 2025-10-03 20:35:50
 * IntelligentQuestionBankSystem.java
 * 
 * This program implements an intelligent question bank system using Java and Hibernate.
 * The system allows users to manage questions and answers within a database.
 * 
 * @author Your Name
 * @version 1.0
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;

public class IntelligentQuestionBankSystem {

    // Method to create a Session Factory
    private static SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    // Method to add a new question to the question bank
    public static void addQuestion(String questionText, String[] options, String correctAnswer) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Question question = new Question(questionText, options, correctAnswer);
            session.save(question);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all questions from the question bank
    public static List<Question> getAllQuestions() {
        try (Session session = getSessionFactory().openSession()) {
            List<Question> questions = session.createQuery("FROM Question", Question.class).list();
            return questions;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Method to update an existing question in the question bank
    public static void updateQuestion(int id, String questionText, String[] options, String correctAnswer) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Question question = session.get(Question.class, id);
            question.setQuestionText(questionText);
            question.setOptions(options);
            question.setCorrectAnswer(correctAnswer);
            session.update(question);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete a question from the question bank
    public static void deleteQuestion(int id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Question question = session.get(Question.class, id);
            session.delete(question);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * Question.java
 * 
 * Represents a question entity in the question bank system.
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Arrays;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_text")
    private String questionText;

    @ElementCollection
    @Column(name = "options")
    private String[] options;

    @Column(name = "correct_answer")
    private String correctAnswer;

    public Question() {}

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Getters and setters for the question attributes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
