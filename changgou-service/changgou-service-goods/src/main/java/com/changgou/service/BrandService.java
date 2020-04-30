package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {

    /**
     * 查询所有品牌
     *
     * @return
     */
    List<Brand> findAll();

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Brand findById(Integer id);

    /**
     * 新增品牌
     *
     * @param brand
     */
    void add(Brand brand);

    /**
     * 修改品牌数据
     *
     * @param brand
     */
    void update(Brand brand);

    /**
     * 删除品牌
     *
     * @param id
     */
    void delete(Integer id);

    /**
     * 多条件搜索品牌方法
     *
     * @param brand
     * @return
     */
    List<Brand> findList(Brand brand);

    /**
     * 分页查询
     * @param page 当前页
     * @param size 每页显示条数
     * @return
     */
    PageInfo<Brand> findPage(Integer page, Integer size);
}
