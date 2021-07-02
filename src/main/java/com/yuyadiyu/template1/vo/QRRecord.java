package com.yuyadiyu.template1.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("qrcode_record")
@Data
public class QRRecord {
    private int id;
    @TableField("qrCodeName")
    private String qrCodeName;
    @TableField("imageDatas")
    private byte[] imageDatas;

}
