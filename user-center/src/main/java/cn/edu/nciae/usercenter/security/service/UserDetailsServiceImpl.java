package cn.edu.nciae.usercenter.security.service;

import cn.edu.nciae.usercenter.common.dto.ClaimsDTO;
import cn.edu.nciae.usercenter.common.entity.*;
import cn.edu.nciae.usercenter.common.mapper.RoleResourceMapper;
import cn.edu.nciae.usercenter.common.mapper.UserInfoMapper;
import cn.edu.nciae.usercenter.common.mapper.UserRoleMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private RoleResourceMapper roleResourceMapper;

    @Override
    public SystemUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoMapper.selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getNickname, username));
        List<UserRole> userRoles = userRoleMapper.selectList(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUid, userInfo.getUid()));

        Set<Role> roles = new HashSet<>();
        Set<Resource> resources = new HashSet<>();
        for (UserRole userRole : userRoles) {
            roleResourceMapper.listRoleResourceVOByRoleId(userRole.getRoleId())
                    .forEach(item -> {
                        resources.add(item.getResource());
                        roles.add(item.getRole());
                    });
        }
        String mergedRole = roles.stream().map(Role::getRolename).distinct().collect(Collectors.joining(","));
        ClaimsDTO claimsDTO = ClaimsDTO.builder().urlResources(resources.stream().map(Resource::getUrl).collect(Collectors.toList())).build();
        return new SystemUserDetails(username, userInfo.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(mergedRole), claimsDTO);
    }
}
