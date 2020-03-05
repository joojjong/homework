package com.joojognhun.homework.service;

import com.google.gson.Gson;
import com.joojognhun.homework.dto.ItemDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


@Service
public class OpenApiService {

    public List<ItemDto> getItemsFromOpenApi() throws UnsupportedEncodingException, URISyntaxException, ParseException {

        String url = "http://apis.data.go.kr/1611000/BldRgstService/getBrBasisOulnInfo?sigunguCd=11560&bjdongCd=11800";
        String serviceKey = "EJt3X5VcUzwhcTsR7BqCp0dlXcdBHWiEPsWaeJ6lUN6fcIhfxb8X4kwFIjKmP6APcVJBPILeStpY%2B7hTUhTK8w%3D%3D";
        RestTemplate restTemplate = new RestTemplate();

//        String decodeServiceKey = URLDecoder.decode(serviceKey, "UTF-8");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));    //Response Header to UTF-8

        long num = 1;
        URI test = new URI(url+"&numOfRows="+num +"&ServiceKey=" +serviceKey+"&_type=json");
        System.out.println("test=> " + test.toString());
        ResponseEntity<String> testt = restTemplate.getForEntity(test, String.class);
        String json = testt.getBody().toString();

        System.out.println("testt==>? " + testt.toString());

        Gson gson = new Gson();



        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(json);
        System.out.println("json => " + jsonObj);
        JSONObject jsonResponse = (JSONObject) jsonObj.get("response");
        JSONObject jsonBody = (JSONObject) jsonResponse.get("body");
        num = (Long) jsonBody.get("totalCount");
        System.out.println("#### totalCount => " + num);


        URI test2 = new URI(url+"&numOfRows="+num +"&ServiceKey=" +serviceKey+"&_type=json");
        ResponseEntity<String> testtt = restTemplate.getForEntity(test2, String.class);
        String json2 = testtt.getBody().toString();
        JSONParser jsonParser2 = new JSONParser();
        JSONObject jsonObj2 = (JSONObject) jsonParser2.parse(json2);
        JSONObject jsonResponse2 = (JSONObject) jsonObj2.get("response");
        JSONObject jsonBody2 = (JSONObject) jsonResponse2.get("body");
        JSONObject jsonItems = (JSONObject) jsonBody2.get("items");
        JSONArray itemsArray = (JSONArray) jsonItems.get("item");

        System.out.println("### itemsArray.size()=> " + itemsArray.size());

        List<ItemDto> itemDtoList = new ArrayList<ItemDto>();
        for (int i = 0; i < 2 ; i++) {
            JSONObject tempObj = (JSONObject) itemsArray.get(i);
            ItemDto item = gson.fromJson(tempObj.toString(), ItemDto.class);
            itemDtoList.add(item);
            System.out.println(i + "번째 => " + tempObj.toJSONString());
        }


        System.out.println("after rest");
        return itemDtoList;
    }
}