package com.sdxxtop.guardianapp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.photoview.PhotoView;
import com.orhanobut.logger.Logger;
import com.sdxxtop.guardianapp.R;
import com.sdxxtop.guardianapp.base.BaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import butterknife.BindView;

public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.photo_view_save_layout)
    LinearLayout saveLayout;
    private String imageUrl;

    @Override
    protected int getLayout() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initVariables() {
        if (getIntent() != null) {
            imageUrl = getIntent().getStringExtra("image_url");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        if (!TextUtils.isEmpty(imageUrl)) {
            photoView.setZoomable(true);
            photoView.setMinimumScale(0.9f);
            Glide.with(mContext).load(imageUrl).into(photoView);
        }

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        saveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File directory = null;
                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                    directory = Environment.getExternalStorageDirectory();
                } else {
                    directory = getExternalCacheDir();
                }
                String path = directory + "/sdxxtop/" + imageName();
                createParentFolder(path);
                File file = new File(path);
                saveImage(file);
            }
        });
    }

    private static void createParentFolder(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return;
        }
        File dir = file.getParentFile();
        if (dir != null) {
            dir.mkdirs();
        }
    }

    private static String checkIsSameUserCrop(Context context, String lastSavePath) {
        if (TextUtils.isEmpty(lastSavePath)) {
            return null;
        }

        String[] arr = getRealPathFromUri(context, Uri.parse(lastSavePath));
        if (arr == null) {
            return null;
        }

        String path = arr[0];
        String title = arr[1];

        if (!TextUtils.isEmpty(path)) {
            return path;
        }

        return null;
    }

    private static String[] getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.TITLE};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor.moveToFirst()) {
                String path = cursor.getString(0);
                String title = cursor.getString(1);
                return new String[]{path, title};
            }
        } catch (Exception e) {
            Logger.e("" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private void saveImage(final File file) {
        new Thread() {
            @Override
            public void run() {
                FileOutputStream fos = null;
                InputStream is = null;
                try {
                    HttpURLConnection url = (HttpURLConnection) new URL(imageUrl).openConnection();
                    url.setConnectTimeout(5000);
                    url.setReadTimeout(5000);
                    if (url.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        is = url.getInputStream();
                        if (is != null) {
                            fos = new FileOutputStream(file);
                            int len = -1;
                            byte[] bs = new byte[1024 * 4];
                            while ((len = is.read(bs)) != -1) {
                                fos.write(bs, 0, len);
                                fos.flush();
                            }

                            String savePicPath = MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(),
                                    imageName(), "CaoHuaImage");

                            savePicPath = checkIsSameUserCrop(PhotoViewActivity.this, savePicPath);
                            if (!TextUtils.isEmpty(savePicPath)) {
                                sendBroadcast(
                                        new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(savePicPath))));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast("保存成功");
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();
    }

    private String imageName() {
        int indexOf = imageUrl.lastIndexOf("/");
        String substring = imageUrl.substring(indexOf + 1);
        return substring;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            closeActivity();
        }
        return super.onKeyDown(keyCode, event);

    }


    public static void start(Context context, String imageUrl) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra("image_url", imageUrl);
//        if (Build.VERSION.SDK_INT > 16) {
//            ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(context, R.anim.scale_out, R.anim.scale_in);
////            ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity)context, Pair.create((View)orginalImageView, R.id.target_imageView))
//            context.startActivity(intent, compat.toBundle());
//        } else {
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(com.luck.picture.lib.R.anim.a5, 0);
        }
//        }
    }

    protected void closeActivity() {
        this.finish();
//        this.overridePendingTransition(0, R.anim.photo_view_out);
        this.overridePendingTransition(0, com.luck.picture.lib.R.anim.a3);

    }

    public static void start(Context context, String imageUrl, View ImageView) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra("image_url", imageUrl);
        context.startActivity(intent);

        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((BaseActivity) context, ImageView, "photo_view");
        ActivityCompat.startActivity(context, new Intent(context, PhotoViewActivity.class), compat.toBundle());
    }
}
