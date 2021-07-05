package com.yuyadiyu.template1.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@TableName("qrcode_record")
@Data
public class QRRecord {
    private int id;
    @ApiModelProperty(value = "名称",required = true)
    @TableField("qrCodeName")
    private String qrCodeName;
    @ApiModelProperty(value = "二维码生成地址")
    @TableField("imageURL")
    private String imageURL;

}
