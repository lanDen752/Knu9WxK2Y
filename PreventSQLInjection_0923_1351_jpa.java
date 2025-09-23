// 代码生成时间: 2025-09-23 13:51:55
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Properties;

// 预防SQL注入的示例类
public class PreventSQLInjection {

    // Hibernate的SessionFactory对象
    private static SessionFactory sessionFactory;

    // 初始化SessionFactory
    public static void initSessionFactory() {
        // 构造配置对象
        Configuration configuration = new Configuration();
        // 设置配置属性，加载配置文件
        configuration.configure();
        // 添加额外的属性，例如方言、显示SQL语句等
        Properties settings = new Properties();
        settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.hbm2ddl.auto", "update");
        // 应用额外的属性
        configuration.setProperties(settings);
        // 构建SessionFactory
        sessionFactory = configuration.buildSessionFactory();
    }

    // 关闭SessionFactory
    public static void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // 演示使用预编译的HQL查询来预防SQL注入
    public static List<String> findUserBySecuredQuery(String name) {
        List<String> users = null;
        try (Session session = sessionFactory.openSession()) {
            // 开启事务
            Transaction transaction = session.beginTransaction();
            // 构造安全的HQL查询
            Query<String> query = session.createQuery("SELECT u.name FROM User u WHERE u.name LIKE :name", String.class);
            query.setParameter("name", name + "%"); // 使用命名参数来传递参数值
            // 执行查询
            users = query.getResultList();
            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            // 异常处理
            e.printStackTrace();
        }
        return users;
    }

    // 主方法，程序入口
    public static void main(String[] args) {
        // 初始化SessionFactory
        initSessionFactory();
        
        try {
            // 调用方法演示预防SQL注入
            List<String> users = findUserBySecuredQuery("John");
            if (users != null) {
                users.forEach(System.out::println);
            }
        } finally {
            // 关闭SessionFactory
            closeSessionFactory();
        }
    }
}
