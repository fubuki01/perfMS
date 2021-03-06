package com.readboy.ssm.po;

import java.math.BigDecimal;

/**
 * khgxgl_sjyhkhghlsb  手机银行管户信息查询
 * @author Administrator
 *
 */
public class CellBankCustomerPerson {
	private String jgdm;
	private String khbh;
	private String hth;
	private String ghlx;
	private String ghr;
	private String ygxm;		//管户人姓名，通过hr_bas_staff查询
	private BigDecimal ghbl;
	private String ksrq;
	private String jsrq;
	private int lrbz;
	private String lrr;
	private String lrsj;
	public String getJgdm() {
		return jgdm;
	}
	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}
	public String getKhbh() {
		return khbh;
	}
	public void setKhbh(String khbh) {
		this.khbh = khbh;
	}
	public String getHth() {
		return hth;
	}
	public void setHth(String hth) {
		this.hth = hth;
	}
	public String getGhlx() {
		return ghlx;
	}
	public void setGhlx(String ghlx) {
		this.ghlx = ghlx;
	}
	public String getGhr() {
		return ghr;
	}
	public void setGhr(String ghr) {
		this.ghr = ghr;
	}
	public String getYgxm() {
		return ygxm;
	}
	public void setYgxm(String ygxm) {
		this.ygxm = ygxm;
	}
	public BigDecimal getGhbl() {
		return ghbl;
	}
	public void setGhbl(BigDecimal ghbl) {
		this.ghbl = ghbl;
	}
	public String getKsrq() {
		return ksrq;
	}
	public void setKsrq(String ksrq) {
		if(ksrq != null && ksrq.length() > 10){
			ksrq = ksrq.substring(0, 10);
		}
		this.ksrq = ksrq;
	}
	public String getJsrq() {
		return jsrq;
	}
	public void setJsrq(String jsrq) {
		if(jsrq != null && jsrq.length() > 10){
			jsrq = jsrq.substring(0, 10);
		}
		this.jsrq = jsrq;
	}
	public int getLrbz() {
		return lrbz;
	}
	public void setLrbz(int lrbz) {
		this.lrbz = lrbz;
	}
	public String getLrr() {
		return lrr;
	}
	public void setLrr(String lrr) {
		this.lrr = lrr;
	}
	public String getLrsj() {
		return lrsj;
	}
	public void setLrsj(String lrsj) {
		this.lrsj = lrsj;
	}
	
	
}
