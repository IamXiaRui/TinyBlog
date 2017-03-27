package com.tinyblog.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.tinyblog.R;
import com.tinyblog.activity.AboutActivity;
import com.tinyblog.activity.CollectPostListActivity;
import com.tinyblog.activity.DraftActivity;
import com.tinyblog.activity.PersonResumeActivity;
import com.tinyblog.activity.SupportActivity;
import com.tinyblog.base.BaseFragment;
import com.tinyblog.bean.AdminBean;
import com.tinyblog.bean.WeatherBean;
import com.tinyblog.db.CollectModel;
import com.tinyblog.db.NewPostModel;
import com.tinyblog.sys.Url;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;

/**
 * @author xiarui
 * @date 2016/10/11 11:42
 * @desc 我的页面
 */
public class MeFragment extends BaseFragment {
    private RelativeLayout mResumeRLayout;
    private RelativeLayout mAboutRLayout;
    private RelativeLayout mSupportRLayout;
    private RelativeLayout mWebRLayout;
    private RelativeLayout mAdminRLayout;
    private RelativeLayout mCollectRLayout;
    private RelativeLayout mDraftRLayout;
    private ImageView mNowWeatherImage, mTomWeatherImage;
    private TextView mNowTempText, mNowTimeText, mTomTempText, mAqiText, mWindText, mWindPowerText;
    private ImageView mRefreshImage;
    private boolean REFRESH_TAG = false;
    private TextView mBuildWebTime;
    private TextView mSloganText;
    private ImageView mHeaderImage;
    private TextView mCollectText;
    private TextView mAdminText;
    private AdminBean mAdminBean;
    private TextView mDraftText;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {

        mSloganText = (TextView) findViewById(R.id.tv_me_slogan);
        mHeaderImage = (ImageView) findViewById(R.id.iv_me_header);
        mCollectText = (TextView)findViewById(R.id.tv_me_collect);

        mRefreshImage = (ImageView) findViewById(R.id.iv_me_refresh);
        mResumeRLayout = (RelativeLayout) findViewById(R.id.rl_me_resume);
        mAboutRLayout = (RelativeLayout) findViewById(R.id.rl_me_about);
        mSupportRLayout = (RelativeLayout) findViewById(R.id.rl_me_support);

        mWebRLayout = (RelativeLayout) findViewById(R.id.rl_me_web);
        mAdminRLayout = (RelativeLayout) findViewById(R.id.rl_me_admin);
        mCollectRLayout = (RelativeLayout) findViewById(R.id.rl_me_collect);
        mDraftRLayout = (RelativeLayout) findViewById(R.id.rl_me_draft);

        mNowWeatherImage = (ImageView) findViewById(R.id.iv_weather_now);
        mTomWeatherImage = (ImageView) findViewById(R.id.iv_weather_tom);
        mNowTempText = (TextView) findViewById(R.id.tv_weather_now_temp);
        mNowTimeText = (TextView) findViewById(R.id.tv_weather_now_time);
        mTomTempText = (TextView) findViewById(R.id.tv_weather_tom_temp);
        mAqiText = (TextView) findViewById(R.id.tv_weather_aqi);
        mWindText = (TextView) findViewById(R.id.tv_weather_wind);
        mWindPowerText = (TextView) findViewById(R.id.tv_weather_power);
        mBuildWebTime = (TextView) findViewById(R.id.tv_me_web_time);
        mAdminText = (TextView) findViewById(R.id.tv_me_admin);
        mDraftText = (TextView) findViewById(R.id.tv_me_draft);
    }

    @Override
    public void initData() {
        mBuildWebTime.setText(getDateDifferDays() + "天");
        SharedPreferences readSP = getContext().getSharedPreferences("person_slogan", Context.MODE_PRIVATE);
        String sloganStr = readSP.getString("MY_SLOGAN", "在能驾驭的领域，做个自由的行者");
        mSloganText.setText(sloganStr);
        loadWeatherFromNet();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCollectText.setText(queryHowManyCollectFromDB() + "篇");
        mDraftText.setText(queryHowManyDraftFromDB() + "篇");
        loadAdminFromNet();
    }

    /**
     * 从数据库查询有几篇收藏
     */
    private String queryHowManyCollectFromDB() {
        return String.valueOf(new Select().from(CollectModel.class).queryList().size());
    }

    private String queryHowManyDraftFromDB() {
        return String.valueOf(new Select().from(NewPostModel.class).queryList().size());
    }

    private void loadAdminFromNet() {
        OkHttpUtils
                .get()
                .url(Url.GET_ADMIN)
                .build()
                .execute(new AdminCallBack());
    }

