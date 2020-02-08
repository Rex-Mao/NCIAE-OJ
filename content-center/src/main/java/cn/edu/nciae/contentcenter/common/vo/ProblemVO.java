package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.utils.VOUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/2/8 11:40 AM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "problem", resultMap = "ProblemViewResultMap")
public class ProblemVO extends Problem {
    /**
     * 样例输入输出
     */
    private List<Sample> sampleList;

    public Problem getProblem() {
        return VOUtils.getSuperObjectFromSubObject(this, Problem.class);
    }

    public void setProblem(Problem problem) {
        BeanUtils.copyProperties(problem, this);
    }
}
