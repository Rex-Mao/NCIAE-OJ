package cn.edu.nciae.contentcenter.service.impl;

import cn.edu.nciae.contentcenter.common.entity.Record;
import cn.edu.nciae.contentcenter.common.mapper.RecordMapper;
import cn.edu.nciae.contentcenter.service.IRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Autowired
    private RecordMapper recordMapper;

    /**
     * desc : list the record by paging
     * @param page - Page
     * @return IPage<Record>
     */
    @Override
    public IPage<Record> listRecordByPaging(Page<Record> page) {
        return recordMapper.selectPage(page, Wrappers.<Record>lambdaQuery().orderByDesc(Record::getCommitTime));
    }

    /**
     * desc : list the record by nickname with paging
     * @param page - Page
     * @param nickname - nickname
     * @return IPage<Record>
     */
    @Override
    public IPage<Record> listRecordByUid(Page<Record> page, String nickname) {
        return recordMapper.selectPage(page, Wrappers.<Record>lambdaQuery().eq(Record::getCommitNickname, nickname));
    }
}
