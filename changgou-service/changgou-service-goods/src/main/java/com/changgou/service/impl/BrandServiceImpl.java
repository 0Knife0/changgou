package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有
     *
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 新增品牌
     *
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 修改品牌数据
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    /**
     * 删除品牌
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }


    /**
     * 多条件搜索品牌方法
     *
     * @param brand
     * @return
     */
    @Override
    public List<Brand> findList(Brand brand) {
        Example example = createExample(brand);
        return brandMapper.selectByExample(example);
    }

    /**
     * 分页查询
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<>(brands);
    }

    /**
     * 分页+条件查询
     *
     * @param brand
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 构造条件
        Example example = createExample(brand);
        // 分页查询
        List<Brand> brands = brandMapper.selectByExample(example);
        return new PageInfo<>(brands);
    }

    /**
     * 构建查询条件
     *
     * @param brand
     * @return
     */
    public Example createExample(Brand brand) {
        // 自定义条件搜索对象
        Example example = new Example(Brand.class);
        // 条件构造器
        Example.Criteria criteria = example.createCriteria();

        if (brand != null) {
            // 根据名字搜索
            if (!StringUtils.isEmpty(brand.getName())) {
                criteria.andLike("name", "%" + brand.getName() + "%");
            }
            // 根据首字母搜索
            if (!StringUtils.isEmpty(brand.getLetter())) {
                criteria.andEqualTo("letter", brand.getLetter());
            }
        }
        return example;
    }
}
