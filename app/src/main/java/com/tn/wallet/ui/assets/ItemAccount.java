package com.tn.wallet.ui.assets;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tn.wallet.payload.AssetBalance;

public class ItemAccount {

    @NonNull public String label;
    @NonNull public String displayBalance;
    @Nullable public Long absoluteBalance;

    @Nullable public AssetBalance accountObject;

    public ItemAccount(@NonNull String label,
                       @NonNull String displayBalance,
                       @Nullable Long absoluteBalance,
                       @Nullable AssetBalance accountObject) {
        this.label = label;
        this.displayBalance = displayBalance;
        this.absoluteBalance = absoluteBalance;
        this.accountObject = accountObject;
    }
}
