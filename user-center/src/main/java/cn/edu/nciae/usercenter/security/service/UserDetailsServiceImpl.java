package cn.edu.nciae.usercenter.security.service;

import cn.edu.nciae.usercenter.common.dto.ClaimsDTO;
import cn.edu.nciae.usercenter.common.entity.*;
import cn.edu.nciae.usercenter.common.mapper.ResourceMapper;
import cn.edu.nciae.usercenter.common.mapper.RoleMapper;
import cn.edu.nciae.usercenter.common.mapper.UserInfoMapper;
import cn.edu.nciae.usercenter.common.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/4/4 11:35 PM
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public SystemUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUid, userInfo.getUid()));

        List<Role> roles = roleMapper.selectBatchIds(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
        String mergedRole = roles.stream().map(Role::getRolename).distinct().collect(Collectors.joining(","));

        List<Resource> resources = resourceMapper.selectBatchIds(roles.stream().map(Role::getRoleId).collect(Collectors.toList()));
        ClaimsDTO claimsDTO = ClaimsDTO.builder().urlResources(resources.stream().map(Resource::getUrl).collect(Collectors.toList())).build();
        return new SystemUserDetails(username, userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(mergedRole), claimsDTO);
    }
}
