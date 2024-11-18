package model;

import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.hibernate.query.Query;
import org.hibernate.query.*;
import utilities.*;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.*;

public class Persister
{
    private static SessionFactory factory;

    public static void startDBConnection()
    {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(ssr).getMetadataBuilder().build();
        factory = metadata.getSessionFactoryBuilder().build();
    }

    public static void stopDBConnection()
    {
        factory.close();
    }

    private static void execute(Consumer<Result> consumer, Result result)
    {
        if (session == null)
        {
            try
            {
                session = factory.openSession();
                t = session.beginTransaction();
                consumer.accept(result);
                if (result.isFailed())
                {
                    t.rollback();
                    return;
                }
                t.commit();
            } catch (Exception e)
            {
                e.printStackTrace();
                try
                {
                    t.rollback();
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            } finally
            {
                if (session == null)
                    return;
                session.close();
                session = null;
            }

        } else
        {
            consumer.accept(result);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T findById(Class klass, Long id)
    {
        ObjectWrapper<T> wrapper = new ObjectWrapper<>();
        execute((r) ->
        {
            wrapper.setObject((T) session.get(klass, id));
        }, new Result());
        return wrapper.getObject();
    }

    private static Session session = null;
    private static Transaction t = null;

    public static Session openSession()
    {
        if (session != null)
            return session;
        session = factory.openSession();
        session.setHibernateFlushMode(FlushMode.COMMIT);
        return session;

    }

    public static Result saveOrUpdate(Object obj)
    {
        Result result = new Result();
        execute((r) ->
        {
            if (obj instanceof IEntity)
                ((IEntity) obj).isValidForCommit(r);
            if (r.isFailed())
                return;
            session.saveOrUpdate(obj);
            if (obj instanceof IEntity)
                ((IEntity) obj).postCommit(r);
        }, result);
        return result;
    }

    public static <T> List<T> list(Class<T> klass)
    {

        return list(klass, "", new HashMap<>());
    }

    public static <T> List<T> list(Class<T> klass, String condition, Map<String, Object> params)
    {
        ObjectWrapper<List<T>> wrapper = new ObjectWrapper<>();
        execute(r ->
        {
            Query<T> query = session.createQuery("From " + klass.getSimpleName() + " " + condition);

            setParameters(query, params);
            List<T> resultedList = query.list();
            wrapper.setObject(resultedList == null ? new ArrayList<>() : resultedList);
        }, new Result());
        return wrapper.getObject();
    }

    public static <T> List<T> list(Class<T> klass, String condition, Map<String, Object> params,
                                   PaginationData paginationData)
    {
        ObjectWrapper<List<T>> wrapper = new ObjectWrapper<>();
        execute(r ->
        {
            Query<T> query = session
                    .createQuery("From " + klass.getSimpleName() + " " + ObjectChecker.toStringOrEmpty(condition));
            setPagination(paginationData, query);
            setParameters(query, params);
            wrapper.setObject(query.list());
        }, new Result());
        return wrapper.getObject();
    }

    public static <T> List<T> searchFor(String sqlString)
    {
        List<T> list = new ArrayList<>();
        execute((r) ->
        {
            list.addAll(session.createQuery(sqlString).list());
        }, new Result());
        if (ObjectChecker.isEmptyOrZeroOrNull(list))
            return null;
        return list;
    }

    public static <T> T getSingleResultFromNativeQuery(String sqlString, Map<String, Object> params)
    {
        return getSingleResult(sqlString, params, queryString -> session.createNativeQuery(queryString));
    }

    public static <T> T getSingleResult(String sqlString, Map<String, Object> params)
    {
        return getSingleResult(sqlString, params, queryString -> session.createQuery(queryString));
    }

    private static <T> T getSingleResult(String sqlString, Map<String, Object> params,
                                         Function<String, Query> queryCreator)
    {
        Result result = new Result();

        List<T> resultList = new ArrayList<>();
        execute((r) ->
        {
            Query q = queryCreator.apply(sqlString);
            setParameters(q, params);
            resultList.add((T) CollectionsUtility.fetchFirstElement(q.list()));
        }, result);
        return CollectionsUtility.fetchFirstElement(resultList);
    }

    public static <T> void setPagination(PaginationData paginationData, Query<T> query)
    {
        query.setFirstResult(paginationData.getFirstResult());
        query.setMaxResults(paginationData.getMaxResult());
    }

    private static void setParameters(Query q, Map<String, Object> params)
    {
        for (Entry<String, Object> entry : params.entrySet())
        {
            q.setParameter(entry.getKey(), entry.getValue());
        }
    }

    public static Map<String, Object> params(Object... objects)
    {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < objects.length; i += 2)
        {
            params.put((String) objects[i], objects[i + 1]);
        }
        return params;
    }

    public static int countOf(Class<?> klass)
    {
        return countOf(klass, "");
    }

    public static int countOf(Class<?> klass, String condition)
    {
        return countOf(klass.getSimpleName(), condition, new HashMap<>());
    }

    public static int countOf(String entity, String condition, Map<String, Object> params)
    {
        ObjectWrapper<Integer> obj = new ObjectWrapper<>();
        execute((r) ->
        {
            NativeQuery query = session.createNativeQuery("SELECT COUNT(id) FROM " + entity + " " + condition);
            setParameters(query, params);
            obj.setObject((Integer) query.getSingleResult());
        }, new Result());
        return obj.getObject();
    }

    public static <T> T findByCode(Class<T> klass, String code)
    {
        return findByCode(klass.getSimpleName(), code);
    }

    public static <T> T findByCode(String entityType, String code)
    {
        return getSingleResult("FROM " + entityType + " WHERE code = :code ", params("code", code));

    }

    public static void remove(Object obj)
    {
        if (obj == null)
            return;
        execute(r ->
        {
            session.remove(obj);
        }, new Result());
    }

    public static void executeNativeQuery(String queryString, Map<String, Object> params)
    {
        execute(r ->
        {
            NativeQuery nativeQuery = session.createNativeQuery(queryString);
            setParameters(nativeQuery, params);
            nativeQuery.executeUpdate();
        }, new Result());
    }
}
