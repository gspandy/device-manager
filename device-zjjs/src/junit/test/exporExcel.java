package junit.test;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import jxl.SheetSettings;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class exporExcel {
	/**
     * 导出导出采暖市场部收入、成本、利润明细表
     */
    /*public String exporExcel_(String str) {
            String path = str + "收入、成本、利润明细表.xls";
        try {
            List<WholesaleAgreementMaterialExportExcelTemplate> list = agreementDao.selectAllWholeDetails();
//          打开文件
            WritableWorkbook book = Workbook.createWorkbook( new File(path));
//          生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet( " 第一页 " , 0 );
            SheetSettings ss = sheet.getSettings();
            // ss.setHorizontalFreeze(2);  // 设置列冻结
            ss.setVerticalFreeze(2);  // 设置行冻结前2行
 
            WritableFont font1 =new WritableFont(WritableFont.createFont("微软雅黑"), 10 ,WritableFont.BOLD);
            WritableFont font2 =new WritableFont(WritableFont.createFont("微软雅黑"), 9 ,WritableFont.NO_BOLD);
            WritableCellFormat wcf = new WritableCellFormat(font1);
            WritableCellFormat wcf2 = new WritableCellFormat(font2);
            WritableCellFormat wcf3 = new WritableCellFormat(font2);//设置样式，字体
             
            // wcf2.setBackground(Colour.LIGHT_ORANGE);
            wcf.setAlignment(Alignment.CENTRE);  //平行居中
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setAlignment(Alignment.CENTRE);  //平行居中
            wcf3.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
            wcf3.setBackground(Colour.LIGHT_ORANGE);
            wcf2.setAlignment(Alignment.CENTRE);  //平行居中
            wcf2.setVerticalAlignment(VerticalAlignment.CENTRE);  //垂直居中
 
            sheet.mergeCells( 1 , 0 , 13 , 0 ); // 合并单元格  
 
//          在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
//          以及单元格内容为test
            Label titleLabel = new Label( 1 , 0 , " 采暖市场部收入、成本、利润明细表 ",wcf);
//          将定义好的单元格添加到工作表中
            sheet.addCell(titleLabel);
            sheet.setRowView(1, 500); // 设置第一行的高度  20121111
            int[] headerArrHight = {13,10,30,20,20,25,7,10,15,20,13,15,15,30};
            String headerArr[] = {"年份","月份","经销商","合同号","产品","规格","数量","单价(元)","收款金额(元)","收款不含税价(元)","成本(元)","毛利(元)","毛利率","备注"};
            for (int i = 0; i < headerArr.length; i++) {
                sheet.addCell(new Label( i , 1 , headerArr[i],wcf));
                sheet.setColumnView(i, headerArrHight[i]);
            }           
            DecimalFormat df = new DecimalFormat("#.00");
            int conut = 2;
            for (int i = 0; i < list.size(); i++) {   //循环一个list里面的数据到excel中
                sheet.addCell(new Label( 0 , conut ,list.get(i).getFromDate().substring(0, 4) + "年" ,wcf2));
                sheet.addCell(new Label( 1 , conut ,list.get(i).getFromDate().substring(4, 6) + "月" ,wcf2));
                sheet.addCell(new Label( 2 , conut ,list.get(i).getCustomerName() ,wcf2));
                sheet.addCell(new Label( 3 , conut ,list.get(i).getAgreementCode() ,wcf2));
                sheet.addCell(new Label( 4 , conut ,list.get(i).getBrandName() ,wcf2));
                sheet.addCell(new Label( 5 , conut ,list.get(i).getType() ,wcf2));
                sheet.addCell(new Label( 6 , conut ,list.get(i).getQuantity().substring(0, list.get(i).getQuantity().indexOf(".")) ,wcf2));
                sheet.addCell(new Label( 7 , conut ,list.get(i).getUnivalent() ,wcf2));
                sheet.addCell(new Label( 8 , conut ,list.get(i).getReceiptAmount()+"" ,wcf2));// 收款金额
                sheet.addCell(new Label( 9 , conut ,df.format(list.get(i).getReceiptNoTax()) ,wcf2));// 收款不含税价
                sheet.addCell(new Label( 10 , conut ,list.get(i).getCost()+"" ,wcf2));//成本
                sheet.addCell(new Label( 11 , conut ,df.format(list.get(i).getReceiptNoTax().add(list.get(i).getCost().multiply(new BigDecimal(-1)))) ,wcf2));// 毛利
                BigDecimal bigDecimal = list.get(i).getReceiptNoTax().add(list.get(i).getCost().multiply(new BigDecimal(-1)));
                double bigDecimal2 = bigDecimal.doubleValue()/list.get(i).getReceiptNoTax().doubleValue();
                sheet.addCell(new Label( 12 , conut ,(df.format(bigDecimal2*100)) + "%" ,wcf2));
                sheet.addCell(new Label( 13 , conut ,list.get(i).getRemark() ,wcf2));
                sheet.setRowView(conut, 370); // 设置第一行的高度
                conut++;
            }
            sheet.setRowView(list.size() + 2, 370);
            sheet.setRowView(list.size() + 3, 370);
            sheet.setRowView(list.size() + 4, 370);
            sheet.setRowView(list.size() + 5, 370);
             
            double sumZ = 0.00; //收款总额
            double sumT = 0.00; // 收款不含税价
            double sumC = 0.00; // 成本
            double sumM = 0.00; // 毛利
            for (int i = 0; i < list.size(); i++) {
                sumZ += list.get(i).getReceiptAmount().doubleValue();
                sumT += list.get(i).getReceiptNoTax().doubleValue();
                sumC += list.get(i).getCost().doubleValue();
                sumM += list.get(i).getReceiptNoTax().add(list.get(i).getCost().multiply(new BigDecimal(-1))).doubleValue();
            }
            sheet.addCell(new Label( 0 , list.size() + 3 ,"合计：" ,wcf));
            sheet.addCell(new Label( 8 , list.size() + 3 ,df.format(sumZ) ,wcf));
            sheet.addCell(new Label( 9 , list.size() + 3 ,df.format(sumT) ,wcf));
            sheet.addCell(new Label( 10 , list.size() + 3, df.format(sumC) ,wcf));
            sheet.addCell(new Label( 11 , list.size() + 3 ,df.format(sumM) ,wcf));
            sheet.addCell(new Label( 13 , list.size() + 5 ,"导出时间：" + new Date().toLocaleString() ,wcf3));
             
//          写入数据并关闭文件
            book.write();
            book.close();
            return path;
        } catch (Exception e) {
        }
        return path;
    }*/
}
