package com.tyebile.meereen.starter.spring.boot.init.simple;

import com.tyebile.meereen.starter.spring.boot.SystemVersion;
import com.tyebile.meereen.starter.spring.boot.init.DependencyInstaller;
import com.tyebile.meereen.starter.spring.boot.init.InitializeCallBack;
import com.tyebile.meereen.starter.spring.boot.init.InstallerCallBack;
import com.tyebile.meereen.starter.spring.boot.init.UpgradeCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * TODO 完成注释
 *
 * @author zhouhao
 */
public class SimpleDependencyInstaller implements DependencyInstaller {
    SystemVersion.Dependency dependency;
    InstallerCallBack        installer;
    UpgradeCallBack          upgrader;
    InstallerCallBack        unInstaller;
    InitializeCallBack       initializer;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SimpleDependencyInstaller() {
    }

    public SystemVersion.Dependency getDependency() {
        return dependency;
    }

    public void doInstall(Map<String, Object> context) {
        if (installer != null) {
            if (logger.isInfoEnabled()) {
                logger.info("install [{}/{}] version {} {}", dependency.getGroupId(), dependency.getArtifactId(), dependency.versionToString(), dependency.getWebsite());
            }
            installer.execute(context);
        }
    }

    public void doInitialize(Map<String, Object> context) {
        if (initializer != null) {
            if (logger.isInfoEnabled()) {
                logger.info("initialize [{}/{}] version {} {}", dependency.getGroupId(), dependency.getArtifactId(), dependency.versionToString(), dependency.getWebsite());
            }
            initializer.execute(context);
        }
    }

    public void doUnInstall(Map<String, Object> context) {
        if (unInstaller != null) {
            installer.execute(context);
        }
    }

    public void doUpgrade(Map<String, Object> context, SystemVersion.Dependency installed) {
        SimpleDependencyUpgrader simpleDependencyUpgrader =
                new SimpleDependencyUpgrader(installed, dependency, context);
        context.put("upgrader", simpleDependencyUpgrader);
        if (unInstaller != null) {
            upgrader.execute(context);
        }
    }

    @Override
    public DependencyInstaller setup(SystemVersion.Dependency dependency) {
        this.dependency = dependency;
        return this;
    }

    @Override
    public DependencyInstaller onInstall(InstallerCallBack callBack) {
        this.installer = callBack;
        return this;
    }

    @Override
    public DependencyInstaller onUpgrade(UpgradeCallBack callBack) {
        this.upgrader = callBack;
        return this;
    }

    @Override
    public DependencyInstaller onUninstall(InstallerCallBack callBack) {
        this.unInstaller = callBack;
        return this;
    }

    @Override
    public DependencyInstaller onInitialize(InitializeCallBack initializeCallBack) {
        this.initializer = initializeCallBack;
        return this;
    }
}
