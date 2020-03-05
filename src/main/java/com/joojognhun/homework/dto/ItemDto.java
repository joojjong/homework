package com.joojognhun.homework.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ItemDto {
    private int rnum;
    private String platPlc;
    private String sigunguCd;
    private String bjdongCd;
    private String platGbCd;
    private String bun;
    private String ji;
    private String mgmBldrgstPk;
    private String mgmUpBldrgstPk;
    private String regstrGbCd;
    private String regstrGbCdNm;
    private String regstrKindCd;
    private String regstrKindCdNm;
    private String newPlatPlc;
    private String bldNm;
    private String splotNm;
    private String block;
    private String lot;
    private int bylotCnt;
    private String naRoadCd;
    private String naBjdongCd;
    private String naUgrndCd;
    private int naMainBun;
    private int naSubBun;
    private String jiyukCd;
    private String jiguCd;
    private String guyukCd;
    private String jiyukCdNm;
    private String jiguCdNm;
    private String guyukCdNm;
    private String crtnDay;

}
