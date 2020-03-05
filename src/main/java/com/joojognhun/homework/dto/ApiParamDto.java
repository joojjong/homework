package com.joojognhun.homework.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ApiParamDto {
    private String sido; // default or seoul
    private String sigunguCd;
    private String bjdongCd;
    private String platGbCd;
}
