package com.sdxxtop.guardianapp.ui.widget.imgservice.webservice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

/**
 * @author :  lwb
 * Date: 2019/4/18
 * Desc:
 */
public class ImageUtil {
    private static final String TAG ="ImageUtil";
    public static Intent choosePicture() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("image/*");

        return Intent.createChooser(intent, null);

    }



    /**

     * 拍照后返回

     */

    public static Intent takeBigPicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, newPictureUri(getNewPhotoPath()));

        return intent;

    }



    public static String getDirPath() {

        return Environment.getExternalStorageDirectory().getPath() + "/WebViewUploadImage";

    }



    private static String getNewPhotoPath() {

        return getDirPath() + "/" + System.currentTimeMillis() + ".jpg";

    }



    public static String retrievePath(Context context, Intent sourceIntent, Intent dataIntent) {
        String picPath = null;
        try {
            Uri uri;
            if (dataIntent != null) {
                uri = dataIntent.getData();
                if (uri != null) {
                    picPath = ContentUtil.getPath(context, uri);
                }

                if (isFileExists(picPath)) {
                    return picPath;
                }
            }
            if (sourceIntent != null) {
                uri = sourceIntent.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
                if (uri != null) {
                    String scheme = uri.getScheme();
                    if (scheme != null && scheme.startsWith("file")) {
                        picPath = uri.getPath();
                    }
                }
                if (!TextUtils.isEmpty(picPath)) {
                    File file = new File(picPath);
                    if (!file.exists() || !file.isFile()) {
                    }
                }
            }
            return picPath;
        } finally {

        }
    }
    private static Uri newPictureUri(String path) {

        return Uri.fromFile(new File(path));

    }



    private static boolean isFileExists(String path) {

        if (TextUtils.isEmpty(path)) {

            return false;

        }

        File f = new File(path);

        if (!f.exists()) {

            return false;

        }

        return true;

    }

}
