package com.yuyadiyu.template1.controller;


import com.yuyadiyu.template1.dao.QRCodeMapper;
//import com.yuyadiyu.template1.service.QRCodeRecordService;
import com.yuyadiyu.template1.util.QRCodeUtil;
import com.yuyadiyu.template1.vo.QRRecord;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.yuyadiyu.template1.util.FileChargeUtil.*;


@Controller
public class QRCodeController {
//    @Autowired
//    private QRCodeRecordService qrCodeRecordService;

    @Resource
    private QRCodeMapper qrCodeMapper;
    /**
     * 根据 url 生成 普通二维码
     */
    @RequestMapping(value = "/createCommonQRCode")
    public void createCommonQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QRCodeUtil.encode(url, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 根据 url 生成 带有logo二维码
     * @return
     */
    @GetMapping(value = "/createLogoQRCode",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] createLogoQRCode(String url, String name,String logoPath) throws Exception {
        String localImagePath = "E:/idea_create_file/QRCode/"+name+".jpg";
        OutputStream stream2 = new FileOutputStream(localImagePath);
        byte[] datas = null;
        QRRecord qr = new QRRecord();
        try {
            //生成本地二维码
            QRCodeUtil.encode(url, logoPath, stream2, true);
            //转换为byte输入流
            File file = new File(localImagePath);
            FileInputStream fileins = new FileInputStream(file);
            datas = fileToByteArray(localImagePath);
            fileins.read(datas, 0, fileins.available());
            //存表
            qr.setQrCodeName(name);
            qr.setImageDatas(datas);
            qrCodeMapper.insert(qr);
            fileins.close();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream2 != null) {
                stream2.flush();
                stream2.close();
            }
        }
        return datas;
    }

    //查询id为1的
    @GetMapping(value="selectQRCode",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] selectQRCode(int id) throws IOException {
        QRRecord qr = qrCodeMapper.selectById(id);
        String loImagePath = "E:/idea_create_file/QRCode/"+qr.getQrCodeName()+".jpg";
        byteArrayToFile(qr.getImageDatas(), loImagePath);
        InputStream fileins = new FileInputStream(loImagePath);
        byte[] datas = fileToByteArray(loImagePath);
        fileins.read(datas, 0, fileins.available());
        fileins.close();
        return datas;
    }
}
