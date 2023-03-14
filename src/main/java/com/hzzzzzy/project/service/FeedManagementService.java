package com.hzzzzzy.project.service;

import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hzzzzzy.project.model.dto.feed.FeedAddRequest;
import com.hzzzzzy.project.model.dto.feed.FeedDeleteRequest;
import com.hzzzzzy.project.model.dto.feed.FeedUpdateRequest;
import com.hzzzzzy.project.model.entity.FeedManagement;
import com.hzzzzzy.project.model.vo.FeedGetOneVO;
import com.hzzzzzy.project.model.vo.FeedVO;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author hzzzzzy
* @description 针对表【feed_management(饲料管理)】的数据库操作Service
* @createDate 2023-03-10 11:15:21
*/
public interface FeedManagementService extends IService<FeedManagement> {

    /**
     * 饲料管理员创建饲料信息
     *
     * @param feedAddRequest
     * @param request
     * @return
     */
    long add(FeedAddRequest feedAddRequest, HttpServletRequest request);


    /**
     * 导出excel
     *
     * @param response
     * @param sheetBuilder
     * @return
     */
    boolean export(HttpServletResponse response, ExcelWriterSheetBuilder sheetBuilder);


    /**
     * 肉猪信息管理员更新肉猪信息
     *
     * @param feedUpdateRequest
     * @param request
     * @return
     */
    boolean update(FeedUpdateRequest feedUpdateRequest, HttpServletRequest request);


    /**
     * 饲料信息管理员 删除 饲料信息
     *
     * @param feedDeleteRequest
     * @param request
     * @return
     */
    boolean delete(FeedDeleteRequest feedDeleteRequest, HttpServletRequest request);


    /**
     * 通过饲料id获取详细信息
     *
     * @param id
     */
    FeedGetOneVO getDetailById(int id);

    /**
     * 分页获取所有饲料的粗略信息
     *
     * @param current
     * @param size
     * @return
     */
    Page<FeedVO> getAll(long current, long size);

}
