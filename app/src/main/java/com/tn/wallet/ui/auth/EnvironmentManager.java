package com.tn.wallet.ui.auth;

import android.util.Log;

import com.tn.wallet.util.AppUtil;
import com.tn.wallet.util.PrefsUtil;

public class EnvironmentManager {
    private static final String TAG = EnvironmentManager.class.getSimpleName();

    public static final String KEY_ENV_PROD = "env_prod";
    public static final String KEY_ENV_TESTNET = "env_testnet";

    private static EnvironmentManager instance;

    private Environment current = Environment.PRODUCTION;

    private PrefsUtil prefsUtil;
    private AppUtil appUtil;

    public EnvironmentManager(PrefsUtil prefsUtil, AppUtil appUtil) {
        this.prefsUtil = prefsUtil;
        this.appUtil = appUtil;
        String storedEnv = prefsUtil.getEnvironment();
        this.current = Environment.fromString(storedEnv);
    }

    public static void init(PrefsUtil prefsUtil, AppUtil appUtil) {
        instance = new EnvironmentManager(prefsUtil, appUtil);
    }

    public static EnvironmentManager get() {
        return instance;
    }

    public boolean shouldShowDebugMenu() {
        return current != Environment.PRODUCTION;
    }

    public Environment current() {
        return current;
    }

    public void setCurrent(Environment current) {
        Log.d(TAG, "setEnvironment: " + current.getName());
        prefsUtil.setGlobalValue(PrefsUtil.GLOBAL_CURRENT_ENVIRONMENT, current.getName());
        this.current = current;
        appUtil.restartApp();
    }

    public enum Environment {
        PRODUCTION(KEY_ENV_PROD, "https://privatenode.blackturtle.eu", "https://privatematcher.blackturtle.eu/", "https://bot.blackturtle.eu/api/", 'L'),
        TESTNET(KEY_ENV_TESTNET, "https://apitnetworktest.blackturtle.eu", "https://unknown.blackturtle.eu/", "https://bot.blackturtle.eu/api/", 'l');

        private String name;
        private String nodeUrl;
        private String matherUrl;
        private String dataFeedUrl;
        private char addressScheme;

        private Environment(String name, String nodeUrl, String matherUrl, String dataFeedUrl,  char addressScheme) {
            this.name = name;
            this.nodeUrl = nodeUrl;
            this.dataFeedUrl = dataFeedUrl;
            this.matherUrl = matherUrl;
            this.addressScheme = addressScheme;
        }

        public String getMatherUrl() {
            return matherUrl;
        }
        public String getName() {
            return name;
        }

        public String getNodeUrl() {
            return nodeUrl;
        }

        public String getDataFeedUrl() {
            return dataFeedUrl;
        }

        public char getAddressScheme() {
            return addressScheme;
        }

        public static Environment fromString(String text) {
            if(text != null) {
                Environment[] all = values();

                for (Environment anAll : all) {
                    if (text.equalsIgnoreCase(anAll.getName())) {
                        return anAll;
                    }
                }
            }

            return null;
        }

    }

}
