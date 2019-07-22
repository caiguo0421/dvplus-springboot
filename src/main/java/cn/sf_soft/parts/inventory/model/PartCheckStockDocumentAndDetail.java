package cn.sf_soft.parts.inventory.model;

import java.util.List;

/**
 * 新增的盘存单与明细
 * @author cw
 * @date 2014-4-16 上午11:54:49
 */
public class PartCheckStockDocumentAndDetail {
	
	private PartCheckStocks document;
	private List<PartCheckStocksDetail> datail;
	
	public PartCheckStockDocumentAndDetail(){
		
	}
	public PartCheckStockDocumentAndDetail(PartCheckStocks document,
			List<PartCheckStocksDetail> datail) {
		super();
		this.document = document;
		this.datail = datail;
	}
	public PartCheckStocks getDocument() {
		return document;
	}
	public void setDocument(PartCheckStocks document) {
		this.document = document;
	}
	public List<PartCheckStocksDetail> getDatail() {
		return datail;
	}
	public void setDatail(List<PartCheckStocksDetail> datail) {
		this.datail = datail;
	}
	
	
}
