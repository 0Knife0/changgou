package com.changgou.service.impl;

import com.changgou.dao.AlbumMapper;
import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Album;
import com.changgou.service.AlbumService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    /**
     * Album多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(Album album, int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 构建查询对象
        Example example = createExample(album);
        return new PageInfo<>(albumMapper.selectByExample(example));
    }

    /**
     * Album分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Album> findPage(int page, int size) {
        // 静态分页
        PageHelper.startPage(page, size);
        // 分页查询
        return new PageInfo<>(albumMapper.selectAll());
    }

    /**
     * Album多条件搜索方法
     *
     * @param album
     * @return
     */
    @Override
    public List<Album> findList(Album album) {
        Example example = createExample(album);
        return albumMapper.selectByExample(example);
    }

    /**
     * 删除Album
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Album
     *
     * @param album
     */
    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKey(album);
    }

    /**
     * 增加Album
     *
     * @param album
     */
    @Override
    public void add(Album album) {
        albumMapper.insert(album);
    }

    /**
     * 通过id查询Album
     *
     * @param id
     * @return
     */
    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有Album
     *
     * @return
     */
    @Override
    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    /**
     * 构建查询对象
     *
     * @param album
     * @return
     */
    public Example createExample(Album album) {
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();

        if (album != null) {
            // 编号
            if (!StringUtils.isEmpty(album.getId())) {
                criteria.andEqualTo("id", album.getId());
            }
            // 相册名称
            if (!StringUtils.isEmpty(album.getTitle())) {
                criteria.andLike("title", "%" + album.getTitle() + "%");
            }
            // 相册封面
            if (!StringUtils.isEmpty(album.getImage())) {
                criteria.andEqualTo("image", album.getImage());
            }
            // 图片列表
            if (!StringUtils.isEmpty(album.getImageItems())) {
                criteria.andEqualTo("imageItems", album.getImageItems());
            }
        }
        return example;
    }
}
