package cn.edu.nciae.usercenter.service.impl;

import cn.edu.nciae.usercenter.common.entity.Resource;
import cn.edu.nciae.usercenter.common.mapper.ResourceMapper;
import cn.edu.nciae.usercenter.service.IResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author RexALun
 * @since 2019-12-26
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

}
