package com.crowdfunding.common.dao;

import com.crowdfunding.common.PaginationInfo;
import com.crowdfunding.common.util.GenericUtil;
import com.crowdfunding.common.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by LiMeiyuan on 2016/9/26 15:35.
 * description:
 */
public class BaseHibernateDao<T, ID extends Serializable> implements CommonDao<T, ID> {
    @Autowired
    private SessionFactory sf;

    private Class<T> persistenctClass;

    private ClassMetadata persistentClassMetadata;

    public BaseHibernateDao() {
        getPersistentClass();
    }

    public T load(ID id) {
        return (T) getSession().load(getPersistentClass(), id);
    }

    @Override
    public T find(ID id) {
        if (id == null) {
            return null;
        }
        Object obj = getSession().get(getPersistentClass(), id);
        if (obj != null) {
            boolean enable = PaginationUtil.isEnable();
            PaginationUtil.setEnable(false);
            fillOtherField((T) obj);
            PaginationUtil.setEnable(enable);
        }
        return (T) obj;
    }

    /**
     * 根据制定条件查询唯一符合条件的记录
     *
     * @param param 要查询的参数名
     * @param value 查询的参数的值
     * @return 如果查询结果为空或者不唯一，均返回null
     */
    public T findUnique(String param, Object value) {
        List<T> results = createCriteria().add(Restrictions.eq(param, value)).list();
        if (results.size() != 1) {
            return null;
        }
        return results.get(0);
    }

    protected void fillOtherField(T obj) {
    }

    @Override
    public List<T> findAll() {
        DetachedCriteria dc = createDetachedCriteria();
        dc.addOrder(Order.asc("id"));
        List<T> list = query(dc);
        if (list != null && !list.isEmpty()) {
            boolean enable = PaginationUtil.isEnable();
            PaginationUtil.setEnable(false);
            for (T t : list) {
                fillOtherField(t);
            }
            PaginationUtil.setEnable(enable);
        }
        return list;
    }

    public int count(String hql) throws DataAccessException {
        return count(hql, Collections.<String, Object>emptyMap());
    }

    public int count(String hql, Map<String, Object> param) throws DataAccessException {
        Session session = sf.getCurrentSession();
        Integer amount = 0;
        int sqlFrom = hql.indexOf("from");
        int sqlOrderby = hql.indexOf("order by");
        String countStr = "";
        if (sqlOrderby > 0) {
            countStr = "select count(*) " + hql.substring(sqlFrom, sqlOrderby);
        } else {
            countStr = "select count(*) " + hql.substring(sqlFrom);
        }
        Query query = null;
        try {
            query = session.createQuery(countStr);
            query.setProperties(param);
            List list = query.list();
            if (!list.isEmpty()) {
                if (hql.contains("group by") || hql.contains("GROUP BY")) {
                    amount = list.size();
                } else {
                    amount = new Integer(list.get(0).toString());
                }
            } else {
                return 0;
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return amount;
    }


    @Override
    public void create(T domain) {
        getSession().save(domain);
    }

    @Override
    public void update(T domain) {
        getSession().update(domain);
    }

    @Override
    public void createOrUpdate(T domain) {
        getSession().saveOrUpdate(domain);
    }

    @Override
    public void delete(ID id) {
        T t = find(id);
        if (t == null) {
            return;
        }
        getSession().delete(t);
    }

    private Class<T> getPersistentClass() {
        if (this.persistenctClass == null) {
            Type[] types = GenericUtil.getGenericTypes(this.getClass());
            this.persistenctClass = (Class<T>) types[0];
        }
        return this.persistenctClass;
    }

    private ClassMetadata getPersistentClassMetadata() {
        if (this.persistentClassMetadata == null) {
            this.persistentClassMetadata = sf.getClassMetadata(getPersistentClass());
        }

        return this.persistentClassMetadata;
    }

    protected Session getSession() {
        return this.sf.getCurrentSession();
    }

    protected DetachedCriteria createDetachedCriteria() {
        DetachedCriteria dc = DetachedCriteria.forClass(getPersistentClass());
        return dc;
    }

    protected Criteria createCriteria() {
        Criteria c = getSession().createCriteria(getPersistentClass());
        c.setResultTransformer(Criteria.ROOT_ENTITY);
        return c;
    }

    protected List<T> query(DetachedCriteria dc) {
        Criteria c = dc.getExecutableCriteria(getSession());
        return doQuery(c, true);
    }

    protected List<T> queryBase(DetachedCriteria dc) {
        Criteria c = dc.getExecutableCriteria(getSession());
        return doQuery(c, false);
    }

    private List<T> doQuery(Criteria c, boolean isFillOtherInfo) {
        PaginationInfo pageInfo = PaginationUtil.getPaginationFromRequest();
        if (PaginationUtil.isEnable() && pageInfo != null) {
            int count = Integer.parseInt(c.setProjection(Projections.countDistinct("id")).uniqueResult().toString());
            c.setProjection(null);

            int pageCount = count / pageInfo.getpageSizes();
            if (count % pageInfo.getpageSizes() > 0) {
                pageCount++;
            }

            int pageNo = pageInfo.getpageNo();
            int pageSize = pageInfo.getpageSizes();
            if (pageNo <= 0) pageNo = 1;
            if (pageNo > pageCount) pageNo = pageCount;

            c.setFirstResult((pageNo - 1) * pageSize)
                    .setMaxResults(pageSize);

            PaginationUtil.setPaginationToRequest(count, pageCount);
        }
        c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List<T> list = c.list();
        if (isFillOtherInfo) {
            fillOtherInfoList(list);
        }
        return list;
    }

    private void fillOtherInfoList(List<T> list) {
        if (list != null && !list.isEmpty()) {
            boolean enable = PaginationUtil.isEnable();
            PaginationUtil.setEnable(false);
            for (T t : list) {
                fillOtherField(t);
            }
            PaginationUtil.setEnable(enable);
        }
    }

    protected List<T> query(Criteria c) {
        return doQuery(c, true);
    }

    protected void flush() {
        getSession().flush();
    }

    protected List<T> queryHql(String hql) {
        Query query = getSession().createQuery(hql);
        return query.list();
    }

    protected int executeHql(String hql) {
        Query query = getSession().createQuery(hql);
        return query.executeUpdate();
    }


    public List<T> findByProperty(String propertyName, String propertyValue) {
        return createCriteria().add(Restrictions.eq(propertyName, propertyValue)).list();
    }

    public boolean hasProperty(String propertyName, String propertyValue) {
        List<T> lists = findByProperty(propertyName, propertyValue);
        return lists != null && lists.size() > 0;
    }

    public void saveOrUpdate(T t, ID id) {
        if (id == null) {
            this.create(t);
        }
        if (id instanceof String) {
            if (StringUtils.isBlank((String) id)) {
                this.create(t);
            }
        }
        this.update(t);
    }
}
