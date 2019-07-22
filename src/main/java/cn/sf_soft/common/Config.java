package cn.sf_soft.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 系统的一些设置选项
 * 
 * @创建人 LiuJin
 * @创建时间 2014-9-10 上午11:04:01
 * @修改人
 * @修改时间
 */
public class Config {
	/**
	 * 是否开启手机验证
	 */
	private boolean isMobileAuthOn = true;
	/**
	 * 是否开启自动产生机制凭证
	 */
	private boolean isAutoVoucherOn = true;

	/***
	 * 服务端支持的模块的ID
	 */
	private List<String> moduleIds;

	private Map<String, String> applicationConfig;

	public Config(boolean isMobileAuthOn, boolean isAutoVoucherOn, List<String> moduleIds) {
		super();
		this.isMobileAuthOn = isMobileAuthOn;
		this.isAutoVoucherOn = isAutoVoucherOn;
		this.moduleIds = moduleIds;

		this.applicationConfig = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream in = getClass().getClassLoader().getResourceAsStream("application.properties");
		try {
            prop.load(in);
            for (Object o : prop.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                this.applicationConfig.put(entry.getKey().toString(), entry.getValue().toString());
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
	}

	// 手机认证的开关去掉 caigx 20160422
	public Config(boolean isAutoVoucherOn, List<String> moduleIds) {
		super();
		this.isAutoVoucherOn = isAutoVoucherOn;
		this.moduleIds = moduleIds;
	}

	public boolean isMobileAuthOn() {
		return isMobileAuthOn;
	}

	public boolean isAutoVoucherOn() {
		return isAutoVoucherOn;
	}

	public List<String> getModuleIds() {
		return moduleIds;
	}

	public String getApplicationConfig(String key){
		return this.applicationConfig.get(key);
	}
}
