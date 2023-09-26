package model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Query;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import utilities.ObjectChecker;

public class Persister {
	private static SessionFactory factory;

	public static void startDBConnection() {
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
		factory = metadata.getSessionFactoryBuilder().build();
	}

	public static void stopDBConnection() {
		factory.close();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T findById(Class klass, Long id) {
		Session session = factory.openSession();
		T obj = (T) session.get(klass, id);
		session.close();
		return obj;
	}

	public static void save(Object obj) {
		Session session = null;
		org.hibernate.Transaction t = null;
		try {
			session = factory.openSession();
			session.setHibernateFlushMode(FlushMode.COMMIT);
			t = session.beginTransaction();
			session.saveOrUpdate(obj);
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (session != null)
				session.close();
		}
	}
//
//	public static Long nextIDFor(String sequence) {
//		Session session = factory.openSession();
//		Long id = ((BigDecimal) session
//				.createNativeQuery("select next_val as id_val from " + sequence + " with (updlock, rowlock)").list()
//				.get(0)).longValue();
//		session.close();
//		return id;
//	}

	public static <T> List<T> list(Class<T> klass) {
		Session session = factory.openSession();
		List<T> list = session.createQuery("From " + klass.getSimpleName()).list();
		session.close();
		return list;
	}

	public static <T> T searchFor(String sqlString) {
		Session session = factory.openSession();
		List<T> list = session.createNativeQuery(sqlString).list();
		session.close();
		return list.get(0);
	}

	public static <T> T fetchFirstRow(String sqlString, Map<String, Object> params) {
		Session session = factory.openSession();
		Query q = session.createQuery(sqlString);
		q.setMaxResults(1);
		setParameters(q, params);
		List<T> resultList = q.getResultList();
		session.close();
		return ObjectChecker.isNotEmptyOrNull(resultList) ? resultList.get(0) : null;
	}

	private static void setParameters(Query q, Map<String, Object> params) {
		for (Entry<String, Object> entry : params.entrySet()) {
			q.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public static Map<String, Object> params(Object... objects) {
		Map<String, Object> params = new HashMap<>();
		for (int i = 0; i < objects.length; i += 2) {
			params.put((String) objects[i], objects[i + 1]);
		}
		return params;
	}
}
