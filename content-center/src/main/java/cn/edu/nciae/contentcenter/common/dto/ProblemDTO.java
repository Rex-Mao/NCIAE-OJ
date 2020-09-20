package cn.edu.nciae.contentcenter.common.dto;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.entity.Tag;
import cn.edu.nciae.contentcenter.utils.ClassUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author RexALun
 * @version 1.0
 * Annotation :
 * @date 2020/1/30 6:17 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProblemDTO extends Problem {

    private List<Sample> samples;

    private List<Checkpoint> checkpoints;

    private List<Tag> tags;

    /**
     * desc : get Problem instance from VO
     * @return Problem
     */
    public Problem unzip2Problem() {
        return ClassUtils.getSuperObjectFromSubObject(this, Problem.class);
    }

    /**
     * desc : zip Problem instance to VO
     */
    public void zipProblem(Problem problem) {
        BeanUtils.copyProperties(problem, this);
    }

}
