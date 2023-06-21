package com.vaadin.pro.licensechecker;

import com.vaadin.open.Open;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class LicenseChecker {
    private static boolean strictOffline = false;
    static String loggedLicenseOwner = null;
    static Consumer<String> systemBrowserUrlHandler = (url) -> {
        try {
            getLogger().info("Opening system browser to validate license. If the browser is not opened, please open " + url + " manually");
            Open.open(url);
            getLogger().info("For CI/CD build servers, you need to download a server license key, which can only be used for production builds. You can download a server license key from https://vaadin.com/myaccount/licenses.\nIf you are working offline in development mode, please visit " + OfflineKeyValidator.getOfflineUrl(MachineId.get()) + " for an offline development mode license.");
        } catch (Exception var2) {
            getLogger().error("Error opening system browser to validate license. Please open " + url + " manually", var2);
        }

    };

    public LicenseChecker() {
    }

    /** @deprecated */
    @Deprecated
    public static void checkLicenseFromStaticBlock(String productName, String productVersion) {
        checkLicenseFromStaticBlock(productName, productVersion, BuildType.DEVELOPMENT);
    }

    public static void checkLicenseFromStaticBlock(String productName, String productVersion, BuildType buildType) {
        try {
            checkLicense(productName, productVersion, buildType);
        } catch (Exception var4) {
            throw new ExceptionInInitializerError(var4);
        }
    }

    /** @deprecated */
    @Deprecated
    public static void checkLicense(String productName, String productVersion) {
        checkLicense(productName, productVersion, BuildType.DEVELOPMENT, systemBrowserUrlHandler);
    }

    public static void checkLicense(String productName, String productVersion, BuildType buildType) {
        checkLicense(productName, productVersion, buildType, systemBrowserUrlHandler);
    }

    @Deprecated
    public static void checkLicense(String productName, String productVersion, Consumer<String> noKeyUrlHandler) {
        checkLicense(productName, productVersion, BuildType.DEVELOPMENT, noKeyUrlHandler);
    }

    public static void checkLicense(String productName, String productVersion, BuildType buildType, Consumer<String> noKeyUrlHandler) {
        checkLicense(new Product(productName, productVersion), buildType, noKeyUrlHandler);
    }

    /** @deprecated */
    @Deprecated
    public static void checkLicenseAsync(String productName, String productVersion, Callback callback) {
        checkLicenseAsync(productName, productVersion, BuildType.DEVELOPMENT, callback);
    }

    public static void checkLicenseAsync(String productName, String productVersion, BuildType buildType, Callback callback) {
        checkLicenseAsync(productName, productVersion, buildType, callback, systemBrowserUrlHandler);
    }

    /** @deprecated */
    @Deprecated
    public static void checkLicenseAsync(String productName, String productVersion, Callback callback, Consumer<String> noKeyUrlHandler) {
    }

    public static void checkLicenseAsync(String productName, String productVersion, BuildType buildType, Callback callback, Consumer<String> noKeyUrlHandler) {
        checkLicenseAsync(new Product(productName, productVersion), buildType, callback, noKeyUrlHandler, MachineId.get(), LocalProKey.get(), LocalOfflineKey.get(), new OnlineKeyValidator(), new OfflineKeyValidator(), new VaadinComIntegration());
    }

    static void checkLicenseAsync(Product product, BuildType buildType, Callback callback, Consumer<String> noKeyUrlHandler, String machineId, ProKey proKey, OfflineKey offlineKey, OnlineKeyValidator proKeyValidator, OfflineKeyValidator offlineKeyValidator, VaadinComIntegration vaadinComIntegration) {
        (new Thread(() -> {
            try {
                callback.ok();
            } catch (Exception var11) {
                callback.failed(var11);
            }

        })).start();
    }

    static void checkLicense(Product product, BuildType buildType, Consumer<String> noKeyUrlHandler) {
    }

    static void checkLicense(Product product, BuildType buildType, Consumer<String> noKeyUrlHandler, String machineId, ProKey proKey, OfflineKey offlineKey, OnlineKeyValidator proKeyValidator, OfflineKeyValidator offlineKeyValidator, VaadinComIntegration vaadinComIntegration) {
    }

    public static Logger getLogger() {
        return LoggerFactory.getLogger(LicenseChecker.class);
    }

    public static void setStrictOffline(boolean strictOffline) {
        LicenseChecker.strictOffline = strictOffline;
    }

    public interface Callback {
        void ok();

        void failed(Exception var1);
    }
}
