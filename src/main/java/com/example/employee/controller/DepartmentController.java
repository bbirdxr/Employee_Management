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
    BaseResponse  getDepartmentsIncludingSons(Long departmentId){
        Department d=departmentService.findDepartmentById(departmentId);
        if(d==null){
            return  ResultUtils.error(ErrorCode.PARAMS_ERROR,"部门不存在");
        }else {
            return ResultUtils.success(departmentService.filledWithSons(d));
        }
    }

    @GetMapping("/all")
    BaseResponse  getAllDepartments(){
        return ResultUtils.success(departmentService.getAllDepartment());
    }

    @GetMapping("/delete")
    BaseResponse  delete(@RequestParam Long departmentId){
        departmentService.deleteDepartmentIfExist(departmentId);
        return ResultUtils.success("成功删除");
    }

    @PutMapping("/change")
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
    BaseResponse addDepartment(@RequestParam String departmentName,@RequestParam Long parentDepartmentId ){
        Department d1=departmentService.findDepartmentByParentIdAndName(parentDepartmentId,departmentName);
        if(d1!=null){
            return new BaseResponse(400, d1,"已存在同名部门");
        }
        if(parentDepartmentId==0L){//添加根部门
            departmentService.addRootDepartment(departmentName);
            return ResultUtils.success("添加成功");
        }else{//添加部门
            Department d=departmentService.findDepartmentById(parentDepartmentId);
            if(d==null){
                return new BaseResponse(400, d1,"不存在该父部门");
            }
            departmentService.addDepartment(departmentName,parentDepartmentId);
            return new BaseResponse(200,"添加成功");
        }
    }
}

