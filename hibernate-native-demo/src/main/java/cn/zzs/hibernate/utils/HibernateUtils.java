package cn.zzs.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: HibernateUtils
 * @Description: 主要用于加载配置文件，获取SessionFactory
 * @author: zzs
 * @date: 2019年9月2日 上午11:31:07
 */
public class HibernateUtils {
	//配置类
	private static Configuration configuration;
	//连接工厂
	private static SessionFactory sessionFactory;

	private static Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

	static {
		init();
	}
	/**
	 * @Title: getSessionFactory
	 * @Description: 获取SessionFactory
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:53:51
	 * @return
	 * @return: SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			logger.warn("SessionFactory创建失败，请检查连接后再使用");
		}
		return sessionFactory;
	}
	
	/**
	 * @Title: getCurrentSession
	 * @Description: 获取当前线程绑定的Session
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:53:51
	 * @return: SessionFactory
	 */
	public static Session getCurrentSession() {
		if (sessionFactory == null) {
			logger.warn("SessionFactory创建失败，请检查连接后再使用");
		}
		return sessionFactory.getCurrentSession();
	}	
	
	/**
	 * @Title: beginTransaction
	 * @Description: 开启事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:56:16
	 * @return: void
	 */
	public static void beginTransaction() {
		if (sessionFactory == null) {
			logger.warn("SessionFactory创建失败，请检查连接后再使用");
		}
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.begin();
	}
	
	/**
	 * @Title: commit
	 * @Description: 提交事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:57:20
	 * @return: void
	 */
	public static void commit() {
		if (sessionFactory == null) {
			logger.warn("SessionFactory创建失败，请检查连接后再使用");
		}
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.commit();
	}
	
	/**
	 * @Title: rollback
	 * @Description: 回滚事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:57:20
	 * @return: void
	 */
	public static void rollback() {
		if (sessionFactory == null) {
			logger.warn("SessionFactory创建失败，请检查连接后再使用");
		}
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = session.getTransaction();
		transaction.rollback();
	}
	
	/**
	 * @Title: init
	 * @Description: 加载配置文件，初始化SessionFactory
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:32:20
	 * @return: void
	 */
	private static void init() {
		try {
			//获取加载配置管理类
			configuration = new Configuration();
			//不给参数就默认加载hibernate.cfg.xml文件，
			configuration.configure();
			//创建Session工厂对象
			sessionFactory = configuration.buildSessionFactory();
		} catch (Exception e) {
			logger.error("加载配置文件创建sessionFactory失败", e);
		}
	}
}
