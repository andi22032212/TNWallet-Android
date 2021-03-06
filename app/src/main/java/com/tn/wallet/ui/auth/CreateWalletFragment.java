package com.tn.wallet.ui.auth;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tn.wallet.R;
import com.tn.wallet.data.auth.WalletManager;
import com.tn.wallet.databinding.FragmentCreateWalletBinding;
import com.tn.wallet.util.annotations.Thunk;

public class CreateWalletFragment extends Fragment {

    public static String KEY_INTENT_SEED = "intent_seed";

    @Thunk
    FragmentCreateWalletBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_wallet, container, false);

        binding.tvWalletNotice.setText(Html.fromHtml(getString(R.string.wallet_notice_html)));
        getActivity().setTitle(getString(R.string.wallet_notice_title));

        binding.commandNext.setOnClickListener(v -> goToNextActivity());

        return binding.getRoot();
    }

    private void goToNextActivity() {
        Intent intent = new Intent(getActivity(), SeedWalletActivity.class);
        intent.putExtra(KEY_INTENT_SEED, WalletManager.createWalletSeed(getActivity()));
        getActivity().startActivity(intent);
    }

}
