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
    private Integer versionCode;
    private String versionName;

    private String description;

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
