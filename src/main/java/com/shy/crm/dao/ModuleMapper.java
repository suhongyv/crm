package com.shy.crm.dao;

import com.shy.base.BaseMapper;
import com.shy.crm.dto.TreeDto;
import com.shy.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeDto>  queryAllModules();

    Module  queryModuleByGradeAndModuleName(@Param("grade") Integer grade, @Param("moduleName") String moduleName);

    Module queryModuleByGradeAndUrl(@Param("grade") Integer grade,@Param("url") String url);

    Module  queryModuleByOpValue(String optValue);

    int   countSubModByParentId(Integer id);

    List<Map<String, Object>>  queryAllModulesByGrade(Integer grade);
}