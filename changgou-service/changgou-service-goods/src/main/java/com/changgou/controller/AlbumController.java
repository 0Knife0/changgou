package com.changgou.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.service.AlbumService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/album")
@CrossOrigin
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    /**
     * Album多条件分页查询
     *
     * @param album
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Album>> findPage(@RequestBody Album album,
                                            @PathVariable int page,
                                            @PathVariable int size) {
        PageInfo<Album> pageInfo = albumService.findPage(album, page, size);
        return new Result<>(true, StatusCode.OK, "多条件分页查询成功", pageInfo);
    }

    /**
     * Album分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Album>> findPage(@PathVariable int page,
                                            @PathVariable int size) {
        PageInfo<Album> pageInfo = albumService.findPage(page, size);
        return new Result<>(true, StatusCode.OK, "分页查询成功", pageInfo);
    }

    /**
     * Album多条件搜索方法
     *
     * @param album
     * @return
     */
    @PostMapping(value = "/search")
    public Result<List<Album>> findList(@RequestBody Album album) {
        List<Album> list = albumService.findList(album);
        return new Result<>(true, StatusCode.OK, "条件查询成功", list);
    }

    /**
     * 删除Album
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        albumService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 修改Album
     *
     * @param id
     * @param album
     * @return
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id,
                         @RequestBody Album album) {
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    /**
     * 新增Album
     *
     * @param album
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Album album) {
        albumService.add(album);
        return new Result(true, StatusCode.OK, "新增成功");
    }

    /**
     * 通过id查询Album
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Album> findById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return new Result<>(true, StatusCode.OK, "通过id查询成功", album);
    }

    /**
     * 查询所有Album
     *
     * @return
     */
    @GetMapping
    public Result<List<Album>> findAll() {
        List<Album> albums = albumService.findAll();
        return new Result<>(true, StatusCode.OK, "查询所有Album成功", albums);
    }
}
