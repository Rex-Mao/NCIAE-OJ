package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Checkpoint;
import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.entity.Tag;
import cn.edu.nciae.contentcenter.utils.ClassUtils;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
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
@EqualsAndHashCode(callSuper = true)
@TableName(value = "problem", resultMap = "ProblemViewResultMap")
public class ProblemVO extends Problem {
    /**
     * Sample input and output
     */
    private List<Sample> samples;

    /**
     * Tags of the problem
     */
    private List<Tag> tags;

    /**
     * Languages of the problem
     */
    private List<String> languages;

    /**
     * Checkpoints of the problem
     */
    private List<Checkpoint> checkpoints;

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

    @Override
    public String toString() {
        String problemString = unzip2Problem().toString();
        String sampleString = "";
        for(Sample s : this.samples) {
            sampleString = sampleString.concat(s.toString());
        }
        return problemString + "\n" + sampleString;
    }

}
