// 代码生成时间: 2025-10-05 01:44:30
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;

import java.util.ArrayList;
import java.util.List;

// 树形结构组件类
public class TreeStructureComponent {

    // Hibernate配置和会话工厂
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    // 初始化Hibernate
    static {
        try {
            configuration = new Configuration().configure();
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 获取SessionFactory实例
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // 保存树形结构节点
    public void saveNode(Node node) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(node);
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

    // 获取树形结构节点
    public Node getNode(Integer id) {
        Session session = null;
        try {
            session = getSessionFactory().openSession();
            return session.get(Node.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    // 获取树形结构的所有节点
    public List<Node> getAllNodes() {
        Session session = null;
        List<Node> nodes = new ArrayList<>();
        try {
            session = getSessionFactory().openSession();
            nodes = session.createQuery("FROM Node", Node.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return nodes;
    }

    // Node类，用于表示树形结构的节点
    public static class Node {
        private Integer id;
        private String name;
        private Node parent; // 父节点

        // 省略构造函数、getter和setter方法

        // 获取子节点
        public List<Node> getChildren() {
            Session session = null;
            List<Node> children = new ArrayList<>();
            try {
                session = getSessionFactory().openSession();
                children = session.createQuery("FROM Node WHERE parent = :parent", Node.class)
                        .setParameter("parent", this)
                        .list();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (session != null) {
                    session.close();
                }
            }
            return children;
        }
    }

    // 主方法，用于测试树形结构组件
    public static void main(String[] args) {
        TreeStructureComponent component = new TreeStructureComponent();
        TreeStructureComponent.Node root = new TreeStructureComponent.Node();
        root.setName("Root");

        component.saveNode(root);

        TreeStructureComponent.Node child1 = new TreeStructureComponent.Node();
        child1.setName("Child 1");
        child1.setParent(root);

        component.saveNode(child1);

        TreeStructureComponent.Node child2 = new TreeStructureComponent.Node();
        child2.setName("Child 2");
        child2.setParent(root);

        component.saveNode(child2);

        // 获取并打印所有节点
        List<TreeStructureComponent.Node> nodes = component.getAllNodes();
        for (TreeStructureComponent.Node node : nodes) {
            System.out.println(node.getName());
            List<TreeStructureComponent.Node> children = node.getChildren();
            for (TreeStructureComponent.Node child : children) {
                System.out.println("  " + child.getName());
            }
        }
    }
}