package com.joojognhun.homework.entity;


import com.joojognhun.homework.dto.ItemDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Getter
@Setter
@Entity
@Table(name = "items")
public class ItemEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

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

    public ItemEntity(){

    }

    public ItemEntity(ItemDto dto){
        System.out.println("## in constructor dto => " + dto);

        BeanUtils.copyProperties(dto, this);
        System.out.println("## in constructor " + this);
    }

    public static ItemEntity toDomain(ItemDto dto){
        ItemEntity entity = new ItemEntity();
        System.out.println("## in todomain dto" + dto);
        BeanUtils.copyProperties(dto, entity);
        System.out.println("## in toDomain entity " +entity);
        return entity;
    }

}
