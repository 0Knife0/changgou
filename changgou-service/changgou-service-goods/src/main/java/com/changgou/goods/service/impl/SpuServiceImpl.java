package com.changgou.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.changgou.goods.dao.BrandMapper;
import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.SkuMapper;
import com.changgou.goods.dao.SpuMapper;
import com.changgou.goods.pojo.*;
import com.changgou.goods.service.SpuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/****
 * @Author:admin
 * @Description:Spu业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandMapper brandMapper;


    /**
     * Spu条件+分页查询
     *
     * @param spu  查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<Spu> findPage(Spu spu, int page, int size) {
        //分页
        PageHelper.startPage(page, size);
        //搜索条件构建  排除掉 已删除的
        Example example = createExample(spu);
        //执行搜索
        return new PageInfo<Spu>(spuMapper.selectByExample(example));
    }

    /**
     * Spu分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Spu> findPage(int page, int size) {
        //静态分页
        PageHelper.startPage(page, size);
        //分页查询
        return new PageInfo<Spu>(spuMapper.selectAll());
    }

    /**
     * Spu条件查询
     *
     * @param spu
     * @return
     */
    @Override
    public List<Spu> findList(Spu spu) {
        //构建查询条件
        Example example = createExample(spu);
        //根据构建的条件查询数据
        return spuMapper.selectByExample(example);
    }


    /**
     * Spu构建查询对象
     *
     * @param spu
     * @return
     */
    public Example createExample(Spu spu) {
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDelete", 0);//只找 没有被删除的
        if (spu != null) {
            // 主键
            if (!StringUtils.isEmpty(spu.getId())) {
                criteria.andEqualTo("id", spu.getId());
            }
            // 货号
            if (!StringUtils.isEmpty(spu.getSn())) {
                criteria.andEqualTo("sn", spu.getSn());
            }
            // SPU名
            if (!StringUtils.isEmpty(spu.getName())) {
                criteria.andLike("name", "%" + spu.getName() + "%");
            }
            // 副标题
            if (!StringUtils.isEmpty(spu.getCaption())) {
                criteria.andEqualTo("caption", spu.getCaption());
            }
            // 品牌ID
            if (!StringUtils.isEmpty(spu.getBrandId())) {
                criteria.andEqualTo("brandId", spu.getBrandId());
            }
            // 一级分类
            if (!StringUtils.isEmpty(spu.getCategory1Id())) {
                criteria.andEqualTo("category1Id", spu.getCategory1Id());
            }
            // 二级分类
            if (!StringUtils.isEmpty(spu.getCategory2Id())) {
                criteria.andEqualTo("category2Id", spu.getCategory2Id());
            }
            // 三级分类
            if (!StringUtils.isEmpty(spu.getCategory3Id())) {
                criteria.andEqualTo("category3Id", spu.getCategory3Id());
            }
            // 模板ID
            if (!StringUtils.isEmpty(spu.getTemplateId())) {
                criteria.andEqualTo("templateId", spu.getTemplateId());
            }
            // 运费模板id
            if (!StringUtils.isEmpty(spu.getFreightId())) {
                criteria.andEqualTo("freightId", spu.getFreightId());
            }
            // 图片
            if (!StringUtils.isEmpty(spu.getImage())) {
                criteria.andEqualTo("image", spu.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(spu.getImages())) {
                criteria.andEqualTo("images", spu.getImages());
            }
            // 售后服务
            if (!StringUtils.isEmpty(spu.getSaleService())) {
                criteria.andEqualTo("saleService", spu.getSaleService());
            }
            // 介绍
            if (!StringUtils.isEmpty(spu.getIntroduction())) {
                criteria.andEqualTo("introduction", spu.getIntroduction());
            }
            // 规格列表
            if (!StringUtils.isEmpty(spu.getSpecItems())) {
                criteria.andEqualTo("specItems", spu.getSpecItems());
            }
            // 参数列表
            if (!StringUtils.isEmpty(spu.getParaItems())) {
                criteria.andEqualTo("paraItems", spu.getParaItems());
            }
            // 销量
            if (!StringUtils.isEmpty(spu.getSaleNum())) {
                criteria.andEqualTo("saleNum", spu.getSaleNum());
            }
            // 评论数
            if (!StringUtils.isEmpty(spu.getCommentNum())) {
                criteria.andEqualTo("commentNum", spu.getCommentNum());
            }
            // 是否上架
            if (!StringUtils.isEmpty(spu.getIsMarketable())) {
                criteria.andEqualTo("isMarketable", spu.getIsMarketable());
            }
            // 是否启用规格
            if (!StringUtils.isEmpty(spu.getIsEnableSpec())) {
                criteria.andEqualTo("isEnableSpec", spu.getIsEnableSpec());
            }
            // 是否删除
            if (!StringUtils.isEmpty(spu.getIsDelete())) {
                criteria.andEqualTo("isDelete", spu.getIsDelete());
            }
            // 审核状态
            if (!StringUtils.isEmpty(spu.getStatus())) {
                criteria.andEqualTo("status", spu.getStatus());
            }
        }
        return example;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu == null) {
            throw new RuntimeException("商品不存在");
        }
        if (!spu.getIsDelete().equals("1")) {
            throw new RuntimeException("必须先逻辑删除");
        }
        spuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Spu
     *
     * @param spu
     */
    @Override
    public void update(Spu spu) {
        spuMapper.updateByPrimaryKey(spu);
    }

    /**
     * 增加Spu
     *
     * @param spu
     */
    @Override
    public void add(Spu spu) {
        spuMapper.insert(spu);
    }

    /**
     * 根据ID查询Spu
     *
     * @param id
     * @return
     */
    @Override
    public Spu findById(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Spu全部数据
     *
     * @return
     */
    @Override
    public List<Spu> findAll() {
        return spuMapper.selectAll();
    }

    /**
     * 根据SPU的ID查询SPU以及对应的SKU集合
     *
     * @param id
     * @return
     */
    @Override
    public void saveGoods(Goods goods) {
        // 增加spu
        Spu spu = goods.getSpu();

        // 检查spu的id是否为空
        if (spu.getId() == null) {
            // 如果为空为增加操作
            spu.setId(idWorker.nextId());
            spuMapper.insertSelective(spu);
        } else {
            // 如果不为空为修改操作
            spuMapper.updateByPrimaryKeySelective(spu);
            // 删除spu的sku列表
            Sku sku = new Sku();
            sku.setSpuId(spu.getId());
            skuMapper.delete(sku);
        }

        // 增加sku
        Date date = new Date();
        Category category = categoryMapper.selectByPrimaryKey(spu.getCategory3Id());
        Brand brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        // 获取sku集合
        List<Sku> skuList = goods.getSkuList();
        // 循环组装sku的名字
        for (Sku sku : skuList) {
            // 构建sku名称，采用spu+规格值组装

            // 判空，防止空指针异常
            if (StringUtils.isEmpty(sku.getSpec())) {
                sku.setSpec("{}");
            }
            // 获取spu名称
            String name = spu.getName();
            // 将规格转换成Map
            Map<String, String> map = JSON.parseObject(sku.getSpec(), Map.class);
            // 循环组装sku名称;
            for (String value : map.values()) {
                name += " " + value;
            }
            /*for (Map.Entry<String, String> entry : map.entrySet()) {
                name += " " + entry.getValue();
            }*/
            sku.setName(name);
            // id
            sku.setId(idWorker.nextId());
            // spuId
            sku.setSpuId(spu.getId());
            // 创建时间
            sku.setCreateTime(date);
            // 修改时间
            sku.setUpdateTime(date);
            // 商品分类Id
            sku.setCategoryId(spu.getCategory3Id());
            // 分类名字
            sku.setCategoryName(category.getName());
            // 品牌名字
            sku.setBrandName(brand.getName());
            // 增加
            skuMapper.insertSelective(sku);
        }
    }

    /**
     * 根据SPUID查询Goods
     *
     * @param id
     * @return
     */
    @Override
    public Goods findGoodsById(Long id) {
        // 查询对应的spu
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 查询List<sku>
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = skuMapper.select(sku);
        // 封装Goods
        Goods goods = new Goods();
        goods.setSpu(spu);
        goods.setSkuList(skuList);
        return goods;
    }

    /**
     * 商品审核
     *
     * @param id
     */
    @Override
    public void auditSpu(Long id) {
        // 查询商品
        Spu spu = spuMapper.selectByPrimaryKey(id);

        // 判断商品是否已被删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已被删除！");
        }
        // 实现上架和审核
        spu.setStatus("1");
        spu.setIsMarketable("1");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 商品下架
     *
     * @param id
     */
    @Override
    public void pullSpu(Long id) {
        // 查询商品
        Spu spu = spuMapper.selectByPrimaryKey(id);

        // 判断商品是否已被删除
        if (spu.getIsDelete().equals("1")) {
            throw new RuntimeException("该商品已被删除！");
        }
        // 实现下架
        spu.setIsMarketable("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 批量上架
     *
     * @param ids
     * @return
     */
    @Override
    public int putMany(Long[] ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("1");
        // 批量修改条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        // 下架状态的
        criteria.andEqualTo("isMarketable", "0");
        // 审核通过的
        criteria.andEqualTo("status", "1");
        // 非删除的
        criteria.andEqualTo("isDelete", "0");
        // 批量修改
        return spuMapper.updateByExampleSelective(spu, example);
    }

    /**
     * 批量下架
     *
     * @param ids
     * @return
     */
    @Override
    public int pullMany(Long[] ids) {
        Spu spu = new Spu();
        spu.setIsMarketable("0");
        // 批量修改条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(ids));
        // 上架状态的
        criteria.andEqualTo("isMarketable", "1");
        // 批量修改
        return spuMapper.updateByExampleSelective(spu, example);
    }

    /**
     * 逻辑删除
     *
     * @param id
     */
    @Override
    public void logicDeleteSpu(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 检查商品是否已下架
        if (!spu.getIsMarketable().equals("0")) {
            throw new RuntimeException("必须先下架再删除！");
        }
        // 设置为已删除
        spu.setIsDelete("1");
        // 设置为未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 还原被删除商品
     *
     * @param id
     */
    @Override
    public void restoreSpu(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        // 判断是否已被删除
        if (!spu.getIsMarketable().equals("1")) {
            throw new RuntimeException("商品尚未被删除！");
        }
        // 设置为未删除
        spu.setIsDelete("0");
        // 设置为未审核
        spu.setStatus("0");
        spuMapper.updateByPrimaryKeySelective(spu);
    }


}
