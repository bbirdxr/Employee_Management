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


    @GetMapping("/find")
    BaseResponse  getAllDepartmentsIncludingSons(Long departmentId){
        Department d=departmentService.findDepartmentById(departmentId);
        if(d==null){
            return  ResultUtils.error(ErrorCode.PARAMS_ERROR,"部门不存在");
        }else {
            return ResultUtils.success(departmentService.filledWithSons(d));
        }
    }

    @GetMapping("/delete")
    BaseResponse  delete(@RequestParam Long departmentId){
        departmentService.deleteDepartmentIfExist(departmentId);
        return ResultUtils.success("成功删除");
    }

    @PostMapping("/change")
    BaseResponse<Department> changeName(@RequestParam String newName,@RequestParam Long departmentId ){
        Department d=departmentService.findDepartmentById(departmentId);
        if(d==null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"没有找到原部门");
        }
        if(departmentService.ifReplicateName(newName,d.getParentDepartmentId())){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR,"已经有重名部门");
        }
            departmentService.changeDepartmentName(newName, departmentId);
            return ResultUtils.success(departmentService.findDepartmentById(departmentId));
    }

    @PostMapping("/add")
    BaseResponse<Department> addDepartment(@RequestParam String departmentName,@RequestParam Long parentDepartmentId ){
        Department d1=departmentService.findDepartment(departmentName);
        if(d1!=null){
            return new BaseResponse(400, d1,"已存在同名部门");
        }
        if(parentDepartmentId.equals("")){//添加根部门
            departmentService.addRootDepartment(departmentName);
            return ResultUtils.success(departmentService.findDepartment(departmentName));
        }else{//添加部门
            Department d=departmentService.findDepartmentById(parentDepartmentId);
            if(d==null){
                return new BaseResponse(400, d1,"不存在该父部门");
            }
            departmentService.addDepartment(departmentName,parentDepartmentId);
            return ResultUtils.success(null);
        }
    }




}

