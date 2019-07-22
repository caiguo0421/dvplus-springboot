package cn.sf_soft.office.approval.ui.vo;

public class VehicleSummaryVo {

	/**
	 * 车辆型号
	 */
	private String vno;
	
	/**
	 * 新增数量
	 */
	private int newCount;
	
	/**
	 * 修改数量
	 */
	private int modfityCount;
	
	/**
	 * 终止数量
	 */
	private int abortCount;
	
	/**
	 * 预估利润合计
	 */
	private double sumProfitPf;
	
	/**
	 * 原合同中车辆数
	 */
	private int oriVehicleCount;
	
	/**
	 * 车辆数
	 */
	private int vehicleCount;
	
	/**
	 * 预估利润合计
	 */
	private double oriSumProfitPf;
	
	//构造方法
	public VehicleSummaryVo(){
		
	}
	
	public VehicleSummaryVo(String vno, int newCount, int modfityCount, int abortCount, double sumProfitPf,
			int oriVehicleCount,int vehicleCount,double oriSumProfitPf) {
		super();
		this.vno = vno;
		this.newCount = newCount;
		this.modfityCount = modfityCount;
		this.abortCount = abortCount;
		this.sumProfitPf = sumProfitPf;
		this.oriVehicleCount = oriVehicleCount;
		this.vehicleCount = vehicleCount;
		this.oriSumProfitPf = oriSumProfitPf;
	}

	public VehicleSummaryVo(int newCount, int modfityCount, int abortCount, double sumProfitPf,
			int oriVehicleCount,int vehicleCount,double oriSumProfitPf) {
		super();
		this.newCount = newCount;
		this.modfityCount = modfityCount;
		this.abortCount = abortCount;
		this.sumProfitPf = sumProfitPf;
		this.oriVehicleCount = oriVehicleCount;
		this.vehicleCount = vehicleCount;
		this.oriSumProfitPf = oriSumProfitPf;
	}

	public String getVno() {
		return vno;
	}

	public void setVno(String vno) {
		this.vno = vno;
	}

	public int getNewCount() {
		return newCount;
	}

	public void setNewCount(int newCount) {
		this.newCount = newCount;
	}

	public int getModfityCount() {
		return modfityCount;
	}

	public void setModfityCount(int modfityCount) {
		this.modfityCount = modfityCount;
	}

	public int getAbortCount() {
		return abortCount;
	}

	public void setAbortCount(int abortCount) {
		this.abortCount = abortCount;
	}

	public double getSumProfitPf() {
		return sumProfitPf;
	}

	public void setSumProfitPf(double sumProfitPf) {
		this.sumProfitPf = sumProfitPf;
	}

	public int getOriVehicleCount() {
		return oriVehicleCount;
	}

	public void setOriVehicleCount(int oriVehicleCount) {
		this.oriVehicleCount = oriVehicleCount;
	}

	public int getVehicleCount() {
		return vehicleCount;
	}

	public void setVehicleCount(int vehicleCount) {
		this.vehicleCount = vehicleCount;
	}

	public double getOriSumProfitPf() {
		return oriSumProfitPf;
	}

	public void setOriSumProfitPf(double oriSumProfitPf) {
		this.oriSumProfitPf = oriSumProfitPf;
	}

	
}
