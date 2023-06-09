package com.hzzzzzy.project.service;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.pig.PigAddRequest;
import com.hzzzzzy.project.model.dto.pig.PigDeleteRequest;
import com.hzzzzzy.project.model.dto.pig.PigUpdateRequest;
import com.hzzzzzy.project.model.entity.Pig;
import com.hzzzzzy.project.model.vo.PigDetailVO;
import com.hzzzzzy.project.model.vo.PigVO;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
* @author hzzzzzy
* @description 针对表【pig(猪只信息)】的数据库操作Service
* @createDate 2023-03-10 11:15:21
*/
public interface PigService extends IService<Pig> {

    /**
     * 肉猪信息管理员更新肉猪信息
     *
     * @param pigUpdateRequest
     * @param request
     * @return
     */
    boolean update(PigUpdateRequest pigUpdateRequest, HttpServletRequest request);

    /**
     * 肉猪信息管理员创建肉猪信息
     *
     * @param pigAddRequest
     * @param request
     * @return
     */
    long add(PigAddRequest pigAddRequest, HttpServletRequest request);

    /**
     * 多条件动态查询
     *
     * @param breed
     * @param age
     * @param gender
     * @param health
     * @param status
     * @param weight_pre 体重起始值
     * @param weight_suf 体重结束值
     * @param feedType
     * @return
     */
    List<PigVO> searchBy(String breed, Integer age, Integer gender, Integer health, Integer status, BigDecimal weight_pre,BigDecimal weight_suf,String feedType);


    /**
     * 关联查询肉猪和所在猪舍信息
     *
     * @param id
     * @return
     */
    PigDetailVO getDetailById(int id);


    /**
     * 导出excel
     *
     * @param response
     */
    boolean export(HttpServletResponse response, ExcelWriterSheetBuilder sheetBuilder);


    /**
     * 肉猪信息管理员 删除 肉猪信息
     *
     * @param pigDeleteRequest
     * @param request
     * @return
     */
    boolean delete(PigDeleteRequest pigDeleteRequest, HttpServletRequest request);


    /**
     * 分页获取所有肉猪详细信息列表
     *
     * @return
     */
    Page<PigVO> getAll(long current, long size);
}
