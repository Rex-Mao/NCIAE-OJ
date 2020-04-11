package cn.edu.nciae.usercenter.common.mapper;

import cn.edu.nciae.usercenter.common.entity.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

/**
 * @Author: RexALun
 * @Date: 2019/12/26 7:50 PM
 * @Version 1.0
 * @Annotation :
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {

    @Autowired
    public UserInfoMapper userInfoMapper;

    @Test
    public void testUserInsert() {
        UserInfo userinfo = UserInfo.builder()
                .uid(new Long(1))
                .nickname("Rex")
                .password("123456")
                .email("123@163.com")
                .regtime(new Date(0))
                .solvednum(0)
                .build();
        int rows = userInfoMapper.insert(userinfo);
        Assert.assertEquals(rows, 1);
    }
}
