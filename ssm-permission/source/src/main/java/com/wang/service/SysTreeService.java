package com.wang.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.wang.dao.SysAclMapper;
import com.wang.dao.SysAclModuleMapper;
import com.wang.dao.SysDeptMapper;
import com.wang.dto.AclDto;
import com.wang.dto.AclModuleLevelDto;
import com.wang.dto.DeptLevelDto;
import com.wang.model.SysAcl;
import com.wang.model.SysAclModule;
import com.wang.model.SysDept;
import com.wang.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create by wangjf
 * Date 2019/1/3 18:29
 */
@Service
public class SysTreeService {
	@Autowired
	private SysDeptMapper sysDeptMapper;
	@Autowired
	private SysAclModuleMapper sysAclModuleMapper;
	@Autowired
	private SysCoreService sysCoreService;
	@Autowired
	private SysAclMapper sysAclMapper;


	// 通过部门的seq进行比较
	private Comparator<DeptLevelDto> deptSeqComparator = Comparator.comparingInt(DeptLevelDto::getSeq);
	// 通过权限模块的seq进行比较
	private Comparator<AclModuleLevelDto> aclModuleSeqComparator = Comparator.comparingInt(AclModuleLevelDto::getSeq);
	// 通过权限点的seq进行比较
	private Comparator<AclDto> aclSeqComparator = Comparator.comparingInt(AclDto::getSeq);


	/*------------------------------部门模块树--------------------------------*/

	public List<DeptLevelDto> deptTree() {
		// 获取所有部门的信息
		List<SysDept> deptList = sysDeptMapper.getAllDept();
		List<DeptLevelDto> dtoList = Lists.newArrayList();
		for (SysDept dept : deptList) {
			// 将SysDept转为DeptLevelDto
			DeptLevelDto dto = DeptLevelDto.adapt(dept);
			dtoList.add(dto);
		}
		return deptListToTree(dtoList);
	}

