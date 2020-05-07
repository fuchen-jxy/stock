package com.ies.service;

import com.ies.domain.User;
import com.ies.utils.DataGridView;
import com.ies.vo.UserVo;

public interface UserService {
    User login(UserVo userVo);

    DataGridView queryAllUser(UserVo userVo);

    Integer addUser(UserVo userVo);

    void updateUser(UserVo userVo);

    void deleteBatchUser(Long[] ids);

    void deleteUser(Long id);

    void resetUserPwd(Long id);

    User queryById(Long id);

}