    private class AdminCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            mAdminBean = new Gson().fromJson(response, AdminBean.class);
            if (mAdminBean.getStatus().equals("ok")) {
                mAdminText.setText(mAdminBean.getCount() + "人");
            }
            loadWeatherFromNet();
        }
    }

    /**
     * 计算时间差
     *
     * @return 时间差
     */
    private String getDateDifferDays() {
        String dayStr = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date d1 = df.parse("2015-12-08");
            Date d2 = df.parse(df.format(new Date(System.currentTimeMillis())));
            long diff = d2.getTime() - d1.getTime();
            long days = diff / (1000 * 3600 * 24);
            dayStr = String.valueOf(days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayStr;
    }


    private void loadWeatherFromNet() {
        SharedPreferences readSP = getContext().getSharedPreferences("city_name", Context.MODE_PRIVATE);
        String cityName = readSP.getString("CITY_NAME", "");
        OkHttpUtils
                .get()
                .url(Url.NOW_WEATHER + cityName)
                .build()
                .execute(new WeatherCallBack());
    }


    private class WeatherCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int id) {
            if (!NetworkUtils.isConnected()) {
                showBaseToast("刷新失败，请检查网络连接");
            }
        }

        @Override
        public void onResponse(String response, int id) {
            WeatherBean weatherBean = new Gson().fromJson(response, WeatherBean.class);
            if (weatherBean.getDesc().equals("OK") && weatherBean.getStatus() == 1000) {
                if (REFRESH_TAG) {
                    showBaseToast("刷新成功");
                }
                //更新天气
                updateWeatherUI(weatherBean);
            } else {
                showBaseToast("请选择正确的城市");
                //显示错误信息
                errorWeather();
            }
        }
    }

    private void updateWeatherUI(WeatherBean weatherBean) {
        WeatherBean.DataBean nowData = weatherBean.getData();
        mNowTempText.setText(nowData.getCity() + " " + nowData.getForecast().get(1).getType() + " " + nowData.getWendu() + "°");
        mNowTimeText.setText(getCurrentTime());
        mTomTempText.setText("明日 " + nowData.getForecast().get(2).getType());
        if (nowData.getAqi() == null) {
            mAqiText.setText("AQI 58");
        } else {
            mAqiText.setText("AQI " + nowData.getAqi());
        }
        mWindText.setText(nowData.getForecast().get(1).getFengxiang());
        mWindPowerText.setText(nowData.getForecast().get(1).getFengli());
        mNowWeatherImage.setImageResource(handleWeatherType(nowData.getForecast().get(1).getType()));
        mTomWeatherImage.setImageResource(handleWeatherType(nowData.getForecast().get(2).getType()));
    }

    private int handleWeatherType(String type) {
        if (type.contains("晴")) {
            return R.drawable.svg_sun;
        } else if (type.contains("多云")) {
            return R.drawable.svg_cloud;
        } else if (type.contains("阴")) {
            return R.drawable.svg_shadow;
        } else if (type.contains("雨")) {
            return R.drawable.svg_rain;
        } else {
            return R.drawable.svg_cloud;
        }
    }

    /**
     * 获得当前系统时间
     *
     * @return 系统时间
     */
    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    /**
     * 请求错误显示
     */
    private void errorWeather() {
        mNowTempText.setText("unknown");
        mNowTimeText.setText("unknown");
        mTomTempText.setText("unknown");
        mAqiText.setText("unknown");
        mWindText.setText("unknown");
        mWindPowerText.setText("unknown");
        mNowWeatherImage.setImageResource(R.drawable.svg_unknown);
        mTomWeatherImage.setImageResource(R.drawable.svg_unknown);
    }

    @Override
    public void initEvents() {
        mHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonResumeActivity.class));
            }
        });
        mResumeRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonResumeActivity.class));
            }
        });
        mAboutRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AboutActivity.class));
            }
        });
        mSupportRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SupportActivity.class));
            }
        });

        mWebRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseToast("于2015年12月8号建站");
            }
        });

        mAdminRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title("管理员")
                        .content("身份：" + mAdminBean.getAuthors().get(0).getSlug()
                                + "\n" + "昵称：" + mAdminBean.getAuthors().get(0).getNickname()
                                + "\n" + "说明：" + mAdminBean.getAuthors().get(0).getDescription())
                        .positiveText("知道了")
                        .show();
            }
        });

        mCollectRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),CollectPostListActivity.class));
            }
        });

        mDraftRLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),DraftActivity.class));
            }
        });

        mSloganText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title("更改签名")
                        .content("此操作会永久更改默认个性签名")
                        .inputRangeRes(1, 30, R.color.md_blue_400)
                        .input("请输入个性签名...", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                SharedPreferences.Editor editor = getContext().getSharedPreferences("person_slogan", Context.MODE_PRIVATE).edit();
                                editor.putString("MY_SLOGAN", input.toString());
                                editor.apply();
                                loadWeatherFromNet();
                            }
                        })
                        .canceledOnTouchOutside(true)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showBaseToast("未做任何更改");
                            }
                        })
                        .show();
            }
        });

        mNowTempText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getContext())
                        .title("更改城市")
                        .content("此操作会永久更改默认显示城市")
                        .inputRangeRes(2, 10, R.color.md_blue_400)
                        .input("请输入需要显示的城市...", null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                SharedPreferences.Editor editor = getContext().getSharedPreferences("city_name", Context.MODE_PRIVATE).edit();
                                editor.putString("CITY_NAME", input.toString());
                                editor.apply();
                                initData();
                            }
                        })
                        .canceledOnTouchOutside(true)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                showBaseToast("未做任何更改");
                            }
                        })
                        .show();
            }
        });

        mRefreshImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBaseToast("正在刷新...");
                REFRESH_TAG = true;
                initData();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
