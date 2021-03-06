package io.jianxun.business.web.dto;

import javax.validation.constraints.NotNull;

/**
 * 密码修改dto
 * 
 * @author tongtn
 *
 *         createDate: 2016-08-16
 */
public class PasswordDto {
	private String oldPassword;
	@NotNull
	private String newPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
