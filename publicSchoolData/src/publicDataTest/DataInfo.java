package publicDataTest;

public class DataInfo {
	private int sn;
	private String districtName;
	private String dataDate;
	private int issueVal;
	private String issueTime;
	private String clearDate;
	private String issueDate;
	private String moveName;
	private String clearTime;
	private String issueGbn;
	private String itemCode;
	private int clearVal;
	private String curdate;
	
	public DataInfo() {
		super();
	}
	
	public DataInfo(int sn, String districtName, String dataDate, int issueVal, String issueTime, String clearDate,
			String issueDate, String moveName, String clearTime, String issueGbn, String itemCode, int clearVal,
			String curdate) {
		super();
		this.sn = sn;
		this.districtName = districtName;
		this.dataDate = dataDate;
		this.issueVal = issueVal;
		this.issueTime = issueTime;
		this.clearDate = clearDate;
		this.issueDate = issueDate;
		this.moveName = moveName;
		this.clearTime = clearTime;
		this.issueGbn = issueGbn;
		this.itemCode = itemCode;
		this.clearVal = clearVal;
		this.curdate = curdate;
	}

	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public int getIssueVal() {
		return issueVal;
	}

	public void setIssueVal(int issueVal) {
		this.issueVal = issueVal;
	}

	public String getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(String issueTime) {
		this.issueTime = issueTime;
	}

	public String getClearDate() {
		return clearDate;
	}

	public void setClearDate(String clearDate) {
		this.clearDate = clearDate;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getMoveName() {
		return moveName;
	}

	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}

	public String getClearTime() {
		return clearTime;
	}

	public void setClearTime(String clearTime) {
		this.clearTime = clearTime;
	}

	public String getIssueGbn() {
		return issueGbn;
	}

	public void setIssueGbn(String issueGbn) {
		this.issueGbn = issueGbn;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getClearVal() {
		return clearVal;
	}

	public void setClearVal(int clearVal) {
		this.clearVal = clearVal;
	}

	public String getCurdate() {
		return curdate;
	}

	public void setCurdate(String curdate) {
		this.curdate = curdate;
	}

	@Override
	public String toString() {
		return "sn=" + sn + ", districtName=" + districtName + ", dataDate=" + dataDate + ", issueVal="
				+ issueVal + ", issueTime=" + issueTime + ", clearDate=" + clearDate + ", issueDate=" + issueDate
				+ ", moveName=" + moveName + ", clearTime=" + clearTime + ", issueGbn=" + issueGbn + ", itemCode="
				+ itemCode + ", clearVal=" + clearVal + ", curdate=" + curdate;
	}

	
}
