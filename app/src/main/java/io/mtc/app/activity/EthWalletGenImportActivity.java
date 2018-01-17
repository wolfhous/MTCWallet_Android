package io.mtc.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import io.mtc.app.R;
import io.mtc.app.fragment.EthWalletFromKeystoreFragment;
import io.mtc.app.fragment.EthWalletFromNewFragment;
import io.mtc.app.fragment.EthWalletFromPrivateKeyFragment;

import java.util.LinkedList;
import java.util.List;

public class EthWalletGenImportActivity extends AppCompatActivity {

    private final static int FRAGMENT_ETH_WALLET_FROM_NEW = 0;
    private final static int FRAGMENT_ETH_WALLET_FROM_PRIVATE_KEY = 1;
    private final static int FRAGMENT_ETH_WALLET_FROM_JSON_FILE = 2;

    private Toolbar toolbar;
    private TextView tvTitle;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private List<SectionFragmentInfo> fragmentInfoList = new LinkedList<>();
    private Handler handler = new Handler();
    private Handler backgroundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eth_wallet_gen_import);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tvTitle = (TextView) findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(R.string.title_wallet_import);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentInfoList.add(new SectionFragmentInfo(FRAGMENT_ETH_WALLET_FROM_PRIVATE_KEY, 0, R.string.title_eth_wallet_from_private_key));
        fragmentInfoList.add(new SectionFragmentInfo(FRAGMENT_ETH_WALLET_FROM_JSON_FILE, 0, R.string.title_eth_wallet_from_keystore));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(fragmentInfoList.size());

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        handlerThread.start();
        backgroundHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {

                }
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SectionFragmentInfo info = fragmentInfoList.get(position);
            switch (info.id) {
                case FRAGMENT_ETH_WALLET_FROM_PRIVATE_KEY: {
                    return EthWalletFromPrivateKeyFragment.newInstance();
                }

                case FRAGMENT_ETH_WALLET_FROM_JSON_FILE: {
                    return EthWalletFromKeystoreFragment.newInstance();
                }
            }
            return EthWalletFromNewFragment.newInstance();
        }

        @Override
        public int getCount() {
            return fragmentInfoList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            SectionFragmentInfo info = fragmentInfoList.get(position);
            return getResources().getString(info.title_string_id);
        }
    }

    private class SectionFragmentInfo {
        int id;
        int icon_drawable_id;
        int title_string_id;

        public SectionFragmentInfo(int id, int icon_drawable_id, int title_string_id) {
            this.id = id;
            this.icon_drawable_id = icon_drawable_id;
            this.title_string_id = title_string_id;
        }
    }

    private HandlerThread handlerThread = new HandlerThread("EthWalletTask");

}
