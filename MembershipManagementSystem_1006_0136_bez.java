// 代码生成时间: 2025-10-06 01:36:25
package membership;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.selector.SimpleStrategyRegistrationImpl;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.metadata.ClassMetadata;

import java.util.List;
import java.util.Properties;

// 会员实体类
class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    // 省略getter和setter方法
}

// 会员DAO接口
interface MemberDAO {
    void addMember(Member member);
    Member getMemberById(int id);
    List<Member> getAllMembers();
    void updateMember(Member member);
    void removeMember(int id);
}

// 使用Hibernate实现会员DAO
class HibernateMemberDAO implements MemberDAO {
    private SessionFactory sessionFactory;

    public HibernateMemberDAO() {
        this.sessionFactory = buildSessionFactory();
    }

    private SessionFactory buildSessionFactory() {
        try {
            // 创建配置文件
            Configuration configuration = new Configuration().configure();
            // 配置H2数据库连接和方言
            Properties settings = new Properties();
            settings.put("hibernate.connection.driver_class", "org.h2.Driver");
            settings.put("hibernate.connection.url", "jdbc:h2:mem:testdb");
            settings.put("hibernate.dialect", H2Dialect.class.getName());
            settings.put("hibernate.hbm2ddl.auto", "create-drop");
            settings.put("hibernate.show_sql", "true");

            // 构建SessionFactory
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
            return configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "
" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public void addMember(Member member) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Member getMemberById(int id) {
        Session session = sessionFactory.openSession();
        Member member = null;
        try {
            member = (Member) session.get(Member.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return member;
    }

    @Override
    public List<Member> getAllMembers() {
        Session session = sessionFactory.openSession();
        List<Member> members = null;
        try {
            members = session.createQuery("FROM Member").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return members;
    }

    @Override
    public void updateMember(Member member) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeMember(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Member member = (Member) session.get(Member.class, id);
            if (member != null) {
                session.delete(member);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}

// 主类
public class MembershipManagementSystem {
    public static void main(String[] args) {
        HibernateMemberDAO memberDAO = new HibernateMemberDAO();
        try {
            // 添加会员
            Member member = new Member();
            // 省略设置member属性的代码
            memberDAO.addMember(member);

            // 获取所有会员
            List<Member> members = memberDAO.getAllMembers();
            for (Member m : members) {
                System.out.println("ID: " + m.getId() + ", Name: " + m.getName());
            }

            // 更新会员信息
            // 省略更新member属性的代码
            memberDAO.updateMember(member);

            // 删除会员
            memberDAO.removeMember(member.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
