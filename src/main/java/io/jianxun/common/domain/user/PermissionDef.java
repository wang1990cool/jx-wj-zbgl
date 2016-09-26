package io.jianxun.common.domain.user;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.jianxun.business.web.dto.CodeNameDto;

//操作定义
public enum PermissionDef {
	// 用户管理相关权限
	USER_PAGE("user_page", "用户列表", ModuleDef.SYS, DomainDef.SYS_USER), USER_CREATE("user_create", "用户新增", ModuleDef.SYS,
			DomainDef.SYS_USER), USER_MODIFY("user_modify", "用户修改", ModuleDef.SYS,
					DomainDef.SYS_USER), USER_REMOVE("user_remove", "用户删除", ModuleDef.SYS, DomainDef.SYS_USER),
	//角色
	ROLE_PAGE("role_page", "角色列表", ModuleDef.SYS, DomainDef.SYS_ROLE), ROLE_CREATE("role_create", "角色新增", ModuleDef.SYS,
			DomainDef.SYS_ROLE), ROLE_MODIFY("role_modify", "角色修改", ModuleDef.SYS,
					DomainDef.SYS_ROLE), ROLE_REMOVE("role_remove", "角色删除", ModuleDef.SYS, DomainDef.SYS_ROLE),
	//数据字典
	DATADIC_PAGE("datadic_page", "字典列表", ModuleDef.SYS, DomainDef.SYS_DATADIC), DATADIC_CREATE("datadic_create", "字典新增", ModuleDef.SYS,
				DomainDef.SYS_DATADIC), DATADIC_MODIFY("datadic_modify", "字典修改", ModuleDef.SYS,
						DomainDef.SYS_DATADIC), DATADIC_REMOVE("datadic_remove", "字典删除", ModuleDef.SYS, DomainDef.SYS_DATADIC);
	//;

	// 操作代码
	private String code;
	// 描述
	private String name;
	// 模块
	private ModuleDef module;

	// 模型代码
	private DomainDef domain;

	private PermissionDef(String code, String name, ModuleDef module, DomainDef domain) {
		this.code = code;
		this.name = name;
		this.module = module;
		this.domain = domain;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModuleDef getModule() {
		return module;
	}

	public void setModule(ModuleDef module) {
		this.module = module;
	}

	public DomainDef getDomain() {
		return domain;
	}

	public void setDomain(DomainDef domain) {
		this.domain = domain;
	}

	private static Map<String, PermissionDef> valueMaps = Maps.newTreeMap();
	private static Map<String, List<CodeNameDto>> persMap = Maps.newTreeMap();

	static {
		for (PermissionDef def : PermissionDef.values()) {
			valueMaps.put(def.code, def);
			if (persMap.containsKey(def.getDomain().getCode())) {
				List<CodeNameDto> v = persMap.get(def.getDomain().getCode());
				v.add(converToCodeName(def));
			} else
				persMap.put(def.getDomain().getCode(), Lists.newArrayList(converToCodeName(def)));
		}
	}

	// 根据操作代码获取权限定义
	public static PermissionDef parse(String code) {
		return valueMaps.get(code);
	}

	private static CodeNameDto converToCodeName(PermissionDef def) {
		CodeNameDto dto = new CodeNameDto();
		dto.setCode(def.getCode());
		dto.setName(def.getName());
		return dto;
	}

	public static Map<String, List<CodeNameDto>> getPermission() {
		return persMap;
	}

	// 模块定义
	public enum ModuleDef {
		SYS("sys", "系统设置", 99), DEPART("depart", "机构及警员管理", 7), SCRAP("scrap", "器械报废管理", 5), CONSUMING("consuming",
				"器械领用(借用)管理", 3), STOCK("stock", "器械库存管理", 0);
		private String code;
		private String name;
		private Integer sortNum = 99;

		private ModuleDef(String code, String name, Integer sortNum) {
			this.code = code;
			this.name = name;
			this.sortNum = sortNum;
		}

		private static Map<String, ModuleDef> valueMaps = Maps.newHashMap();

		static {
			for (ModuleDef category : ModuleDef.values()) {
				valueMaps.put(category.code, category);
			}
		}

		public static ModuleDef parse(String code) {
			return valueMaps.get(code);
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getSortNum() {
			return sortNum;
		}

		public void setSortNum(Integer sortNum) {
			this.sortNum = sortNum;
		}

	}

	// 模型定义
	public enum DomainDef {
		// 系统配置
		SYS_USER("sys_user", "用户管理", 0), SYS_ROLE("sys_role", "角色管理", 10), SYS_DATADIC("sys_datadic", "数据字典", 20);
		private String code;
		private String name;
		private Integer sortNum = 99;

		private DomainDef(String code, String name, Integer sortNum) {
			this.code = code;
			this.name = name;
			this.sortNum = sortNum;
		}

		private static Map<String, DomainDef> valueMaps = Maps.newHashMap();
		private static List<CodeNameDto> v = Lists.newArrayList();

		static {
			for (DomainDef def : DomainDef.values()) {
				valueMaps.put(def.code, def);
				v.add(converToCodeName(def));
			}
		}

		private static CodeNameDto converToCodeName(DomainDef def) {
			CodeNameDto dto = new CodeNameDto();
			dto.setCode(def.getCode());
			dto.setName(def.getName());
			return dto;
		}

		public static DomainDef parse(String code) {
			return valueMaps.get(code);
		}

		public static List<CodeNameDto> getDomainDefs() {
			return v;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getSortNum() {
			return sortNum;
		}

		public void setSortNum(Integer sortNum) {
			this.sortNum = sortNum;
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

}
