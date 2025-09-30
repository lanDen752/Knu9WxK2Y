// 代码生成时间: 2025-10-01 02:41:23
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

// 实体类，代表康复训练计划
class TrainingPlan {
    private String id;
    private String patientId;
    private String details;
    private String status;

    // 构造函数、getter和setter省略

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

// DAO类，用于与数据库交互
class TrainingPlanDAO {
    private SessionFactory sessionFactory;

    public TrainingPlanDAO() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void addTrainingPlan(TrainingPlan plan) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(plan);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // 其他CRUD操作省略
}

// 服务类，提供业务逻辑
class TrainingService {
    private TrainingPlanDAO trainingPlanDAO;

    public TrainingService() {
        this.trainingPlanDAO = new TrainingPlanDAO();
    }

    public void createTrainingPlanForPatient(String patientId, String details) {
        TrainingPlan plan = new TrainingPlan();
        plan.setId(UUID.randomUUID().toString());
        plan.setPatientId(patientId);
        plan.setDetails(details);
        plan.setStatus("PENDING");
        trainingPlanDAO.addTrainingPlan(plan);
    }

    // 其他业务逻辑方法省略
}

// 主类，程序入口点
public class RehabilitationTrainingSystem {
    public static void main(String[] args) {
        TrainingService service = new TrainingService();
        try {
            // 创建一个新的康复训练计划
            service.createTrainingPlanForPatient("patient123", "New training plan details");
            System.out.println("Training plan created successfully");
        } catch (Exception e) {
            System.out.println("Error creating training plan: " + e.getMessage());
        }
    }
}