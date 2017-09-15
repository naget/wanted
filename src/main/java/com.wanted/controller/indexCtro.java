package com.wanted.controller;

import com.wanted.model.Freshmen;
import com.wanted.repository.FreshmenRepository;
import jxl.Workbook;
import jxl.write.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.sql.Date;
import java.util.List;


/**
 * Created by t on 2017/3/1.
 */
@Controller
public class indexCtro {
    @Autowired
    FreshmenRepository freshmenRepository;
    @Autowired
    JavaMailSender mailSender;
    @RequestMapping("/want")
    public String index(){
        return "zhaoxin";
    }
    @RequestMapping("/join")
//    @ResponseBody
    public String join(@RequestParam(name = "name") String name, @RequestParam(name = "tel")String tel,@RequestParam(name = "profession")String profession,
                       @RequestParam(name = "sex")String sex,@RequestParam(name = "text")String text,
                       @RequestParam(name = "email")String email) {
        Freshmen freshmen=new Freshmen(email,name,tel,profession,text,sex,new Date(System.currentTimeMillis()));
//        System.out.println("name:"+name+"tel:"+tel+"profession:"+profession+"sex:"+sex+"text:"+text);
        Freshmen freshmen1=freshmenRepository.findByName(name);



        if (freshmen1==null){
            freshmenRepository.save(freshmen);
            try {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom("1257825993@qq.com");
                helper.setTo(email);
                helper.setSubject("报名成功！");
                StringBuffer sb=new StringBuffer();
               sb.append("<p>你好</p>").append(name+"!").append("恭喜你成功报名翼灵物联网工作室招新！");

                helper.setText(sb.toString(), true);
                mailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return "redirect:/success";

        }else {
            return "redirect:/rejoin";
        }

    }
    @RequestMapping("/success")
    public String success(){
        return "index";
    }
    @RequestMapping("/rejoin")
    public String rejoin(){
        return "rejoin";
    }
    @RequestMapping("print")
    @ResponseBody
    public String print(){
        try {
            WritableWorkbook wwb = null;

            // 创建可写入的Excel工作簿
            String fileName = "D://book.xls";
            File file=new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //以fileName为文件名来创建一个Workbook
            wwb = Workbook.createWorkbook(file);

            // 创建工作表
            WritableSheet ws = wwb.createSheet("Test Shee 1", 0);
            WritableFont font1 = new WritableFont(WritableFont.TIMES,10, WritableFont.NO_BOLD);
            WritableCellFormat format1=new WritableCellFormat(font1);
            //查询数据库中所有的数据
            List<Freshmen>list= freshmenRepository.findAll();
            //要插入到的Excel表格的行号，默认从0开始
            Label labelId= new Label(0, 0, "编号",format1);//表示第
            Label labelName= new Label(1, 0, "姓名",format1);
            Label labelSex= new Label(2, 0, "性别",format1);
            Label labelProfession= new Label(3, 0, "专业",format1);
            Label labelTime=new Label(4,0,"报名时间",format1);
            Label labelTel= new Label(5, 0, "电话",format1);
            Label labelEmail= new Label(6, 0, "邮箱",format1);
            Label labelText= new Label(7, 0, "备注",format1);

            ws.addCell(labelId);
            ws.addCell(labelName);
            ws.addCell(labelSex);
            ws.addCell(labelProfession);
            ws.addCell(labelTime);
            ws.addCell(labelTel);
            ws.addCell(labelText);
            ws.addCell(labelEmail);

            for (int i = 0; i < list.size(); i++) {

                Label labelId_i= new Label(0, i+1, list.get(i).getId()+"",format1);
                Label labelName_i= new Label(1, i+1, list.get(i).getName()+"",format1);
                Label labelSex_i= new Label(2, i+1, list.get(i).getSex()+"",format1);
                Label labelProfession_i= new Label(3, i+1, list.get(i).getProfession()+"",format1);
                Label labelTime_i=new Label(4,i+1,list.get(i).getTime()+"",format1);
                Label labelTel_i=new Label(5,i+1,list.get(i).getTel()+"",format1);
                Label labelEmail_i=new Label(6,i+1,list.get(i).getEmail()+"",format1);
                Label labelText_i=new Label(7,i+1,list.get(i).getText()+"",format1);
                ws.addCell(labelId_i);
                ws.addCell(labelName_i);
                ws.addCell(labelSex_i);
                ws.addCell(labelProfession_i);
                ws.addCell(labelTime_i);
                ws.addCell(labelTel_i);
                ws.addCell(labelText_i);
                ws.addCell(labelEmail_i);
            }

            //写进文档
            wwb.write();
            // 关闭Excel工作簿对象
            System.out.println("数据导出成功!");
            wwb.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "已下载到D盘根目录";
    }

}
