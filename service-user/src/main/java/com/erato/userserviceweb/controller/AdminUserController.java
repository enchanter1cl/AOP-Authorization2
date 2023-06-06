package com.erato.userserviceweb.controller;

import com.erato.cloudcommon.dto.Result;
import com.erato.cloudcommon.dto.ResultGenerator;
import com.erato.cloudcommon.pojo.AdminUserToken;
import com.erato.userserviceweb.config.annotation.TokenToAdminUser;
import com.erato.userserviceweb.entity.AdminUser;
import com.erato.userserviceweb.req.AdminLoginParam;
import com.erato.userserviceweb.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (AdminUser)表控制层
 *
 * @author makejava
 * @since 2023-06-06 02:05:28
 */
@RestController
//@RequestMapping("adminUser")
@Slf4j
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping(value = "/users/admin/login", method = RequestMethod.POST)
    public Result<String> login(@RequestBody AdminLoginParam adminLoginParam) {
        String loginResult = adminUserService.login(adminLoginParam.getUserName(), adminLoginParam.getPasswordMd5());
        log.info("manage login api,adminName={},loginResult={}", adminLoginParam.getUserName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == 32) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }



    @RequestMapping(value = "/users/admin/profile", method = RequestMethod.POST)
    public Result profile(@TokenToAdminUser AdminUserToken adminUser) {
        log.info("adminUser:{}", adminUser.toString());
        AdminUser adminUserEntity = adminUserService.getUserDetailById(adminUser.getAdminUserId());
        if (adminUserEntity != null) {
            adminUserEntity.setLoginPassword("******");
            Result result = ResultGenerator.genSuccessResult();
            result.setData(adminUserEntity);
            return result;
        }
        return ResultGenerator.genFailResult("无此用户数据");
    }

    @GetMapping("/users/admin/test")
    public String testGateway () {
        return "hello";
    }




//    /**
//     * 分页查询
//     *
//     * @param adminUser   筛选条件
//     * @param pageRequest 分页对象
//     * @return 查询结果
//     */
//    @GetMapping
//    public ResponseEntity<Page<AdminUser>> queryByPage(AdminUser adminUser, PageRequest pageRequest) {
//        return ResponseEntity.ok(this.adminUserService.queryByPage(adminUser, pageRequest));
//    }
//
//    /**
//     * 通过主键查询单条数据
//     *
//     * @param id 主键
//     * @return 单条数据
//     */
//    @GetMapping("{id}")
//    public ResponseEntity<AdminUser> queryById(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(this.adminUserService.queryById(id));
//    }
//
//    /**
//     * 新增数据
//     *
//     * @param adminUser 实体
//     * @return 新增结果
//     */
//    @PostMapping
//    public ResponseEntity<AdminUser> add(AdminUser adminUser) {
//        return ResponseEntity.ok(this.adminUserService.insert(adminUser));
//    }
//
//    /**
//     * 编辑数据
//     *
//     * @param adminUser 实体
//     * @return 编辑结果
//     */
//    @PutMapping
//    public ResponseEntity<AdminUser> edit(AdminUser adminUser) {
//        return ResponseEntity.ok(this.adminUserService.update(adminUser));
//    }
//
//    /**
//     * 删除数据
//     *
//     * @param id 主键
//     * @return 删除是否成功
//     */
//    @DeleteMapping
//    public ResponseEntity<Boolean> deleteById(Long id) {
//        return ResponseEntity.ok(this.adminUserService.deleteById(id));
//    }

}

