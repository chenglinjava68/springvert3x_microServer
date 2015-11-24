package com.solodream.spring.vertx.jpa.domain;

import com.solodream.spring.vertx.jpa.model.base.BaseBean;

/**
 * ClientVersionInfoDto
 *
 * @author Young
 * @date 2015/11/24 0024
 */

public class ClientVersionInfoDto extends BaseBean {

    private static final long serialVersionUID = -1L;

    /**
     * 版本代码
     */
    private Integer versionCode;

    /**
     * 版本名称
     */
    private String versionName;

    /**
     * 描述
     */
    private String description;

    /**
     * 升级类型（0:可选升级 / 1:强制升级）
     */
    private Integer upgradeType;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(Integer upgradeType) {
        this.upgradeType = upgradeType;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
