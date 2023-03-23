package com.hzzzzzy.project.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.gson.Gson;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


/**
 * 导出Excel封装的工具类
 *
 * @author hzzzzzy
 */
public class ExcelUtils {
    @SneakyThrows
    public static <R> ExcelWriterSheetBuilder export(HttpServletResponse response, Class<R> r){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = UUID.randomUUID().toString();
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //头部背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        try {
            ExcelWriterSheetBuilder sheetBuilder = EasyExcel.write(response.getOutputStream(), r).sheet("sheet").registerWriteHandler(horizontalCellStyleStrategy);
            return sheetBuilder;
        } catch (IOException e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            Gson gson = new Gson();
            response.getWriter().println(gson.toJson(map));
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
