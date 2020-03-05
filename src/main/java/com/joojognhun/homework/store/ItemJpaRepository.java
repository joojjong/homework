package com.joojognhun.homework.store;

import com.joojognhun.homework.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemJpaRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findBySigunguCdAndBjdongCd(String sigunguCd, String bjdongCd);
    List<ItemEntity> findBySigunguCdAndBjdongCdAndPlatGbCd(String sigunguCd, String bjdongCd, String platGbCd);

}
