package com.tn.wallet.ui.transactions;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.support.annotation.VisibleForTesting;

import com.tn.wallet.R;
import com.tn.wallet.data.datamanagers.TransactionListDataManager;
import com.tn.wallet.injection.Injector;
import com.tn.wallet.payload.ReissueTransaction;
import com.tn.wallet.ui.base.BaseViewModel;
import com.tn.wallet.util.MoneyUtil;
import com.tn.wallet.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import static com.tn.wallet.ui.balance.TransactionsFragment.KEY_TRANSACTION_LIST_POSITION;

@SuppressWarnings("WeakerAccess")
public class ReissueDetailViewModel extends BaseViewModel {

    private DataListener mDataListener;
    @Inject StringUtils mStringUtils;
    @Inject TransactionListDataManager mTransactionListDataManager;

    private Context context;

    @VisibleForTesting
    ReissueTransaction mTransaction;

    public interface DataListener {

        Intent getPageIntent();

        void pageFinish();

    }

    public ReissueDetailViewModel(Context context, DataListener listener) {
        this.context = context;
        Injector.getInstance().getDataManagerComponent().inject(this);
        mDataListener = listener;
    }

    @Override
    public void onViewReady() {
        if (mDataListener.getPageIntent() != null) {
            if (mDataListener.getPageIntent().hasExtra(KEY_TRANSACTION_LIST_POSITION)) {
                mTransaction = (ReissueTransaction) mTransactionListDataManager.getTransactionList().get(
                        mDataListener.getPageIntent().getIntExtra(KEY_TRANSACTION_LIST_POSITION, 0));
            }
        } else {
            mDataListener.pageFinish();
        }
    }

    @Bindable
    public String getAssetName() {
        return mTransaction.getAssetName();
    }

    @Bindable
    public String getQuantity() {
        return MoneyUtil.getScaledText(mTransaction.quantity, mTransaction.getDecimals());
    }

    @Bindable
    public String getIdentifier() {
        return mTransaction.id;
    }

    public boolean isAssetReissuable() {
        return mTransaction.reissuable;
    }

    @Bindable
    public String getReissuable() {
        return  isAssetReissuable() ? "Yes" : "No";
    }

    @Bindable
    public String getTransactionFee() {
        return mStringUtils.getString(R.string.transaction_detail_fee) +
                MoneyUtil.getWavesStripZeros(mTransaction.fee) + " TN";
    }

    @Bindable
    public String getConfirmationStatus() {
        if (mTransaction.isPending) return mStringUtils.getString(R.string.transaction_detail_pending);
        else return mStringUtils.getString(R.string.transaction_detail_confirmed);
    }

    @Bindable
    public String getTransactionDate() {
        Date date = new Date(mTransaction.timestamp);
        DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
        String dateText = dateFormat.format(date);
        String timeText = timeFormat.format(date);

        return dateText + " @ " + timeText;
    }

    @Bindable
    public String getIssuer() {
        return  mTransaction.sender;
    }

    @Bindable
    public String getIssuerLabel() {
        return null;
    }
}
