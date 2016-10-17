package io.jianxun.business.web.dto;

/**
 * 
 * @author tongtn
 *
 *         createDate: 2016-10-17
 */
public class ReAuditorDto extends AuditorDto {

	// 审核数量
	private Integer auditAmount;

	public Integer getAuditAmount() {
		return auditAmount;
	}

	public void setAuditAmount(Integer auditAmount) {
		this.auditAmount = auditAmount;
	}

}
