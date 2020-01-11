package com.shy.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shy.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T,ID> {

    @Autowired
    private BaseMapper<T,ID> baseMapper;

    /**
     * 添加记录返回行数
     * @param entity
     * @return
     */
    public Integer insertSelective(T entity) throws DataAccessException{
        return baseMapper.insertSelective(entity);
    }

    /**
     * 添加记录返回主键
     * @param entity
     * @return
     */
    public ID insertHasKey(T entity) throws DataAccessException{
        baseMapper.insertHasKey(entity);
        try {
           return (ID) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    /**
     * 批量添加
     * @param entities
     * @return
     */
    public Integer insertBatch(List<T> entities) throws DataAccessException{
        return baseMapper.insertBatch(entities);
    }


    /**
     * 根据id 查询详情
     * @param id
     * @return
     */
    public T selectByPrimaryKey(ID id) throws DataAccessException{
        return baseMapper.selectByPrimaryKey(id);
    }


    /**
     * 多条件查询
     * @param baseQuery
     * @return
     */
    public List<T> selectByParams(BaseQuery baseQuery) throws DataAccessException{
        return baseMapper.selectByParams(baseQuery);
    }


    /**
     * 更新单条记录
     * @param entity
     * @return
     */
    public Integer updateByPrimaryKeySelective(T entity) throws DataAccessException{
        return baseMapper.updateByPrimaryKeySelective(entity);
    }


    /**
     * 批量更新
     * @param entities
     * @return
     */
    public Integer updateBatch(List<T> entities) throws DataAccessException{
        return baseMapper.updateBatch(entities);
    }

    /**
     * 删除单条记录
     * @param id
     * @return
     */
    public Integer deleteByPrimaryKey(ID id) throws DataAccessException{
        return baseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    public Integer deleteBatch(ID[] ids) throws DataAccessException{
        return baseMapper.deleteBatch(ids);
    }

    /**
     * 多条件查询for可修改表格
     * @param baseQuery
     * @return
     */
    public Map<String,Object> queryByParamsForDataGrid(BaseQuery baseQuery){
        Map<String,Object> map=new HashMap<>();
        //开启分页
        PageHelper.startPage(baseQuery.getPage(),baseQuery.getRows());
        //查询营销机会列表
        PageInfo<T> pageInfo = new PageInfo<T>(selectByParams(baseQuery));
        //把总数据数与机会列表放入到map中
        map.put("total",pageInfo.getTotal());
        map.put("rows",pageInfo.getList());
        return map;
    }
}
