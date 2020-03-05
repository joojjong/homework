package com.joojognhun.homework.batch;

import com.google.gson.Gson;
import com.joojognhun.homework.dto.ItemDto;
import com.joojognhun.homework.entity.ItemEntity;
import com.joojognhun.homework.store.ItemJpaRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.*;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableBatchProcessing
public class BatchJobConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    private int cnt = 0;

    @Bean
    public Job openApiJob() throws Exception {
        System.out.println("### openApiJob Start !!!!");
        return jobBuilderFactory.get("openApiJob9")
                .start(startStep())
                .next(step1())
                .on("*")
                .to(conditionalJobStep2())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        System.out.println("### step1");
        return stepBuilderFactory.get("step1")
                .<List<ItemDto>, List<ItemEntity>>chunk(1)
                .reader(openApiItemReader())
                .processor(openApiItemProcessor())
                .writer(openApiItemWriter())
                .build();
    }

    @Bean
    public Step startStep() {
        System.out.println("### startStep");
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("@@@ start step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }



    @Bean
    public Step conditionalJobStep2() {
        return stepBuilderFactory.get("conditionalJobStep2")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("@@@ step2");
                    System.out.println("#######################");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    public class ReturnsListReader implements ItemReader<List<?>> {

        public List<?> read() throws Exception {


            String url = "http://apis.data.go.kr/1611000/BldRgstService/getBrBasisOulnInfo?sigunguCd=11560&bjdongCd=11800";
            String serviceKey = "EJt3X5VcUzwhcTsR7BqCp0dlXcdBHWiEPsWaeJ6lUN6fcIhfxb8X4kwFIjKmP6APcVJBPILeStpY%2B7hTUhTK8w%3D%3D";
            RestTemplate restTemplate = new RestTemplate();

            long num = 1;
            URI test = new URI(url + "&numOfRows=" + num + "&ServiceKey=" + serviceKey + "&_type=json");
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


            URI test2 = new URI(url + "&numOfRows=" + num + "&ServiceKey=" + serviceKey + "&_type=json");
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
            for (int i = 0; i < itemsArray.size(); i++) {
                JSONObject tempObj = (JSONObject) itemsArray.get(i);
                ItemDto item = gson.fromJson(tempObj.toString(), ItemDto.class);
                itemDtoList.add(item);
            }

            System.out.println("## itemDtoList in read => " + itemDtoList);

            return itemDtoList;
        }
    }

    @Bean
    ItemReader<List<ItemDto>> openApiItemReader() throws Exception {
        return  new ItemReader<List<ItemDto>>() {
            @Override
            public List<ItemDto> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                System.out.println("0@@@@@@@@@@@@@ openApiItemReader");
                cnt++;
                System.out.println(" $$ cnt => " + cnt);
                return cnt == 2 ? null :(List<ItemDto>) new ReturnsListReader().read();
            }
        };
    }

    @Bean
    ItemProcessor<List<ItemDto>, List<ItemEntity>> openApiItemProcessor() {
        return new ItemProcessor<List<ItemDto>, List<ItemEntity>>() {
            @Override
            public List<ItemEntity> process(List<ItemDto> list) throws Exception {
                System.out.println("1@@@@@@@@@@@@@ openApiItemProcessor");

                List<ItemEntity> listEntity = list.stream().map(e -> {
                    System.out.println(" what is  e => " + e);
                    ItemEntity entity = new ItemEntity(e);
                    return entity;
                }).collect(Collectors.toList());
                return listEntity;
            }
        };
    }

    @Bean
    ItemWriter<List<ItemEntity>> openApiItemWriter() {
        return new ItemWriter<List<ItemEntity>>() {
            @Override
            public void write(List<? extends List<ItemEntity>> itemsLists) throws Exception {
                System.out.println("2@@@@@@@@@@@@@ openApiItemWriter");
                for (List<ItemEntity> items : itemsLists) {
                    itemJpaRepository.saveAll(items);
                }

            }
        };
    }
}
