package io.jianxun.common.domain.user;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.jianxun.business.web.dto.CodeNameDto;

//操作定义
public enum PermissionDef {
	// 装备基本信息;
	WEAPON_PAGE("weapon_page", "列表", ModuleDef.STOCK, DomainDef.STOCK_WEAPON), WEAPON_CREATE("weapon_create", "新增",
			ModuleDef.STOCK, DomainDef.STOCK_WEAPON), WEAPON_MODIFY("weapon_modify", "修改", ModuleDef.STOCK,
					DomainDef.STOCK_WEAPON), WEAPON_REMOVE("weapon_remove", "删除", ModuleDef.STOCK,
							DomainDef.STOCK_WEAPON),
	// 库存管理
	STOCK_PAGE("stock_page", "列表", ModuleDef.STOCK, DomainDef.STOCK_STOCK), STOCK_IN_CREATE("stockin_create", "直接入库",
			ModuleDef.STOCK, DomainDef.STOCK_STOCK), STOCK_IN_PAGE("stockin_page", "入库单明细", ModuleDef.STOCK,
					DomainDef.STOCK_STOCK), STOCK_IN_DETAIL_REMOVE("stockindetail_page", "库存明细", ModuleDef.STOCK,
							DomainDef.STOCK_STOCK),
	// ----组织管理
	// --机构管理
	DEPARTMENT_PAGE("department_page", "列表", ModuleDef.ORGANIZATION, DomainDef.ORG_DEPART), DEPARTMENT_CREATE(
			"department_create", "新增", ModuleDef.ORGANIZATION, DomainDef.ORG_DEPART), DEPARTMENT_MODIFY(
					"department_modify", "修改", ModuleDef.ORGANIZATION, DomainDef.ORG_DEPART), DEPARTMENT_REMOVE(
							"department_remove", "删除", ModuleDef.ORGANIZATION, DomainDef.ORG_DEPART),
	// --警员管理
	CONSTABLE_PAGE("constable_page", "列表", ModuleDef.ORGANIZATION, DomainDef.ORG_CONSTABLE), CONSTABLE_CREATE(
			"constable_create", "新增", ModuleDef.ORGANIZATION, DomainDef.ORG_CONSTABLE), CONSTABLE_MODIFY(
					"constable_modify", "修改", ModuleDef.ORGANIZATION, DomainDef.ORG_CONSTABLE), CONSTABLE_REMOVE(
							"constable_remove", "删除", ModuleDef.ORGANIZATION, DomainDef.ORG_CONSTABLE),
	// 用户管理相关权限
	USER_PAGE("user_page", "列表", ModuleDef.SYS, DomainDef.SYS_USER), USER_CREATE("user_create", "新增", ModuleDef.SYS,
			DomainDef.SYS_USER), USER_MODIFY("user_modify", "修改", ModuleDef.SYS, DomainDef.SYS_USER), USER_REMOVE(
					"user_remove", "删除", ModuleDef.SYS, DomainDef.SYS_USER), USER_RESETPASSWORD("user_resetpassword",
							"重置密码", ModuleDef.SYS,
							DomainDef.SYS_USER), USER_LOCKED("user_locked", "锁定", ModuleDef.SYS, DomainDef.SYS_USER),
	// 角色
	ROLE_PAGE("role_page", "列表", ModuleDef.SYS, DomainDef.SYS_ROLE), ROLE_CREATE("role_create", "新增", ModuleDef.SYS,
			DomainDef.SYS_ROLE), ROLE_MODIFY("role_modify", "修改", ModuleDef.SYS,
					DomainDef.SYS_ROLE), ROLE_REMOVE("role_remove", "删除", ModuleDef.SYS, DomainDef.SYS_ROLE),
	// 数据字典
	DATADIC_PAGE("datadic_page", "列表", ModuleDef.SYS, DomainDef.SYS_DATADIC), DATADIC_CREATE("datadic_create", "新增",
			ModuleDef.SYS, DomainDef.SYS_DATADIC), DATADIC_MODIFY("datadic_modify", "修改", ModuleDef.SYS,
					DomainDef.SYS_DATADIC), DATADIC_REMOVE("datadic_remove", "删除", ModuleDef.SYS,
							DomainDef.SYS_DATADIC),
	// 申请
	REQUESTFORM_PAGE("requestform_page", "列表", ModuleDef.REQUESTFORM,
			DomainDef.REQUESTFORM_REQUESTFORM), REQUESTFORM_CREATE("requestform_create", "新增", ModuleDef.REQUESTFORM,
					DomainDef.REQUESTFORM_REQUESTFORM), REQUESTFORM_MODIFY("requestform_modify", "修改", ModuleDef.REQUESTFORM,
							DomainDef.REQUESTFORM_REQUESTFORM), REQUESTFORM_REMOVE("requestform_remove", "删除", ModuleDef.REQUESTFORM,
									DomainDef.REQUESTFORM_REQUESTFORM), REQUESTFORM_UP("requestform_up", "提交", ModuleDef.REQUESTFORM,
							DomainDef.REQUESTFORM_REQUESTFORM), REQUESTFORM_AUDITMESSAGE("requestform_auditmessage",
									"查看审核信息", ModuleDef.REQUESTFORM,
									DomainDef.REQUESTFORM_REQUESTFORM),
//	审核
	REQUESTFORM_AUDIT("requestform_audit","待审核", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_COMMIT),REQUESTFORM_COMMIT("requestform_commit","审核", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_COMMIT),
	//登记
	REQUESTFORM_ENROLLMENT("requestform_enrollment","待登记", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_ENROLLMENT),REQUESTFORM_OUT("requestform_out","选择登记", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_ENROLLMENT),REQUESTFORM_SYSOUT("requestform_sysout","系统登记", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_ENROLLMENT),
	//领用
	REQUESTFORM_FINISH("requestform_finish","待领用", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_FINISH),REQUESTFORM_FINISHED("requestform_finished","确认", ModuleDef.REQUESTFORM,DomainDef.REQUESTFORM_FINISH),
	// 维护提醒
	NOTICE_MAINTAIN_PAGE("weaponnotice_page", "列表", ModuleDef.NOTICE, DomainDef.NOTICE_MAINTAIN),NOTICE_MAINTAIN_MAINTAIN("weaponnotice_maintain", "维护确认", ModuleDef.NOTICE, DomainDef.NOTICE_MAINTAIN),
	// 报废提醒
	NOTICE_SCRAP_PAGE("rweaponnotice_page", "列表", ModuleDef.NOTICE, DomainDef.NOTICE_SCRAP),NOTICE_SCRAP_SCRAP("rweaponnotice_scrap", "报废确认", ModuleDef.NOTICE, DomainDef.NOTICE_SCRAP),
	// 库存提醒
	NOTICE_STOCK_PAGE("notice_stock", "列表", ModuleDef.NOTICE, DomainDef.NOTICE_STOCK),
	// 归还提醒
	NOTICE_ESCHEAT_PAGE("backnotice_page", "列表", ModuleDef.NOTICE, DomainDef.NOTICE_ESCHEAT),NOTICE_ESCHEAT_ESCHEAT("backnotice_escheat", "归还", ModuleDef.NOTICE, DomainDef.NOTICE_ESCHEAT);
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
		SYS("sys", "系统设置", 99), ORGANIZATION("org", "组织管理", 7), SCRAP("scrap", "器械报废管理", 5), REQUESTFORM("requestform",
				"器械领用(借用)管理", 3), STOCK("stock", "器械库存管理", 0), NOTICE("notice", "提醒信息", 9);
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
		SYS_USER("sys_user", "用户管理", 0), SYS_ROLE("sys_role", "角色管理", 10), SYS_DATADIC("sys_datadic", "数据字典", 20),
		// 库存配置
		STOCK_WEAPON("stock_weapon", "装备基本信息", 0), STOCK_STOCK("stock_stock", "库存管理", 10),
		// 组织配置
		ORG_DEPART("org_department", "机构管理", 0), ORG_CONSTABLE("org_constable", "警员管理", 10),
		// 领用
		REQUESTFORM_REQUESTFORM("requestform_requestform", "装备器械申请", 0), REQUESTFORM_COMMIT("requestform_commit",
				"装备器械审核", 10), REQUESTFORM_ENROLLMENT("requestform_enrollment", "装备器械登记",
						20), REQUESTFORM_FINISH("requestform_finish", "装备器械发放", 20),
		// 提醒
		NOTICE_MAINTAIN("notice_maintain", "维护提醒", 0), NOTICE_SCRAP("notice_scrap", "报废提醒",
				10), NOTICE_STOCK("notice_stock", "库存提醒", 20), NOTICE_ESCHEAT("notice_escheat", "归还提醒", 20);
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
