package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Competition;
import cn.edu.nciae.contentcenter.common.entity.SystemUserDetails;
import cn.edu.nciae.contentcenter.common.entity.UserCompetition;
import cn.edu.nciae.contentcenter.common.mapper.CompetitionMapper;
import cn.edu.nciae.contentcenter.common.mapper.UserCompetitionMapper;
import cn.edu.nciae.contentcenter.service.IUserCompetitionService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Service
public class UserCompetitionServiceImpl extends ServiceImpl<UserCompetitionMapper, UserCompetition> implements IUserCompetitionService {

    @Autowired
    private CompetitionMapper competitionMapper;

    /**
     * desc : check if the user has authority to do action
     * @param authentication -
     * @param cid -
     * @return Boolean
     */
    @Override
    public Boolean checkAdminContestAuthority(Authentication authentication, Long cid) {
        Competition competition = competitionMapper.selectById(cid);
        String username = ((SystemUserDetails) authentication.getPrincipal()).getUsername();
        return competition != null && competition.getCreateUsername().equals(username);
    }

    /**
     * desc : check if the user has authority to do action
     * @param authentication -
     * @param cid -
     * @return Boolean
     */
    @Override
    public Boolean checkUserContestAuthority(Authentication authentication, Long cid) {
        Competition competition = competitionMapper.selectById(cid);
        if (competition == null) {
            return false;
        }
        List<UserCompetition> userCompetitions = list(Wrappers.<UserCompetition>lambdaQuery().eq(UserCompetition::getCid, cid));
        Set<String> nicknames = userCompetitions.stream().map(UserCompetition::getNickname).collect(Collectors.toSet());
        String username = ((SystemUserDetails) authentication.getPrincipal()).getUsername();
        return competition.getCreateUsername().equals(username) || nicknames.contains((username));
    }

}
