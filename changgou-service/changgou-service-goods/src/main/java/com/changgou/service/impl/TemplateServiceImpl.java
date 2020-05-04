package com.changgou.service.impl;

import com.changgou.dao.TemplateMapper;
import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateMapper templateMapper;

    @Override
    public PageInfo<Template> findPage(Template template, int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //构建查询条件
        Example example = createExample(template);
        return new PageInfo<>(templateMapper.selectByExample(example));
    }

    @Override
    public PageInfo<Template> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分装查询结果
        return new PageInfo<>(templateMapper.selectAll());
    }

    @Override
    public List<Template> findList(Template template) {
        Example example = createExample(template);
        return templateMapper.selectByExample(example);
    }

    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKey(template);
    }

    @Override
    public void add(Template template) {
        templateMapper.insert(template);
    }

    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    /**
     * 构建查询条件
     *
     * @param template
     * @return
     */
    public Example createExample(Template template) {
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if (template != null) {
            if (!StringUtils.isEmpty(template.getId())) {
                criteria.andEqualTo("id", template.getId());
            }
            if (!StringUtils.isEmpty(template.getName())) {
                criteria.andLike("name", "%" + template.getName() + "%");
            }
            if (!StringUtils.isEmpty(template.getSpecNum())) {
                criteria.andEqualTo("specNum", template.getSpecNum());
            }
            if (!StringUtils.isEmpty(template.getParaNum())) {
                criteria.andEqualTo("paraNum", template.getParaNum());
            }
        }
        return example;
    }
}
