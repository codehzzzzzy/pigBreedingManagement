package com.hzzzzzy.project.service;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.hogring.HogringAddRequest;
import com.hzzzzzy.project.model.dto.hogring.HogringDeleteRequest;
import com.hzzzzzy.project.model.dto.hogring.HogringUpdateRequest;
import com.hzzzzzy.project.model.dto.hogring.PigIdListGetRequest;
import com.hzzzzzy.project.model.entity.Hogring;
import com.hzzzzzy.project.model.vo.HoringVO;
import com.hzzzzzy.project.model.vo.PigInHoringVO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author hzzzzzy
* @description 针对表【hogring(猪舍)】的数据库操作Service
* @createDate 2023-03-10 11:15:21
*/
public interface HogringService extends IService<Hogring> {

    /**
     * 猪舍管理员 创建 新猪舍
     *
     * @param hogringAddRequest
     * @param request
     * @return
     */
    long add(HogringAddRequest hogringAddRequest, HttpServletRequest request);


    /**
     * 导出excel
     *
     * @param response
     * @param sheetBuilder
     * @return
     */
    boolean export(HttpServletResponse response, ExcelWriterSheetBuilder sheetBuilder);


    /**
     * 猪舍信息管理员 更新 猪舍信息
     *
     * @param hogringUpdateRequest
     * @param request
     * @return
     */
    boolean update(HogringUpdateRequest hogringUpdateRequest, HttpServletRequest request);


    /**
     * 猪舍信息管理员 删除 猪舍信息
     *
     * @param hogringDeleteRequest
     * @param request
     * @return
     */
    boolean delete(HogringDeleteRequest hogringDeleteRequest, HttpServletRequest request);



    /**
     * 分页获取所有猪舍信息
     *
     * @param current
     * @param size
     */
    Page<HoringVO> getAll(long current, long size);
}
