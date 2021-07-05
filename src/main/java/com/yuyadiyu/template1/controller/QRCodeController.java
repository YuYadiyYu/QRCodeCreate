package com.yuyadiyu.template1.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuyadiyu.template1.dao.QRCodeMapper;
//import com.yuyadiyu.template1.service.QRCodeRecordService;
import com.yuyadiyu.template1.util.QRCodeUtil;
import com.yuyadiyu.template1.vo.QRRecord;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yuyadiyu.template1.util.FileChargeUtil.*;


@Controller
public class QRCodeController {
//    @Autowired
//    private QRCodeRecordService qrCodeRecordService;

    @Resource
    private QRCodeMapper qrCodeMapper;

    @Value("${localURL}")
    private String URL;
    /**
     * 根据 url 生成 带有logo二维码
     * @return
     */
    @ApiOperation(value="生成二维码",notes = "根据url生成带有logo的二维码 \n并保存传入二维码名称\n" +
            "请求方式为get，url为二维码；name为二维码名称，logopath为logo路径")
    @GetMapping(value = "/createLogoQRCode",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] createLogoQRCode(String url, String name) throws Exception {
        String logoPath = URL + "logo.jpg";
        String localImagePath = URL + name+".jpg";
        OutputStream stream2 = new FileOutputStream(localImagePath);
        QRRecord qr = new QRRecord();
        byte[] datas = null;
        //生成本地二维码
        QRCodeUtil.encode(url, logoPath, stream2, true);
        datas = fileToByteArray(localImagePath);
        //存表
        qr.setQrCodeName(name);
        qr.setImageURL(localImagePath);
        qrCodeMapper.insert(qr);
        if (stream2 != null) {
            stream2.flush();
            stream2.close();
        }
        return datas;
    }
    @ApiOperation(value="生成二维码",notes = "根据url生成带有logo的二维码 \n并保存传入二维码名称\n" +
            "请求方式为get，url为二维码；name为二维码名称，logopath为logo路径")
    @GetMapping(value = "/getQRCode",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getQRCode(String name) throws Exception {
        QRRecord qrRecord= qrCodeMapper.selectOne(new QueryWrapper<QRRecord>().eq("qrCodeName",name));
        byte[] datas = fileToByteArray(qrRecord.getImageURL());
        return datas;
    }
}
