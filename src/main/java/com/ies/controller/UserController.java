package com.ies.controller;

import com.ies.constast.SysConstast;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.utils.ResultObj;
import com.ies.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo uservo) {
        return userService.queryAllUser(uservo);
    }

    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo) {
        if (userVo.getId() != null) {
            userVo.setId(null);
        }
        if (userVo.getType() == null) {
            userVo.setType(SysConstast.USER_TYPE_NORMAL);
        }
        String pwd = DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes());
        userVo.setPassword(pwd);
        userService.addUser(userVo);
        return ResultObj.ADD_SUCCESS;
    }

    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo uservo) {
        userService.updateUser(uservo);
        return ResultObj.UPDATE_SUCCESS;
    }


    @RequestMapping("deleteBatchUser")
    public ResultObj deleteBatchUser(UserVo userVo) {
        userService.deleteBatchUser(userVo.getIds());
        return ResultObj.DELETE_SUCCESS;
    }

    @RequestMapping("deleteUser")
    public ResultObj deleteUser(UserVo uservo) {
        try {
            userService.deleteUser(uservo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }
    }


    @RequestMapping("resetUserPwd")
    public ResultObj resetUserPwd(Long id) {
        try {
            userService.resetUserPwd(id);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            return ResultObj.RESET_ERROR;
        }
    }
}
