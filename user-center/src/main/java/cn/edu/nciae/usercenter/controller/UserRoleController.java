package cn.edu.nciae.usercenter.controller;

import cn.edu.nciae.usercenter.common.entity.UserRole;
import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@RestController
public class UserRoleController {

    @Autowired
    private IUserRoleService userRoleService;

    @PutMapping("/super/admin/userrole/{uid}")
    public MessageVO<Boolean> updateUserRoleBySuperAdmin(@PathVariable("uid") Long uid, @RequestBody ArrayList<Long> roleIds) {
        List<UserRole> userRoleList = new ArrayList<>(8);
        roleIds.forEach(roleId -> {
            userRoleList.add(UserRole.builder()
                    .uid(uid)
                    .roleId(roleId)
                    .build());
        });
        ArrayList<Long> uids = new ArrayList<>();
        uids.add(uid);
        userRoleService.removeByUids(uids);
        userRoleService.saveBatch(userRoleList);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(true)
                .build();
    }

    @DeleteMapping("/super/admin/userrole")
    public MessageVO<Boolean> updateUserRoleBySuperAdmin(@RequestBody ArrayList<Long> uids) {
        userRoleService.removeByUids(uids);
        return MessageVO.<Boolean>builder()
                .error(null)
                .data(true)
                .build();
    }

}
