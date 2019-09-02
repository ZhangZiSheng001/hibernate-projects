package cn.zzs.hibernate.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: HibernateUtils
 * @Description: 这个工具类主要为了简化hibernate的连接获取和事务操作
 * @author: zzs
 * @date: 2019年9月2日 上午11:31:07
 */
public class HibernateUtils {
	/**
	 * EntityManager工厂
	 */
	private static EntityManagerFactory entityManagerFactory;
	/**
	 * 指定配置文件中的persistenceUnitName
	 */
	private static String persistenceUnitName = "JPA";
	/**
	 * 用于将EntityManager与当前线程绑定
	 */
	private static ThreadLocal<EntityManager> tl = new ThreadLocal<>();
	/**
	 * 对象锁，用于保证EntityManager的线程安全
	 */
	private static Object obj = new Object();

	private static Logger logger = LoggerFactory.getLogger(HibernateUtils.class);

	static {
		init();
	}

	/**
	 * @Title: getEntityManagerFactory
	 * @Description: 获取EntityManagerFactory
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:53:51
	 * @return: EntityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		return entityManagerFactory;
	}

	/**
	 * @Title: getEntityManager
	 * @Description: 获取当前线程绑定的EntityManager
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:53:51
	 * @return: EntityManager
	 */
	public static EntityManager getEntityManager() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		EntityManager entityManager = tl.get();
		if (entityManager == null) {
			synchronized (obj) {
				if(tl.get() == null) {
					entityManager = entityManagerFactory.createEntityManager();
					tl.set(entityManager);
				}
			}
		}
		return entityManager;
	}

	/**
	 * @Title: beginTransaction
	 * @Description: 开启事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:56:16
	 * @return: void
	 */
	public static void beginTransaction() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
	}

	/**
	 * @Title: commit
	 * @Description: 提交事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:57:20
	 * @return: void
	 */
	public static void commit() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().commit();
	}
	
	/**
	 * 
	 * @Title: resetEntityManager
	 * @Description: 解除当前线程绑定的EntityManager对象，返回重新生成的实例。
	 * @author: zzs
	 * @date: 2019年9月2日 下午8:25:09
	 * @return: EntityManager
	 */
	public static EntityManager resetEntityManager() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		tl.remove();
		return getEntityManager();
	}
	/**
	 * @Title: rollback
	 * @Description: 回滚事务
	 * @author: zzs
	 * @date: 2019年9月2日 下午12:57:20
	 * @return: void
	 */
	public static void rollback() {
		if (entityManagerFactory == null) {
			logger.warn("EntityManagerFactory创建失败，请检查连接后再使用");
		}
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().rollback();
	}

	/**
	 * @Title: init
	 * @Description: 加载配置文件，初始化EntityManagerFactory
	 * @author: zzs
	 * @date: 2019年9月2日 上午11:32:20
	 * @return: void
	 */
	private static void init() {
		try {
			//加载配置,创建EntityManager工厂
			entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		} catch (Exception e) {
			logger.error("加载配置文件创建entityManagerFactory失败", e);
		}
	}
}
