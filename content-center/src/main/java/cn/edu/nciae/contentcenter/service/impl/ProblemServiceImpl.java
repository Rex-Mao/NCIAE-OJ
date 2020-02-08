package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.mapper.ProblemMapper;
import cn.edu.nciae.contentcenter.service.IProblemService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements IProblemService {
    @Autowired
    public ProblemMapper problemMapper;
    /**
     * @param page
     * @return IPage<Problem>
     */
    public IPage<Problem> getProblemListPage(Page<Problem> page) {
        return problemMapper.selectProblemListPage(page);
    }

}