	private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelList) {
		if (CollectionUtils.isEmpty(deptLevelList)) {
			return Lists.newArrayList();
		}
		// level -> [dep0t1, dept2, ...] Map<String, List<Object>>
		Multimap<String, DeptLevelDto> levelDeptMap = ArrayListMultimap.create();
		List<DeptLevelDto> rootList = Lists.newArrayList(); // 部门树根

		for (DeptLevelDto dto : deptLevelList) {
			levelDeptMap.put(dto.getLevel(), dto);
			if (LevelUtil.ROOT.equals(dto.getLevel())) {
				rootList.add(dto);
			}
		}
		// 按照seq从小到大排序
		rootList.sort(deptSeqComparator);
		// 递归生成树
		transformDeptTree(rootList, LevelUtil.ROOT, levelDeptMap);
		return rootList;
	}

	private void transformDeptTree(List<DeptLevelDto> deptLevelList, String level, Multimap<String, DeptLevelDto> levelDeptMap) {
		for (DeptLevelDto deptLevelDto : deptLevelList) { // 遍历该层的每个元素
			// 处理当前层级的数据
			String currentLevel = LevelUtil.calculateLevel(level, deptLevelDto.getId());
			// 处理下一层
			List<DeptLevelDto> tempDeptList = (List<DeptLevelDto>) levelDeptMap.get(currentLevel);
			if (CollectionUtils.isNotEmpty(tempDeptList)) {
				// 排序
				tempDeptList.sort(deptSeqComparator);
				// 设置下一层部门
				deptLevelDto.setDeptList(tempDeptList);
				// 进入到下一层处理
				transformDeptTree(tempDeptList, currentLevel, levelDeptMap);
			}
		}
	}

	/*-------------------------------权限模块树--------------------------------*/

	public List<AclModuleLevelDto> aclModuleTree() {
		List<SysAclModule> aclModuleList = sysAclModuleMapper.getAllAclModule();// 获取所有权限模块的信息
		List<AclModuleLevelDto> dtoList = Lists.newArrayList();
		for (SysAclModule aclModule : aclModuleList) {
			AclModuleLevelDto dto = AclModuleLevelDto.adapt(aclModule);// 将SysAclModule转为AclModuleLevelDto
			dtoList.add(dto);
		}
		return aclModuleListToTree(dtoList);
	}

	private List<AclModuleLevelDto> aclModuleListToTree(List<AclModuleLevelDto> dtoList) {
		if (CollectionUtils.isEmpty(dtoList)) {
			return Lists.newArrayList();
		}
		Multimap<String, AclModuleLevelDto> levelAclModuleMap = ArrayListMultimap.create();
		List<AclModuleLevelDto> rootList = Lists.newArrayList();
		for (AclModuleLevelDto dto : dtoList) {
			levelAclModuleMap.put(dto.getLevel(), dto);
			if (LevelUtil.ROOT.equals(dto.getLevel())) {
				rootList.add(dto);
			}
		}
		rootList.sort(aclModuleSeqComparator);
		transformAclModuleTree(rootList, LevelUtil.ROOT, levelAclModuleMap);
		return rootList;
	}

	private void transformAclModuleTree(List<AclModuleLevelDto> aclModuleLevelList, String level, Multimap<String, AclModuleLevelDto> levelAclModuleMap) {
		for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelList) {
			String currentLevel = LevelUtil.calculateLevel(level, aclModuleLevelDto.getId());
			List<AclModuleLevelDto> tempAclModuleList = (List<AclModuleLevelDto>) levelAclModuleMap.get(currentLevel);
			if (CollectionUtils.isNotEmpty(tempAclModuleList)) {
				tempAclModuleList.sort(aclModuleSeqComparator);
				aclModuleLevelDto.setAclModuleList(tempAclModuleList);
				transformAclModuleTree(tempAclModuleList, currentLevel, levelAclModuleMap);
			}
		}
	}

	/*-------------------------------角色树--------------------------------*/

	// 通过角色id得到该角色由权限模块和权限点组成的树
	public List<AclModuleLevelDto> roleTree(int roleId) {
		// 1. 当前用户已分配的权限点
		List<SysAcl> userAclList = sysCoreService.getCurrentUserAclList();
		// 2. 某个角色已分配的权限点
		List<SysAcl> roleAclList = sysCoreService.getRoleAclList(roleId);
		// 3. 当前系统所有有效的权限点
		List<AclDto> aclDtoList = Lists.newArrayList();

		Set<Integer> userAclIdSet = userAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());
		Set<Integer> roleAclIdSet = roleAclList.stream().map(SysAcl::getId).collect(Collectors.toSet());

		List<SysAcl> allAclList = sysAclMapper.getAll(); // 所有权限点

		for (SysAcl sysAcl : allAclList) {
			if (sysAcl.getStatus() == 1) { // 有效的权限点
				AclDto aclDto = AclDto.adapt(sysAcl);
				if (userAclIdSet.contains(sysAcl.getId())) {
					aclDto.setHasAcl(true); // 当前用户有权限设置该权限点
				}
				if (roleAclIdSet.contains(sysAcl.getId())) {
					aclDto.setChecked(true); // 该角色的权限点默认选中
				}
				aclDtoList.add(aclDto);
			}
		}
		return aclDtoListToTree(aclDtoList);
	}

	private List<AclModuleLevelDto> aclDtoListToTree(List<AclDto> aclDtoList) {
		if (CollectionUtils.isEmpty(aclDtoList)) {
			return Lists.newArrayList();
		}
		List<AclModuleLevelDto> aclModuleLevelDtoList = aclModuleTree(); // 所有权限模块组成的树

		Multimap<Integer, AclDto> moduleIdAclDtoMap = ArrayListMultimap.create();
		for (AclDto aclDto : aclDtoList) {
			moduleIdAclDtoMap.put(aclDto.getAclModuleId(), aclDto);
		}
		bindAclsWithOrder(aclModuleLevelDtoList, moduleIdAclDtoMap);
		return aclModuleLevelDtoList;
	}

	// 将权限点和权限模块树绑定在一起
	private void bindAclsWithOrder(List<AclModuleLevelDto> aclModuleLevelDtoList, Multimap<Integer, AclDto> moduleIdAclDtoMap) {
		if (CollectionUtils.isEmpty(aclModuleLevelDtoList)) {
			return;
		}
		for (AclModuleLevelDto aclModuleLevelDto : aclModuleLevelDtoList) {
			List<AclDto> aclDtoList = (List<AclDto>) moduleIdAclDtoMap.get(aclModuleLevelDto.getId());
			if (CollectionUtils.isNotEmpty(aclDtoList)) {
				aclDtoList.sort(aclSeqComparator);
				aclModuleLevelDto.setAclList(aclDtoList);
			}
			bindAclsWithOrder(aclModuleLevelDto.getAclModuleList(), moduleIdAclDtoMap);
		}
	}

	/*----------------------------用户已分配权限点的树------------------------------*/

	public List<AclModuleLevelDto> userAclTree(Integer userId) {
		// 查询一个用户所有权限点的列表
		List<SysAcl> userAclList = sysCoreService.getUserAclList(userId);
		List<AclDto> aclDtoList = Lists.newArrayList();
		for (SysAcl acl : userAclList) {
			AclDto dto = AclDto.adapt(acl);
			dto.setHasAcl(true);
			dto.setChecked(true);
			aclDtoList.add(dto);
		}
		return aclDtoListToTree(aclDtoList); // 将权限点列表转为树结构
	}
}
