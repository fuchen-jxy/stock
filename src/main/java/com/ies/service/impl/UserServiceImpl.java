package com.ies.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ies.constast.SysConstast;
import com.ies.domain.BreakageCriteria;
import com.ies.domain.CaigouCriteria;
import com.ies.domain.User;
import com.ies.domain.UserCriteria;
import com.ies.mapper.BreakageMapper;
import com.ies.mapper.CaigouMapper;
import com.ies.mapper.UserMapper;
import com.ies.service.UserService;
import com.ies.utils.DataGridView;
import com.ies.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CaigouMapper caigouMapper;
    @Autowired
    private BreakageMapper breakageMapper;

    @Override
    public User login(UserVo userVo) {
        if (StringUtils.isEmpty(userVo.getUsername()) || StringUtils.isEmpty(userVo.getPassword())) {
            return null;
        }
        String pwd = DigestUtils.md5DigestAsHex(userVo.getPassword().getBytes());
        userVo.setPassword(pwd);
        UserCriteria example = new UserCriteria();
        example.createCriteria().andUsernameEqualTo(userVo.getUsername()).andPasswordEqualTo(userVo.getPassword());
        List<User> users = userMapper.selectByExample(example);
        if (users.size() >= 1) {
            return users.get(0);
        } else {
            return null;
        }

    }

    @Override
    public DataGridView queryAllUser(UserVo uservo) {
        UserCriteria example = new UserCriteria();
        UserCriteria.Criteria criteria = example.createCriteria();
        if (uservo.getUsername() != null) {
            criteria.andUsernameLike("%" + uservo.getUsername() + "%");
        }
        Page<Object> page = PageHelper.startPage(uservo.getPage(), uservo.getLimit());
        List<User> users = userMapper.selectByExample(example);
        return new DataGridView(page.getTotal(), users);
    }

    @Override
    public Integer addUser(UserVo userVo) {
        if (checkTheSameName(userVo.getUsername())) {
            throw new IllegalArgumentException("用户名已经存在");
        }
        return userMapper.insert(userVo);
    }

    @Override
    public void updateUser(UserVo userVo) {
        User user = this.queryById(userVo.getId());
        if (!user.getUsername().equals(userVo.getUsername())) {
            if (checkTheSameName(userVo.getUsername())) {
                throw new IllegalArgumentException("用户名已经存在");
            }
        }
        userMapper.updateByPrimaryKey(userVo);
    }

    @Override
    @Transactional
    public void deleteBatchUser(Long[] ids) {
        for (Long id : ids) {
            deleteUser(id);
        }
    }

    @Override
    public void deleteUser(Long id) {
        checkDeleteUser(id);
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void resetUserPwd(Long id) {
        String pwd = DigestUtils.md5DigestAsHex(SysConstast.USER_DEFAULT_PWD.getBytes());
        UserCriteria example = new UserCriteria();
        example.createCriteria().andPasswordEqualTo(pwd);
        User user = userMapper.selectByPrimaryKey(id);
        user.setPassword(pwd);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private boolean checkTheSameName(String name) {
        UserCriteria example = new UserCriteria();
        example.createCriteria().andUsernameEqualTo(name);
        List<User> userList = userMapper.selectByExample(example);
        return !CollectionUtils.isEmpty(userList);
    }

    private void checkDeleteUser(Long id) {
        CaigouCriteria caigouCriteria = new CaigouCriteria();
        caigouCriteria.createCriteria().andUserIdEqualTo(id);
        int countCaigou = caigouMapper.countByExample(caigouCriteria);
        if (countCaigou > 0) {
            throw new IllegalArgumentException("当前员工已经关联采购单无法删除");
        }
        BreakageCriteria breakageCriteria = new BreakageCriteria();
        breakageCriteria.createCriteria().andUserIdEqualTo(id);
        int countBreakage = breakageMapper.countByExample(breakageCriteria);
        if (countBreakage > 0) {
            throw new IllegalArgumentException("当前员工已经关联采退单无法删除");
        }
    }

}
