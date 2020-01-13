package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeDto>  queryAllModules();
}