package cn.edu.nciae.contentcenter.common.mapper;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Component
public interface ProblemMapper extends BaseMapper<Problem> {

    /**
     * <p>
     * 查询 : 分页显示
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @return 分页对象
     */
    IPage<Problem> selectProblemListPage(Page<?> page);

}
