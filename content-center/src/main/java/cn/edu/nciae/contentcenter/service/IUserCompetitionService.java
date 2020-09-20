package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.entity.UserCompetition;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.Authentication;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
public interface IUserCompetitionService extends IService<UserCompetition> {

    /**
     * desc : check if the user has authority to do action
     * @param authentication -
     * @param cid -
     * @return Boolean
     */
    Boolean checkAdminContestAuthority(Authentication authentication, Long cid);

    /**
     * desc : check if the user has authority to do action
     * @param authentication -
     * @param cid -
     * @return Boolean
     */
    Boolean checkUserContestAuthority(Authentication authentication, Long cid);
}
