// 代码生成时间: 2025-09-18 15:24:33
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelGenerator {

    // 创建Excel工作簿
    private Workbook workbook;

    // 构造函数，初始化Workbook
    public ExcelGenerator() {
        workbook = new XSSFWorkbook();
    }

    /**
     * 创建新的Excel工作表
     *
     * @param sheetName 工作表名称
     */
    public void createSheet(String sheetName) {
        if (workbook.getSheet(sheetName) == null) {
            workbook.createSheet(sheetName);
        }
    }

    /**
     * 向指定工作表添加数据
     *
     * @param sheetName 工作表名称
     * @param data      要添加的数据，以二维数组形式表示
     */
    public void addDataToSheet(String sheetName, Object[][] data) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new IllegalArgumentException(