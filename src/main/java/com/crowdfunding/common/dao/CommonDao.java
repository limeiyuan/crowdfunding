package com.crowdfunding.common.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LiMeiyuan on 2016/9/26 15:34.
 * description:
 */
public interface CommonDao<T, ID extends Serializable> {
    public T find(ID id);

    public List<T> findAll();

    public void create(T domain);

    public void update(T domain);

    public void createOrUpdate(T domain);

    public void delete(ID id);
}
