package com.bzh.floodview.module.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.bumptech.glide.Glide;
import com.bzh.floodview.App;
import com.bzh.floodview.BuildConfig;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.R;
import com.bzh.floodview.api.RetrofitHelper;
import com.bzh.floodview.base.activity.BaseActivity;
import com.bzh.floodview.module.WebSocketChatClient;
import com.bzh.floodview.module.home.homeIndex.IndexFragment;
import com.bzh.floodview.module.home.homeMap.MapFragment;
import com.bzh.floodview.module.home.homeChat.TalkFragment;
import com.bzh.floodview.module.home.homeChat.talk.talkFriends.FriendsActivity;
import com.bzh.floodview.module.setting.StationActivity;
import com.bzh.floodview.sideslip.AboutusActivity;
import com.bzh.floodview.sideslip.FeedbackActivity;
import com.bzh.floodview.utils.AppManager;
import com.bzh.floodview.utils.CommonUtil;
import com.bzh.floodview.utils.FileUtil;
import com.google.gson.Gson;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import static com.bzh.floodview.utils.FileUtil.getRealFilePathFromUri;

/**
 * 主界面
 */
public class HomeActivity extends BaseActivity implements HomeContract.View {

    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.home_drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.home_float_button)
    FloatingActionButton mFloatingActionButton;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.home_bottom_bar)
    BottomNavigationBar mBottomNavigationBar;

    private CircleImageView mCircleImageView;

    private TextView mTextViewName;

    private TextView mTextViewMotto;

    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type = 1;

    @Inject
    HomeContract.Presenter mPresenter;
    @Inject
    IndexFragment sportFragment;
    @Inject
    MapFragment mapFragment;
    @Inject
    TalkFragment talkFragment;
    @Inject
    MainAttrs mainAttrs;
    @Inject
    RetrofitHelper retrofitHelper;

    private WebSocketChatClient webSocketChatClient;

    @Inject
    Gson gson;

    private Fragment[] fragments = new Fragment[3];

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void beforeInit() {
        super.beforeInit();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); //固定页面压制 bug:底部状态栏跟随键盘
    }

    private static final String TAG = "HomeActivity";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        View navigationHeadView = mNavigationView.getHeaderView(0);
        mCircleImageView = navigationHeadView.findViewById(R.id.user_icon);
        mTextViewName = navigationHeadView.findViewById(R.id.user_name);
        mTextViewMotto = navigationHeadView.findViewById(R.id.user_motto);
        mCircleImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) { //选择图像
                uploadHeadImage();
                return true;
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //mToolbar.getBackground().mutate().setAlpha(225);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        fragments[0] = sportFragment;
        fragments[1] = mapFragment;
        fragments[2] = talkFragment;
        //侧滑菜单 menu
        //mNavigationView.setCheckedItem(R.id.nav_login);
        mNavigationView.setNavigationItemSelectedListener(menuItem -> {
            Intent instant;
            switch (menuItem.getItemId()) {
                case R.id.nav_my_stations:
                    startActivity(new Intent(HomeActivity.this, StationActivity.class));
                    break;
                case R.id.nav_feedback_feedback:
                    startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                    break;
                case R.id.nav_adout_me:
                    startActivity(new Intent(HomeActivity.this, AboutusActivity.class));
                    break;
                case R.id.nav_sign_out:
                    AppManager.getAppManager().finishAllActivity();
                    System.exit(0);
                    break;
            }
            mDrawerLayout.closeDrawers();
            return true;
        });
        //底部菜单
        TextBadgeItem numberBadgeItem = new TextBadgeItem();
        numberBadgeItem.setBorderWidth(3)
                .setBackgroundColorResource(R.color.red)
                .setHideOnSelect(false);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED).setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .addItem(new BottomNavigationItem(R.drawable.home_unchecked, "主页").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.patrol_unchecked, "监测预警").setActiveColorResource(R.color.blue))
                .addItem(new BottomNavigationItem(R.drawable.maillist_unchecked, "通讯录").setActiveColorResource(R.color.blue)
                        .setBadgeItem(numberBadgeItem))
                .setFirstSelectedPosition(0).initialise();

        //是否显示徽章
        mainAttrs.getLoginSign().observe(this, aBoolean -> {
            if (aBoolean) {
                numberBadgeItem.show();
            } else {
                numberBadgeItem.hide();
            }
        });

        mainAttrs.getNoReadCount().observe(this, integer -> {
            if (integer != null && integer == 0) {
                numberBadgeItem.hide();
            } else {
                numberBadgeItem.show();
                numberBadgeItem.setText(String.valueOf(integer));
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.ttest, fragments[0]).commit();//设置默认的fragment
        mFloatingActionButton.hide();
        mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue, null)));//设置默认颜色
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() { //悬浮按钮点击跳转到好友列表
            @Override
            public void onClick(View v) {
                FriendsActivity.open(HomeActivity.this);
            }
        });
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                if (position == 0) {
                    if (mFloatingActionButton.isShown()) {
                        mFloatingActionButton.hide();
                    }
                    if (mToolbar.getVisibility() == View.GONE) {
                        mToolbar.setVisibility(View.VISIBLE);
                    }
                }
                if (position == 1) {
                    if (mFloatingActionButton.isShown()) {
                        mFloatingActionButton.hide();
                    }
                    if (mToolbar.getVisibility() == View.VISIBLE) {
                        mToolbar.setVisibility(View.GONE);
                    }
                }
                if (position == 2) {
                    if (!mFloatingActionButton.isShown()) {
                        mFloatingActionButton.show();
                    }
                    if (mToolbar.getVisibility() == View.GONE) {
                        mToolbar.setVisibility(View.VISIBLE);
                    }
                }
                if (position < fragments.length) {
                    //mFloatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplication(), colors[position])));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment nowFragment = fragmentManager.findFragmentById(R.id.ttest);
                    Fragment nextFragment = fragments[position];
                    if(nowFragment == null) return;
                    if (nextFragment.isAdded()) {
                        fragmentTransaction.hide(nowFragment).show(nextFragment);
                    } else {
                        fragmentTransaction.hide(nowFragment).add(R.id.ttest, nextFragment);
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }

            @Override
            public void onTabUnselected(int position) {
                //这儿也要操作隐藏，否则Fragment会重叠
                if (fragments != null) {
                    if (position < fragments.length) {
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        Fragment fragment = fragments[position];
                        // 隐藏当前的fragment
                        ft.hide(fragment);
                        ft.commitAllowingStateLoss();
                    }
                }
            }

            @Override
            public void onTabReselected(int position) {
            }
        });

        //根据登录状态 连接或断开webSocket
        mainAttrs.getLoginSign().observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                successSetting();
            } else {
                failSettring();
            }
        });
        mPresenter.loadData(App.username); //加载当前用户的信息
    }

    //跳转到这儿
    public static void open(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @OnClick(R.id.home_float_button) //悬浮按钮
    public void makeByFloatingActionButton(View view) {
        showToast("点击了");
       /* Snackbar.make(view,"1111", Snackbar.LENGTH_SHORT)
                .setAction("222", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("点击了");
                    }
                }).show();*/
    }

    @Override
    public void setHeadPortrait(Bitmap bitmap) {
        Glide.with(this).load(bitmap).into(mCircleImageView);
    }

    @Override
    public void setHeadName(String name) {
        mTextViewName.setText(name);
    }

    @Override
    public void setHeadMotto(String motto) {
        mTextViewMotto.setText(motto);
    }

    @Override
    public void successSetting() {
        Glide.with(this).load(ContextCompat.getDrawable(getApplication(), R.mipmap.no_login_user)).into(mCircleImageView);
        try {
            webSocketChatClient = WebSocketChatClient.InstanceWebSocket(new URI("ws://"+App.socketIp), gson, mainAttrs);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketChatClient.connect(); //登录成功，连接webSocket
    }

    @Override
    public void failSettring() {
        webSocketChatClient.close();
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.dialog_imgchoose, null);
        TextView btnCarema = view.findViewById(R.id.btn_camera); //拍照
        TextView btnPhoto = view.findViewById(R.id.btn_photo); //相册
        TextView btnCancel = view.findViewById(R.id.btn_cancel); //取消
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(HomeActivity.this, android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(HomeActivity.this).inflate(R.layout.activity_home, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CommonUtil.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            CommonUtil.READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CommonUtil.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == CommonUtil.READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), CommonUtil.REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(HomeActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CommonUtil.REQUEST_CAPTURE);
    }

    /**
     * 调用某个活动后的返回处理
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case CommonUtil.REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case CommonUtil.REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case CommonUtil.REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);

                    if (type == 1) {
                        //上传图片
                        File file = new File(cropImagePath);
                        // 创建 RequestBody，用于封装构建RequestBody
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
                        MultipartBody.Part body = MultipartBody.Part.createFormData("headPortrait", file.getName(), requestFile);
                        RequestBody username = RequestBody.create(MediaType.parse("text/x-markdown"), App.getUsername());
                        retrofitHelper.successHandler(retrofitHelper.getServer().uploadPng(username, body), new RetrofitHelper.callBack() {
                            @Override
                            public <T> void run(T t) {
                                mCircleImageView.setImageBitmap(bitMap);
                            }
                        });
                    } else {
                        mCircleImageView.setImageBitmap(bitMap);
                    }
                }
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, CommonUtil.REQUEST_CROP_PHOTO);
    }

    //back键
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        AppManager.getAppManager().AppExit(this);
//    }

    //back键
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(),
                    "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            AppManager.getAppManager().AppExit(this);
            mainAttrs.setLoginSign(false);
        }
    }

}
