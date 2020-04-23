package cn.edu.nciae.usercenter.controller;


import cn.edu.nciae.usercenter.common.vo.MessageVO;
import cn.edu.nciae.usercenter.common.vo.RoleListVO;
import cn.edu.nciae.usercenter.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/admin/role")
    public MessageVO<RoleListVO> getAdminRoles() {
        return MessageVO.<RoleListVO>builder()
                .error(null)
                .data(RoleListVO.builder()
                        .roles(roleService.list())
                        .build())
                .build();
    }

}
