package cn.sf_soft.report.model;

import java.io.Serializable;

public class FinanceType implements Serializable{

		private String code;
		private String meaning;
		public FinanceType(){
			
		}
		public FinanceType(String code, String meaning) {
			super();
			this.code = code;
			this.meaning = meaning;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMeaning() {
			return meaning;
		}
		public void setMeaning(String meaning) {
			this.meaning = meaning;
		}
		
		
}
