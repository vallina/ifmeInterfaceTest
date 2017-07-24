package com.hulu.excel;

import com.mysql.cj.api.result.Row;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.*;
import java.util.regex.Pattern;

import static org.testng.CommandLineArgs.LOG;

/*
 * Created by yumi on 2017/3/7.
 */
public class ExcelUtils {
    //log
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
    /**
     * 读取excel工作簿信息.<br>
     * @param book Excel工作簿
     *
     */

    public void readExcel(Workbook book){
        try{
            int maxSheet = book.getNumberOfSheets();
            logger.info("Excel工作簿的sheet页总数：" + maxSheet + "页.");
            Sheet rs = book.getSheet(0);
            String sheetname = rs.getName(); //获取sheet名称
            int rsColumns = rs.getColumns(); //获取列数
            int rsRows = rs.getRows(); //获取行数

            System.out.println(sheetname);
            System.out.println("总列数" + rsColumns);
            System.out.println("总行数" + rsRows);

            int i = 0, j = 0;
            for (i = 2; i < rsRows; i++){
                for (j = 5; j< 7; j++){
                    System.out.println("第"+i+"行，第"+j+"列为："+ rs.getCell(j, i).getContents());
                }
            }

        }catch (IndexOutOfBoundsException e){
            logger.info("读取excel工作簿信息数组下标越界：" + e.getMessage(), e);
        }finally {
            book.close();
        }
    }



    /**
     * 读取excel指定sheet测试数据行数
     * @param excelPath Excel工作簿
     * @param  sheetName 表单名称
     */
    public static int readRows(String excelPath, String sheetName){
        File excel = new File(excelPath);
        Workbook book = null;
        try{
            book = Workbook.getWorkbook(excel);
            Sheet rs = book.getSheet(sheetName);
            int rsRows = rs.getRows(); //获取行数-标题行2行
            int rsNums = rsRows -2;

            System.out.println("rsRows is： "+ rsRows);

            return rsRows;

        }catch (BiffException e){
            logger.info("读取Excel工作簿文件二进制文件交换格式异常：" + e.getMessage(), e);
        }catch (IOException e){
            logger.info("读取Excel工作簿文件输入输出流异常：" + e.getMessage(), e);
        }
        return -1;



    }



    /**
     * 文件形式.<br>
     * 操作Excel工作簿.<br>
     * @param file Excel文件
     * */
    public void readExcelOfFile(File file){
        Workbook book = null;
        try {
            book = Workbook.getWorkbook(file);
            readExcel(book);
        } catch (BiffException e) {
            logger.info("读取Excel工作簿文件二进制文件交换格式异常：" + e.getMessage(), e);
        } catch (IOException e) {
            logger.info("读取Excel工作簿文件输入输出流异常：" + e.getMessage(), e);
        }
    }


