package com.changgou.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
@RequestMapping("/template")
@CrossOrigin
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @PostMapping("/search/{page}/{size}")
    public Result<PageInfo<Template>> findPage(@RequestBody Template template,
                                               @PathVariable int page,
                                               @PathVariable int size) {
        PageInfo<Template> pageInfo = templateService.findPage(template, page, size);
        return new Result<>(true, StatusCode.OK, "条件分页查询成功", pageInfo);
    }

    @GetMapping("/search/{page}/{size}")
    public Result<PageInfo<Template>> findPage(@PathVariable int page,
                                               @PathVariable int size) {
        PageInfo<Template> pageInfo = templateService.findPage(page, size);
        return new Result<>(true, StatusCode.OK, "分页查询成功", pageInfo);
    }

    @PostMapping("/search")
    public Result<List<Template>> findList(@RequestBody Template template) {
        List<Template> list = templateService.findList(template);
        return new Result<>(true, StatusCode.OK, "条件查询成功", list);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        templateService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @PutMapping
    public Result update(@RequestBody Template template) {
        templateService.update(template);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @PostMapping
    public Result add(@RequestBody Template template) {
        templateService.add(template);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @GetMapping("/{id}")
    public Result<Template> findById(@PathVariable Integer id) {
        Template template = templateService.findById(id);
        return new Result<>(true, StatusCode.OK, "查询成功", template);
    }

    @GetMapping
    public Result<List<Template>> findAll() {
        List<Template> list = templateService.findAll();
        return new Result<>(true, StatusCode.OK, "查询全部成功", list);
    }
}
