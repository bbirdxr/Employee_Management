package com.example.employee.controller;


import com.example.employee.mapper.PositionMapper;
import com.example.employee.service.PositionService;
import com.example.entity.Position;
import com.example.result.BaseResponse;
import com.example.result.ErrorCode;
import com.example.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 职位表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    @Autowired
    PositionService positionService;

    @Autowired
    PositionMapper positionMapper;
    //增
    @PostMapping("/")
    BaseResponse add(@RequestParam String positionName){
        if(positionName.equals("")){
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        if(positionMapper.selectByName(positionName)!=null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"已有相同职位");
        }else {
            positionMapper.addOne(positionName);
            return ResultUtils.success("添加成功");
        }
    }

    //查
    @GetMapping("/like")
    BaseResponse get(@RequestParam(required = false) String positionName){
        List<Position> positionList=positionMapper.likeSelectByName(positionName);
        return ResultUtils.success(positionList);
    }
    //职位
}