    /**
     * 生成指定的excel副本
     */
    public static void createExcelCopy(){
        try {
            Date date = new Date();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String datestr = dateFormat.format(new Date());

            System.out.println(datestr);
           // String newName = "D:\\test2\\newTest1.xls";
            String newName = "D:\\test2\\APITest"+datestr+".xls";
            System.out.println(newName);

            InputStream fis = new FileInputStream("D:\\test2\\test.xls");
            //InputStream fis = new FileInputStream(new File("C:/test.xls"));
            Workbook wb = Workbook.getWorkbook(fis);

            WritableWorkbook newWb = Workbook.createWorkbook(new File(
                    newName), wb);
            System.out.println();
       //     newWb.importSheet("NewTest", 0, wb.getSheet(0));
            fis.close();
            wb.close();
            newWb.write();
            newWb.close();
        }catch (BiffException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (WriteException e) {e.printStackTrace();}
    }



    /**
     * 生成指定的excel副本
     * @param filePath 指定文件的絕對路徑
     */
    public static void createExcelCopy(String filePath){
        try {
            Date date = new Date();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            String datestr = dateFormat.format(new Date());

            System.out.println(datestr);
            //String fileType=filePath.substring(filePath.lastIndexOf('.')+1,filePath.length());
            String fileHead=filePath.substring(0,filePath.lastIndexOf('.'));
            String newName = fileHead + datestr +".xls";
            System.out.println(newName);

            //InputStream fis = new FileInputStream("D:\\test2\\test.xls");
            InputStream fis = new FileInputStream(filePath);

            Workbook wb = Workbook.getWorkbook(fis);

            WritableWorkbook newWb = Workbook.createWorkbook(new File(newName), wb);
            System.out.println();
            //     newWb.importSheet("NewTest", 0, wb.getSheet(0));
            fis.close();
            wb.close();
            newWb.write();
            newWb.close();
        }catch (BiffException e) {e.printStackTrace();}
        catch (IOException e) {e.printStackTrace();}
        catch (WriteException e) {e.printStackTrace();}
    }




    /**
     * 操作Excel工作簿读取测试数据转成Object[][]类型<br>
     * */
    public static Object[][] getTestData(File fileNameroot, String sheetName, int startNum, int paramNum) throws IOException{
        Workbook book = null;
        try{
            book = Workbook.getWorkbook(fileNameroot);
            Sheet rs = book.getSheet(sheetName);
            int rsRows = rs.getRows(); //获取行数-标题行2行
            int rsNums = rsRows -2;
            int rsColumns = paramNum; //实际要读取的参数列数

           // System.out.println(rsRows);

            Object[][] datas = new Object[rsNums][rsColumns+1];
            for (int i=2; i<rsRows; i++) {
                Object[] data = new Object[rsColumns+1];
                for(int j = startNum; j <startNum+paramNum ; j++) {
                    data[j-startNum] = rs.getCell(j, i).getContents();
                }
                data[data.length-1] = i;
                datas[i-2] = data;
            }

            System.out.println(Arrays.deepToString(datas));
            System.out.println("the length is: "+datas.length);
            book.close();
            return datas;

        }catch (BiffException e){
            logger.info("读取Excel工作簿文件二进制文件交换格式异常：" + e.getMessage(), e);
        }catch (IOException e){
            logger.info("读取Excel工作簿文件输入输出流异常：" + e.getMessage(), e);
        }
        return null;

    }


    /**
     * 回写指定工作表中指定单元格的内容
     * @param excelpath        修改的目的工作薄
     * @param sheetName        工作薄中工作表的名称
     * @param row            要修改的单元格在表中的行号
     * @param column        要修改的单元格在表中的行号
     * @param value            要写入改单元格的值
     * @return
     */
    public static void writeExcel(String excelpath,String sheetName,int row,int column,String value){
        try {
                Workbook wb =null; //创建一个workbook对象
            try {
                    InputStream is = new FileInputStream(excelpath); //创建一个文件流，读入Excel文件
                    wb = Workbook.getWorkbook(is); //将文件流写入到workbook对象
                } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                WritableWorkbook wbe= Workbook.createWorkbook(new File(excelpath), wb);

                WritableSheet sheet = wbe.getSheet(sheetName); //获取指定sheet

                System.out.println("sheetName : "+ sheet.getName());

                WritableCell cell =sheet.getWritableCell(column,row);//获取单元格

               // System.out.println("cellContent:" + cell.getContents());

                jxl.format.CellFormat cf = cell.getCellFormat();//获取指定单元格的格式
                Label lbl = new Label(column, row, value);//将指定单元格的值改为“修改後的值”
                lbl.setCellFormat(cf);//将修改后的单元格的格式设定成跟原来一样

                sheet.addCell(lbl);//将改过的单元格保存到sheet

                wbe.write();
                wbe.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }



    public static void writeExcel(String excelpath,String sheetName, List<ExcelParam> paramList){
        try {
            Workbook wb =null; //创建一个workbook对象
            try {
                InputStream is = new FileInputStream(excelpath); //创建一个文件流，读入Excel文件
                wb = Workbook.getWorkbook(is); //将文件流写入到workbook对象
            } catch (BiffException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WritableWorkbook wbe= Workbook.createWorkbook(new File(excelpath), wb);

            WritableSheet sheet = wbe.getSheet(sheetName); //获取指定sheet

            System.out.println("sheetName : "+ sheet.getName());

            for (ExcelParam param : paramList) {
                WritableCell cell =sheet.getWritableCell(param.getColumn(), param.getRow());//获取单元格

                // System.out.println("cellContent:" + cell.getContents());

                jxl.format.CellFormat cf = cell.getCellFormat();//获取指定单元格的格式
                Label lbl = new Label(param.getColumn(), param.getRow(), param.getValue());//将指定单元格的值改为“修改後的值”
                lbl.setCellFormat(cf);//将修改后的单元格的格式设定成跟原来一样

                sheet.addCell(lbl);//将改过的单元格保存到sheet
            }

            wbe.write();
            wbe.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }




    /*
    創建新的測試數據文件
     */
    public void createNewDataExcel(){





    }



    public static void main(String[] args) throws IOException {
        //ExcelUtils jxlrw1 = new ExcelUtils();
        String excelPath = "D:/2222.xls";
        String sn = "personalpage";
        int start = 5;
        int paraNum = 3;

        File excel = new File(excelPath);

        String path = "D:\\test3\\test.xls";

        //jxlrw1.readExcelOfFile(excel);
        // getTestData(excel,sn,start,paraNum);
       // createExcelCopy();
        createExcelCopy(path);

        /*
        try {
            int num = ExcelUtils.readRows("D:/2222.xls", "personalpage");
            System.out.println("num is: " + num);

            for(int i = 2; i < num; i++) {
                writeExcel(excelPath, sn, i, 8, "123");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */



    }

}
