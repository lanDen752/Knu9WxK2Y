// 代码生成时间: 2025-09-29 19:36:59
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.ServiceRegistryBuilder;
import java.util.Properties;
import java.util.List;
import java.util.Iterator;

// IoT网关实体类
class IoTGateway {
    private int id;
    private String name;
    private String ipAddress;

    // 省略构造函数、getter和setter方法
}

// IoT网关DAO类
class IoTGatewayDAO {
    private SessionFactory sessionFactory;

    public IoTGatewayDAO() {
        // 配置Hibernate，初始化SessionFactory
        try {
            // 创建服务注册对象
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
            // 创建SessionFactory
            sessionFactory = new Configuration().configure().buildSessionFactory(registry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void saveIoTGateway(IoTGateway gateway) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // 保存IoT网关
            session.save(gateway);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
    }

    public List<IoTGateway> getAllIoTGateways() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            // 查询所有IoT网关
            return session.createQuery("from IoTGateway").list();
        } finally {
            if (session != null) session.close();
        }
    }
}

// IoT网关管理类
public class IoTGatewayManagement {
    public static void main(String[] args) {
        IoTGatewayDAO gatewayDAO = new IoTGatewayDAO();

        // 创建IoT网关对象
        IoTGateway gateway = new IoTGateway();
        gateway.setName("Test Gateway");
        gateway.setIpAddress("192.168.1.100");

        try {
            // 保存IoT网关
            gatewayDAO.saveIoTGateway(gateway);

            // 查询所有IoT网关
            List<IoTGateway> gateways = gatewayDAO.getAllIoTGateways();
            for (IoTGateway g : gateways) {
                System.out.println("Gateway ID: " + g.getId() + ", Name: " + g.getName() + ", IP Address: " + g.getIpAddress());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}