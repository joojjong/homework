package com.joojognhun.homework.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class OpenApiDto {
    List<ItemDto> items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
