package com.example.employee.controller;


import com.example.employee.common.BaseResponse;
import com.example.employee.common.ErrorCode;
import com.example.employee.common.ResultUtils;
import com.example.employee.entity.Department;
import com.example.employee.service.DepartmentService;
import com.example.employee.service.impl.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-06-27
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @GetMapping("/getByName")
    BaseResponse  getAllDepartmentsIncludingSons(String departmentName){
        Department d=departmentService.findDepartment(departmentName);
        if(d==null){
            return  ResultUtils.error(ErrorCode.PARAMS_ERROR,"部门不存在");
        }else {
            return ResultUtils.success(departmentService.filledWithSons(d));
        }
    }

    @ResponseBody
    @PostMapping("/add")
    BaseResponse<Department> addDepartment(@RequestParam String departmentName,@RequestParam String parentDepartmentName ){
        Department d1=departmentService.findDepartment(departmentName);
        if(d1!=null){
            return new BaseResponse(400, d1,"已存在同名部门");
        }
        if(parentDepartmentName.equals("")){//添加根部门
            departmentService.addRootDepartment(departmentName);
            return ResultUtils.success(departmentService.findDepartment(departmentName));
        }else{//添加部门
            Department d=departmentService.findDepartment(parentDepartmentName);
            if(d==null){
                return new BaseResponse(400, d1,"不存在该父部门");
            }
            departmentService.addDepartment(departmentName,parentDepartmentName);
            return ResultUtils.success(null);
        }


    }




}

