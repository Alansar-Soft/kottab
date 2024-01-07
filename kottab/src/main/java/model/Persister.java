package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import utilities.CollectionsUtility;
import utilities.IFile;
import utilities.ObjectChecker;
import utilities.ObjectWrapper;
import utilities.Result;

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

	private static void execute(Consumer<Result> consumer, Result result) {
		if (session == null) {
			try {
				session = factory.openSession();
				t = session.beginTransaction();
				consumer.accept(result);
				if (result.isFailed()) {
					t.rollback();
					return;
				}
				t.commit();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					t.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} finally {
				if (session == null)
					return;
				session.close();
				session = null;
			}

		} else {
			consumer.accept(result);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T findById(Class klass, Long id) {
		ObjectWrapper<T> wrapper = new ObjectWrapper<>();
		execute((r) -> {
			wrapper.setObject((T) session.get(klass, id));
		}, new Result());
		return wrapper.getObject();
	}

	private static Session session = null;
	private static Transaction t = null;

	private static Session openSession() {
		if (session != null)
			return session;
		session = factory.openSession();
		session.setHibernateFlushMode(FlushMode.COMMIT);
		return session;

	}

	public static Result saveOrUpdate(Object obj) {
		Result result = new Result();
		execute((r) -> {
			if (obj instanceof IFile)
				r.accmulate(((IFile) obj).isValidForCommit());
			if (r.isFailed())
				return;
			session.saveOrUpdate(obj);
			if (obj instanceof IFile)
				r.accmulate(((IFile) obj).postCommit());
		}, result);
		return result;
	}

	public static <T> List<T> list(Class<T> klass) {
		List<T> list = new ArrayList<>();
		try (Session session = factory.openSession()) {
			list = session.createQuery("From " + klass.getSimpleName()).list();
		}
		return list;
	}

	public static <T> T searchFor(String sqlString) {
		List<T> list = new ArrayList<>();
		execute((r) -> {
			list.addAll(session.createNativeQuery(sqlString).list());
		}, new Result());
		if (ObjectChecker.isEmptyOrZeroOrNull(list))
			return null;
		return list.get(0);
	}

	public static <T> T getSingleResultFromNativeQuery(String sqlString, Map<String, Object> params) {
		return getSingleResult(sqlString, params, queryString -> session.createNativeQuery(queryString));
	}

	public static <T> T getSingleResult(String sqlString, Map<String, Object> params) {
		return getSingleResult(sqlString, params, queryString -> session.createQuery(queryString));
	}

	private static <T> T getSingleResult(String sqlString, Map<String, Object> params,
			Function<String, Query> queryCreator) {
		Result result = new Result();

		List<T> resultList = new ArrayList<>();
		execute((r) -> {
			Query q = queryCreator.apply(sqlString);
			setParameters(q, params);
			resultList.add((T) CollectionsUtility.fetchFirstElement(q.list()));
		}, result);
		return CollectionsUtility.fetchFirstElement(resultList);
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

	public static int countOf(Class<?> klass, String query) {
		ObjectWrapper<Integer> obj = new ObjectWrapper<>();
		execute((r) -> {
			System.out.println(query);
			obj.setObject(
					(Integer) session.createNativeQuery("SELECT COUNT(id) FROM " + klass.getSimpleName() + " " + query)
							.getSingleResult());
		}, new Result());
		return obj.getObject();
	}

	public static <T> T findByCode(Class<T> klass, Long code) {
		return getSingleResult("FROM " + klass.getSimpleName() + " WHERE code = :code ", params("code", code));

	}
}
