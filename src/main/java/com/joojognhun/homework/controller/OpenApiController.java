package com.joojognhun.homework.controller;

import com.joojognhun.homework.batch.BatchJobConfig;
import com.joojognhun.homework.dto.ApiParamDto;
import com.joojognhun.homework.dto.ItemDto;
import com.joojognhun.homework.dto.UserDto;
import com.joojognhun.homework.entity.ItemEntity;
import com.joojognhun.homework.entity.UserEntity;
import com.joojognhun.homework.store.ItemJpaRepository;
import com.joojognhun.homework.store.UserJpaRepository;
import org.json.simple.parser.ParseException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin("*")
@RestController
public class OpenApiController {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Autowired
    com.joojognhun.homework.service.OpenApiService openApiService;

    @PostMapping("/user/login")
    public String findUser(@RequestBody UserDto user) {
        System.out.println("## what is user => " + user);
        if(null != userJpaRepository.findByUserIdAndPassword(user.getUserId(), user.getPw())) {
            return "ok";
        }else{
            return "fail";
        }
    }

    @PostMapping("/user/register")
    public String createUser(@RequestBody UserDto user) {
        System.out.println("## what is userId => " + user.getUserId());
        System.out.println("## what is password => " + user.getPw());

        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(user.getUserId());
        userEntity.setPassword(user.getPw());
        if (userJpaRepository.findById(user.getUserId()).isPresent()) {
            System.out.println("ID 가 이미 존재합니다.");
            return "fail";
        } else {
            UserEntity entity = userJpaRepository.save(userEntity);
            System.out.println("## 회원가입성공 " + entity);
            return "ok";
        }
    }

    @PostMapping("/openApi/find")
    public List<ItemEntity> findOpenApiData(@RequestBody ApiParamDto params) {
        System.out.println("## findOpenApiData start ~");
        System.out.println("## findOpenApiData params => " + params.toString());
        if (params.getSido().equals("seoul")) {

            if (params.getPlatGbCd().equals("default")) {
                return itemJpaRepository.findBySigunguCdAndBjdongCd(params.getSigunguCd(), params.getBjdongCd());
            } else {
                return itemJpaRepository.findBySigunguCdAndBjdongCdAndPlatGbCd(params.getSigunguCd(), params.getBjdongCd(), params.getPlatGbCd());
            }
        }
        return null;
    }


    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public Object getData(@RequestBody String id) throws UnsupportedEncodingException, URISyntaxException, ParseException {

        System.out.println("start controller");
        List<ItemDto> response = openApiService.getItemsFromOpenApi();
        System.out.println(" what is response => " + response);
        System.out.println(" what is response 1 => " + response.get(0).getBjdongCd());

        List<ItemEntity> entities = response.stream().map(e -> {
            System.out.println(" what is  e => " + e);
            ItemEntity entity = new ItemEntity(e);
            return entity;
        }).collect(Collectors.toList());
        itemJpaRepository.saveAll(entities);
        return response;
    }

    @RequestMapping(value = "/batch", method = RequestMethod.GET)
    public String startBatch() {
        System.out.println(" $$$$$$$$$$ batch start ~~~~~~");

        ApplicationContext context = new AnnotationConfigApplicationContext(BatchJobConfig.class);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("openApiJob9", Job.class);

        try {
            JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
            System.out.println("### jobExecution");

        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

        return "Batch Success!!";
    }

}