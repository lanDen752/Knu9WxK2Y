// 代码生成时间: 2025-09-23 04:23:23
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.ServiceRegistry;
import org.hibernate.boot.spi.SessionFactoryBuilder;
import java.util.Properties;
import java.util.List;
import java.util.ArrayList;

// 定义审计日志实体类
public class AuditLog {
    private Long id;
    private String user;
    private String action;
    private String details;
    private String timestamp;

    // 省略构造函数、getter和setter方法
    // ...
}

// 定义审计日志DAO类
public class AuditLogDAO {
    private SessionFactory sessionFactory;

    public AuditLogDAO() {
        this.sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            // 创建服务注册表（ServiceRegistry）
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
            // 创建SessionFactoryBuilder
            SessionFactoryBuilder sessionFactoryBuilder = new Configuration().configure().buildSessionFactory(serviceRegistry);
            return sessionFactoryBuilder.buildSessionFactory();
        } catch (Throwable ex) {
            // 记录异常信息并关闭服务注册表
            System.err.println("Initial SessionFactory creation failed." + "
" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public void close() {
        sessionFactory.close();
    }

    public void addAuditLog(AuditLog auditLog) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(auditLog);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                throw e;
            }
        }
    }

    public List<AuditLog> getAllAuditLogs() {
        List<AuditLog> auditLogs = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            auditLogs = session.createQuery("FROM AuditLog", AuditLog.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auditLogs;
    }

    // 其他方法，如：updateAuditLog, deleteAuditLog等
    // ...
}

// 定义审计日志服务类
public class AuditLogService {
    private AuditLogDAO auditLogDAO;

    public AuditLogService() {
        this.auditLogDAO = new AuditLogDAO();
    }

    public void logAction(String user, String action, String details) {
        AuditLog auditLog = new AuditLog();
        auditLog.setUser(user);
        auditLog.setAction(action);
        auditLog.setDetails(details);
        auditLog.setTimestamp(java.time.LocalDateTime.now().toString());
        auditLogDAO.addAuditLog(auditLog);
    }

    // 其他方法，如：getAuditLogs, deleteAuditLog等
    // ...

    public void close() {
        auditLogDAO.close();
    }
}

// 主类，用于演示审计日志功能
public class SecurityAuditLogApp {
    public static void main(String[] args) {
        AuditLogService auditLogService = new AuditLogService();
        try {
            auditLogService.logAction("admin", "login", "User logged in successfully.");
            auditLogService.logAction("admin", "logout", "User logged out successfully.");
            // 获取并打印所有审计日志
            List<AuditLog> auditLogs = auditLogService.getAllAuditLogs();
            for (AuditLog auditLog : auditLogs) {
                System.out.println("User: " + auditLog.getUser() + ", Action: " + auditLog.getAction() + ", Details: " + auditLog.getDetails() + ", Timestamp: " + auditLog.getTimestamp());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            auditLogService.close();
        }
    }
}