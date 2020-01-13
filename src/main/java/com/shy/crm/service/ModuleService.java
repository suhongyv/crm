package com.shy.crm.service;

import com.shy.base.BaseService;
import com.shy.crm.dao.ModuleMapper;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.vo.Module;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author suhongyv
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
    @Resource
    private ModuleMapper moduleMapper;

    public List<TreeDto> queryAllModules(){
        return moduleMapper.queryAllModules();
    }
}
