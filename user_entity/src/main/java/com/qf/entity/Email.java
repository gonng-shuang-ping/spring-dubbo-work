package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email implements Serializable {
   //收件人
   private String recipients;
   //发件人
   private String addresser;
   //发送时间
   private Date sendtime;
   //发送内容
   private String content;
   //标题
   private String headline;
}
