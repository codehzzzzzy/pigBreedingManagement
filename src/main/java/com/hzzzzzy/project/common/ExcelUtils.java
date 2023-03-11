package com.hzzzzzy.project.common;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.hzzzzzy.project.exception.BusinessException;
import com.hzzzzzy.project.model.dto.pig.PigExcel;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;


/**
 * 导出Excel封装的工具类
 *
 * @author hzzzzzy
 */
public class ExcelUtils {
    public static ExcelWriterSheetBuilder export(HttpServletResponse response){
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
            ExcelWriterSheetBuilder sheetBuilder = EasyExcel.write(response.getOutputStream(), PigExcel.class).sheet("sheet").registerWriteHandler(horizontalCellStyleStrategy);
            return sheetBuilder;
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
