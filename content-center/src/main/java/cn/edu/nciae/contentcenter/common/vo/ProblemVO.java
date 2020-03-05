package cn.edu.nciae.contentcenter.common.vo;

import cn.edu.nciae.contentcenter.common.entity.Problem;
import cn.edu.nciae.contentcenter.common.entity.Sample;
import cn.edu.nciae.contentcenter.common.entity.Tag;
import cn.edu.nciae.contentcenter.utils.VOUtils;
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
     * desc : get Problem instance from VO
     * @return Problem
     */
    public Problem unzipProblemVO() {
        return VOUtils.getSuperObjectFromSubObject(this, Problem.class);
    }

    /**
     * desc : zip Problem instance to VO
     */
    public void zipProblem(Problem problem) {
        BeanUtils.copyProperties(problem, this);
    }

    @Override
    public String toString() {
        String problemString = unzipProblemVO().toString();
        String sampleString = "";
        for(Sample s : this.samples) {
            sampleString = sampleString.concat(s.toString());
        }
        return problemString + "\n" + sampleString;
    }

}
