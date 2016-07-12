/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.finalteam.galleryfinal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.finalteam.galleryfinal.adapter.FolderListAdapter;
import cn.finalteam.galleryfinal.adapter.PhotoListAdapter;
import cn.finalteam.galleryfinal.model.PhotoFolderInfo;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.permission.AfterPermissionGranted;
import cn.finalteam.galleryfinal.permission.EasyPermissions;
import cn.finalteam.galleryfinal.utils.PhotoTools;
import cn.finalteam.galleryfinal.widget.FloatingActionButton;
import cn.finalteam.toolsfinal.DeviceUtils;
import cn.finalteam.toolsfinal.StringUtils;
import cn.finalteam.toolsfinal.io.FilenameUtils;

/**
 * Desction:图片选择器
 * Author:pengjianbo
 * Date:15/10/10 下午3:54
 */
public class PhotoSelectActivity extends PhotoBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    private final int HANDLER_TAKE_PHOTO_EVENT = 1000;
    private final int HANDLER_REFRESH_LIST_EVENT = 1002;

    private GridView gvPhotoList;
    private ListView lvFolderList;
    private LinearLayout layoutFolderPanel;
    private ImageView ivTakePhoto;
    private ImageView ivBack;
    private ImageView ivClear;
    private ImageView ivPreView;
    private TextView tvChooseCount;
    private TextView tvSubTitle;
    private LinearLayout layoutTitle;
    private FloatingActionButton floatingActionButton;
    private TextView tvEmptyView;
    private RelativeLayout layoutTitlebar;
    private TextView tvTitle;
    private ImageView ivFolderArrow;

    private List<PhotoFolderInfo> allPhotoFolderList;
    private FolderListAdapter folderListAdapter;

    private List<PhotoInfo> curPhotoList;
    private PhotoListAdapter photoListAdapter;

    //是否需要刷新相册
    private boolean mHasRefreshGallery = false;
    private ArrayList<PhotoInfo> mSelectPhotoList = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectPhotoMap", mSelectPhotoList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectPhotoList = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra("selectPhotoMap");
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == HANDLER_TAKE_PHOTO_EVENT) {
                PhotoInfo photoInfo = (PhotoInfo) msg.obj;
                takeRefreshGallery(photoInfo);
                refreshSelectCount();
            } else if ( msg.what == HANDLER_REFRESH_LIST_EVENT ){
                refreshSelectCount();
                photoListAdapter.notifyDataSetChanged();
                folderListAdapter.notifyDataSetChanged();
                if (allPhotoFolderList.get(0).getPhotoList() == null ||
                        allPhotoFolderList.get(0).getPhotoList().size() == 0) {
                    tvEmptyView.setText(R.string.no_photo);
                }

                gvPhotoList.setEnabled(true);
                layoutTitle.setEnabled(true);
                ivTakePhoto.setEnabled(true);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ( GalleryFinal.getFunctionConfig() == null || GalleryFinal.getGalleryTheme() == null) {
            resultFailureDelayed(getString(R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.gf_activity_photo_select);
            mPhotoTargetFolder = null;

            findViews();
            setListener();

            allPhotoFolderList = new ArrayList<>();
            folderListAdapter = new FolderListAdapter(this, allPhotoFolderList, GalleryFinal.getFunctionConfig());
            lvFolderList.setAdapter(folderListAdapter);

            curPhotoList = new ArrayList<>();
            photoListAdapter = new PhotoListAdapter(this, curPhotoList, mSelectPhotoList, mScreenWidth);
            gvPhotoList.setAdapter(photoListAdapter);

            if (GalleryFinal.getFunctionConfig().isMutiSelect()) {
                tvChooseCount.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
            }

            setTheme();
            gvPhotoList.setEmptyView(tvEmptyView);

            if (GalleryFinal.getFunctionConfig().isCamera()) {
                ivTakePhoto.setVisibility(View.VISIBLE);
            } else {
                ivTakePhoto.setVisibility(View.GONE);
            }

            refreshSelectCount();
            requestGalleryPermission();

            gvPhotoList.setOnScrollListener(GalleryFinal.getCoreConfig().getPauseOnScrollListener());
        }

        Global.mPhotoSelectActivity = this;
    }

    private void setTheme() {
        ivBack.setImageResource(GalleryFinal.getGalleryTheme().getIconBack());
        if (GalleryFinal.getGalleryTheme().getIconBack() == R.drawable.ic_gf_back) {
            ivBack.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        ivFolderArrow.setImageResource(GalleryFinal.getGalleryTheme().getIconFolderArrow());
        if (GalleryFinal.getGalleryTheme().getIconFolderArrow() == R.drawable.ic_gf_triangle_arrow) {
            ivFolderArrow.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        ivClear.setImageResource(GalleryFinal.getGalleryTheme().getIconClear());
        if (GalleryFinal.getGalleryTheme().getIconClear() == R.drawable.ic_gf_clear) {
            ivClear.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        ivPreView.setImageResource(GalleryFinal.getGalleryTheme().getIconPreview());
        if (GalleryFinal.getGalleryTheme().getIconPreview() == R.drawable.ic_gf_preview) {
            ivPreView.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }

        ivTakePhoto.setImageResource(GalleryFinal.getGalleryTheme().getIconCamera());
        if (GalleryFinal.getGalleryTheme().getIconCamera() == R.drawable.ic_gf_camera) {
            ivTakePhoto.setColorFilter(GalleryFinal.getGalleryTheme().getTitleBarIconColor());
        }
        floatingActionButton.setIcon(GalleryFinal.getGalleryTheme().getIconFab());

        layoutTitlebar.setBackgroundColor(GalleryFinal.getGalleryTheme().getTitleBarBgColor());
        tvSubTitle.setTextColor(GalleryFinal.getGalleryTheme().getTitleBarTextColor());
        tvTitle.setTextColor(GalleryFinal.getGalleryTheme().getTitleBarTextColor());
        tvChooseCount.setTextColor(GalleryFinal.getGalleryTheme().getTitleBarTextColor());
        floatingActionButton.setColorPressed(GalleryFinal.getGalleryTheme().getFabPressedColor());
        floatingActionButton.setColorNormal(GalleryFinal.getGalleryTheme().getFabNornalColor());
    }

    private void findViews() {
        gvPhotoList = (GridView) findViewById(R.id.gv_photo_list);
        lvFolderList = (ListView) findViewById(R.id.lv_folder_list);
        tvSubTitle = (TextView) findViewById(R.id.tv_sub_title);
        layoutFolderPanel = (LinearLayout) findViewById(R.id.ll_folder_panel);
        ivTakePhoto = (ImageView) findViewById(R.id.iv_take_photo);
        tvChooseCount = (TextView) findViewById(R.id.tv_choose_count);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_ok);
        tvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        layoutTitle = (LinearLayout) findViewById(R.id.ll_title);
        ivClear = (ImageView) findViewById(R.id.iv_clear);
        layoutTitlebar = (RelativeLayout) findViewById(R.id.titlebar);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ivFolderArrow = (ImageView) findViewById(R.id.iv_folder_arrow);
        ivPreView = (ImageView) findViewById(R.id.iv_preview);
    }

    private void setListener() {
        layoutTitle.setOnClickListener(this);
        ivTakePhoto.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivFolderArrow.setOnClickListener(this);

        lvFolderList.setOnItemClickListener(this);
        gvPhotoList.setOnItemClickListener(this);
        floatingActionButton.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        ivPreView.setOnClickListener(this);
    }

    protected void deleteSelect(int photoId) {
        try {
            for(Iterator<PhotoInfo> iterator = mSelectPhotoList.iterator();iterator.hasNext();){
                PhotoInfo info = iterator.next();
                if (info != null && info.getPhotoId() == photoId) {
                    iterator.remove();
                    break;
                }
            }
        } catch (Exception e){}

        refreshAdapter();
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }

    protected void takeRefreshGallery(PhotoInfo photoInfo, boolean selected) {
        if (isFinishing() || photoInfo == null) {
            return;
        }

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANDLER_TAKE_PHOTO_EVENT;
        mSelectPhotoList.add(photoInfo);
        mHanlder.sendMessageDelayed(message, 100);
    }

    /**
     * 解决在5.0手机上刷新Gallery问题，从startActivityForResult回到Activity把数据添加到集合中然后理解跳转到下一个页面，
     * adapter的getCount与list.size不一致，所以我这里用了延迟刷新数据
     * @param photoInfo
     */
    private void takeRefreshGallery(PhotoInfo photoInfo) {
        curPhotoList.add(0, photoInfo);
        photoListAdapter.notifyDataSetChanged();

        //添加到集合中
        List<PhotoInfo> photoInfoList = allPhotoFolderList.get(0).getPhotoList();
        if (photoInfoList == null) {
            photoInfoList = new ArrayList<>();
        }
        photoInfoList.add(0, photoInfo);
        allPhotoFolderList.get(0).setPhotoList(photoInfoList);

        if ( folderListAdapter.getSelectFolder() != null ) {
            PhotoFolderInfo photoFolderInfo = folderListAdapter.getSelectFolder();
            List<PhotoInfo> list = photoFolderInfo.getPhotoList();
            if ( list == null ) {
                list = new ArrayList<>();
            }
            list.add(0, photoInfo);
            if ( list.size() == 1 ) {
                photoFolderInfo.setCoverPhoto(photoInfo);
            }
            folderListAdapter.getSelectFolder().setPhotoList(list);
        } else {
            String folderA = new File(photoInfo.getPhotoPath()).getParent();
            for (int i = 1; i < allPhotoFolderList.size(); i++) {
                PhotoFolderInfo folderInfo = allPhotoFolderList.get(i);
                String folderB = null;
                if (!StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                    folderB = new File(photoInfo.getPhotoPath()).getParent();
                }
                if (TextUtils.equals(folderA, folderB)) {
                    List<PhotoInfo> list = folderInfo.getPhotoList();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(0, photoInfo);
                    folderInfo.setPhotoList(list);
                    if ( list.size() == 1 ) {
                        folderInfo.setCoverPhoto(photoInfo);
                    }
                }
            }
        }

        folderListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void takeResult(PhotoInfo photoInfo) {

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANDLER_TAKE_PHOTO_EVENT;

        if ( !GalleryFinal.getFunctionConfig().isMutiSelect() ) { //单选
            mSelectPhotoList.clear();
            mSelectPhotoList.add(photoInfo);

            if ( GalleryFinal.getFunctionConfig().isEditPhoto() ) {//裁剪
                mHasRefreshGallery = true;
                toPhotoEdit();
            } else {
                ArrayList<PhotoInfo> list = new ArrayList<>();
                list.add(photoInfo);
                resultData(list);
            }

            mHanlder.sendMessageDelayed(message, 100);
        } else {//多选
            mSelectPhotoList.add(photoInfo);
            mHanlder.sendMessageDelayed(message, 100);
        }
    }

    /**
     * 执行裁剪
     */
    protected void toPhotoEdit() {
        Intent intent = new Intent(this, PhotoEditActivity.class);
        intent.putExtra(PhotoEditActivity.SELECT_MAP, mSelectPhotoList);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if ( id == R.id.ll_title || id == R.id.iv_folder_arrow) {
            if ( layoutFolderPanel.getVisibility() == View.VISIBLE ) {
                layoutFolderPanel.setVisibility(View.GONE);
                layoutFolderPanel.setAnimation(AnimationUtils.loadAnimation(this, R.anim.gf_flip_horizontal_out));
            } else {
                layoutFolderPanel.setAnimation(AnimationUtils.loadAnimation(this, R.anim.gf_flip_horizontal_in));
                layoutFolderPanel.setVisibility(View.VISIBLE);
            }
        } else if ( id == R.id.iv_take_photo ) {
            //判断是否达到多选最大数量
            if (GalleryFinal.getFunctionConfig().isMutiSelect() && mSelectPhotoList.size() == GalleryFinal.getFunctionConfig().getMaxSize()) {
                toast(getString(R.string.select_max_tips));
                return;
            }

            if (!DeviceUtils.existSDCard()) {
                toast(getString(R.string.empty_sdcard));
                return;
            }

            takePhotoAction();
        } else if ( id == R.id.iv_back ) {
            if ( layoutFolderPanel.getVisibility() == View.VISIBLE ) {
                layoutTitle.performClick();
            } else {
                finish();
            }
        } else if ( id == R.id.fab_ok ) {
            if(mSelectPhotoList.size() > 0) {
                if (!GalleryFinal.getFunctionConfig().isEditPhoto()) {
                    resultData(mSelectPhotoList);
                } else {
                    toPhotoEdit();
                }
            }
        } else if ( id == R.id.iv_clear ) {
            mSelectPhotoList.clear();
            photoListAdapter.notifyDataSetChanged();
            refreshSelectCount();
        } else if ( id == R.id.iv_preview ) {
            Intent intent = new Intent(this, PhotoPreviewActivity.class);
            intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, mSelectPhotoList);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if ( parentId == R.id.lv_folder_list ) {
            folderItemClick(position);
        } else {
            photoItemClick(view, position);
        }
    }
    private void folderItemClick(int position) {
        layoutFolderPanel.setVisibility(View.GONE);
        curPhotoList.clear();
        PhotoFolderInfo photoFolderInfo = allPhotoFolderList.get(position);
        if ( photoFolderInfo.getPhotoList() != null ) {
            curPhotoList.addAll(photoFolderInfo.getPhotoList());
        }
        photoListAdapter.notifyDataSetChanged();

        if (position == 0) {
            mPhotoTargetFolder = null;
        } else {
            PhotoInfo photoInfo = photoFolderInfo.getCoverPhoto();
            if (photoInfo != null && !StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                mPhotoTargetFolder = new File(photoInfo.getPhotoPath()).getParent();
            } else {
                mPhotoTargetFolder = null;
            }
        }
        tvSubTitle.setText(photoFolderInfo.getFolderName());
        folderListAdapter.setSelectFolder(photoFolderInfo);
        folderListAdapter.notifyDataSetChanged();

        if (curPhotoList.size() == 0) {
            tvEmptyView.setText(R.string.no_photo);
        }
    }

    private void photoItemClick(View view, int position) {
        PhotoInfo info = curPhotoList.get(position);
        if (!GalleryFinal.getFunctionConfig().isMutiSelect()) {
            mSelectPhotoList.clear();
            mSelectPhotoList.add(info);
            String ext = FilenameUtils.getExtension(info.getPhotoPath());
            if (GalleryFinal.getFunctionConfig().isEditPhoto() && (ext.equalsIgnoreCase("png")
                    || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))) {
                toPhotoEdit();
            } else {
                ArrayList<PhotoInfo> list = new ArrayList<>();
                list.add(info);
                resultData(list);
            }
            return;
        }
        boolean checked = false;
        if (!mSelectPhotoList.contains(info)) {
            if (GalleryFinal.getFunctionConfig().isMutiSelect() && mSelectPhotoList.size() == GalleryFinal.getFunctionConfig().getMaxSize()) {
                toast(getString(R.string.select_max_tips));
                return;
            } else {
                mSelectPhotoList.add(info);
                checked = true;
            }
        } else {
            try {
                for(Iterator<PhotoInfo> iterator = mSelectPhotoList.iterator();iterator.hasNext();){
                    PhotoInfo pi = iterator.next();
                    if (pi != null && TextUtils.equals(pi.getPhotoPath(), info.getPhotoPath())) {
                        iterator.remove();
                        break;
                    }
                }
            } catch (Exception e){}
            checked = false;
        }
        refreshSelectCount();

        PhotoListAdapter.PhotoViewHolder holder = (PhotoListAdapter.PhotoViewHolder) view.getTag();
        if (holder != null) {
            if (checked) {
                holder.mIvCheck.setBackgroundColor(GalleryFinal.getGalleryTheme().getCheckSelectedColor());
            } else {
                holder.mIvCheck.setBackgroundColor(GalleryFinal.getGalleryTheme().getCheckNornalColor());
            }
        } else {
            photoListAdapter.notifyDataSetChanged();
        }
    }

    @SuppressLint("StringFormatMatches")
    public void refreshSelectCount() {
        if((tvChooseCount!=null) && (mSelectPhotoList!=null) && (GalleryFinal.getFunctionConfig()!=null)){
            tvChooseCount.setText(getString(R.string.selected, mSelectPhotoList.size(), GalleryFinal.getFunctionConfig().getMaxSize()));
        }
        if ( mSelectPhotoList.size() > 0 && GalleryFinal.getFunctionConfig().isMutiSelect() ) {
            ivClear.setVisibility(View.VISIBLE);
        } else {
            ivClear.setVisibility(View.GONE);
        }

        if(GalleryFinal.getFunctionConfig().isEnablePreview()){
            ivPreView.setVisibility(View.VISIBLE);
        } else {
            ivPreView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPermissionsGranted(List<String> list) {
        getPhotos();
    }

    @Override
    public void onPermissionsDenied(List<String> list) {
        tvEmptyView.setText(R.string.permissions_denied_tips);
        ivTakePhoto.setVisibility(View.GONE);
    }

    /**
     * 获取所有图片
     */
    @AfterPermissionGranted(GalleryFinal.PERMISSIONS_CODE_GALLERY)
    private void requestGalleryPermission() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            getPhotos();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.permissions_tips_gallery),
                    GalleryFinal.PERMISSIONS_CODE_GALLERY, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void getPhotos() {
        tvEmptyView.setText(R.string.waiting);
        gvPhotoList.setEnabled(false);
        layoutTitle.setEnabled(false);
        ivTakePhoto.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();

                allPhotoFolderList.clear();
                List<PhotoFolderInfo> allFolderList = PhotoTools.getAllPhotoFolder(PhotoSelectActivity.this, mSelectPhotoList);
                allPhotoFolderList.addAll(allFolderList);

                curPhotoList.clear();
                if ( allFolderList.size() > 0 ) {
                    if ( allFolderList.get(0).getPhotoList() != null ) {
                        curPhotoList.addAll(allFolderList.get(0).getPhotoList());
                    }
                }

                refreshAdapter();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ( keyCode == KeyEvent.KEYCODE_BACK ) {
            if ( layoutFolderPanel.getVisibility() == View.VISIBLE ) {
                layoutTitle.performClick();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( mHasRefreshGallery) {
            mHasRefreshGallery = false;
            requestGalleryPermission();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (GalleryFinal.getCoreConfig() != null &&GalleryFinal.getCoreConfig().getImageLoader() != null ) {
            GalleryFinal.getCoreConfig().getImageLoader().clearMemoryCache();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPhotoTargetFolder = null;
        mSelectPhotoList.clear();
        GalleryFinal.onHandlerResultCallback = null;
        GalleryFinal.coreConfig = null;
        GalleryFinal.currentFunctionConfig = null;
        GalleryFinal.functionConfig = null;
        GalleryFinal.themeConfig = null;
        System.gc();
    }
}
