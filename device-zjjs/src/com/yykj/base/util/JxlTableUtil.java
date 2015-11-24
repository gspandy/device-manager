package com.yykj.base.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


/**
 * 文件导出
 * @author QinShuJin
 *
 */
public class JxlTableUtil {
	private final static JxlTableUtil jxlTable = new JxlTableUtil();

	public static JxlTableUtil getInstance() {
		return jxlTable;
	}

	public JxlTableUtil() {
	}

	public boolean createTable(String header, List<String> bodys, String filePath) {
		boolean createFlag = true;
		WritableWorkbook book;
		try {
			// 根据路径生成excel文件
			book = Workbook.createWorkbook(new File(filePath));
			// 创建一个sheet名为"表格"
			WritableSheet sheet = book.createSheet("表格", 0);
			// 设置NO列宽度
			sheet.setColumnView(1, 5);
			// 去掉整个sheet中的网格线
			sheet.getSettings().setShowGridLines(false);
			Label tempLabel = null;
			// 表头输出
			String[] headerArr = header.split(",");
			int headerLen = headerArr.length;
			// 循环写入表头内容
			for (int i = 0; i < headerLen; i++) {
				tempLabel = new Label(i, 0, headerArr[i],getHeaderCellStyle());
				sheet.addCell(tempLabel);
			}
			// 表体输出
			int bodyLen = bodys.size();
			// 循环写入表体内容
			for (int i = 0; i < bodys.size(); i++) {
				String body = bodys.get(i);
				String[] bodyTempArr = body.split(",");
				for (int k = 0; k < bodyTempArr.length; k++) {
					WritableCellFormat tempCellFormat = null;
					tempCellFormat = getBodyCellStyle();
					if (tempCellFormat != null) {
						if (k == 0 || k == (bodyTempArr.length - 1)) {
							tempCellFormat.setAlignment(Alignment.CENTRE);
						}
					}
					tempLabel = new Label(k, 1 + i, bodyTempArr[k],tempCellFormat);
					sheet.addCell(tempLabel);
				}
			}
			book.write();
			book.close();
		} catch (IOException e) {
			createFlag = false;
			System.out.println("EXCEL创建失败！");
			e.printStackTrace();
		} catch (RowsExceededException e) {
			createFlag = false;
			System.out.println("EXCEL单元设置创建失败！");
			e.printStackTrace();
		} catch (WriteException e) {
			createFlag = false;
			System.out.println("EXCEL写入失败！");
			e.printStackTrace();
		}
		return createFlag;
	}

	public WritableCellFormat getHeaderCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat headerFormat = new WritableCellFormat(
				NumberFormats.TEXT);
		try {
			// 添加字体设置
			headerFormat.setFont(font);
			// 设置单元格背景色：表头为黄色
			headerFormat.setBackground(Colour.YELLOW);
			// 设置表头表格边框样式
			// 整个表格线为粗线、黑色
			headerFormat.setBorder(Border.ALL, BorderLineStyle.THICK,Colour.BLACK);
			// 表头内容水平居中显示
			headerFormat.setAlignment(Alignment.CENTRE);
		} catch (WriteException e) {
			System.out.println("表头单元格样式设置失败！");
		}
		return headerFormat;
	}

	public WritableCellFormat getBodyCellStyle() {
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}

	public static void main(String[] args) {
		String header = "NO,姓名,性别,年龄";
		List<String> body = new ArrayList<String>();
		body.add("1,欧阳锋,男,68");
		body.add("2,黄药师,男,67");
		body.add("3,洪七公,男,70");
		body.add("4,郭靖,男,32");
		String filePath = "e:/test.xls";
		JxlTableUtil testJxl = JxlTableUtil.getInstance();
		boolean flag = testJxl.createTable(header, body, filePath);
		if (flag) {
			System.out.println("表格创建成功！！");
		}
	}
}