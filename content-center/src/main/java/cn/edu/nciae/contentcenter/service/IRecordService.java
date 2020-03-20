package cn.edu.nciae.contentcenter.service;

import cn.edu.nciae.contentcenter.common.entity.Record;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author RexALun
 * @since 2020-02-08
 */
public interface IRecordService extends IService<Record> {

    IPage<Record> listRecordByPaging(Page<Record> page);

    IPage<Record> listRecordByUid(Page<Record> page, String nickname);

}
