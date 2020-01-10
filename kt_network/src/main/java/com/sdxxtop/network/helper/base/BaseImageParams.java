package com.sdxxtop.network.helper.base;

import com.sdxxtop.network.helper.HttpConstantValue;
import com.sdxxtop.network.utils.PictureUtil;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018/5/7.
 * <p>
 * RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), new File(compressImagePath));
 * HashMap<String, RequestBody> map = new HashMap<>();
 * map.put("img" + "" + "\";filename=\"img1.png", requestBody);
 * map.put("data", RequestBody.create(MediaType.parse("text/plain"), paramsData));
 * <p>
 * <p>
 * 图片上传要求 参照如下 要用 @Multipart 和  @PartMap 两个注解
 * 图片 ： MediaType.parse("image/png")
 * 文本 ： MediaType.parse("text/plain")
 * <p>
 * 图文上传应该可以满足需求
 * <p>
 * 外勤上传图片
 *
 * @Multipart
 * @POST("main/img") Call<BaseModel> postMainImg(@PartMap HashMap<String, RequestBody> map);
 */

public abstract class BaseImageParams extends BaseParams {
    private LinkedHashMap<String, RequestBody> imgMap;

    public BaseImageParams() {
        super();
        imgMap = new LinkedHashMap<>();
    }

    /**
     * 单图片上传
     *
     * @param imgPar 上传填入参数，上传有所不同 如：img
     */
    public void addCompressImagePath(String imgPar, File file, String targetPath, int quality) {
        String compressPath = PictureUtil.compressImage(file.getPath(), targetPath, quality);
        File targetFile = new File(compressPath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), targetFile);
        imgMap.put(imgPar + "" + "\";filename=\"" + targetFile.getName(), requestBody);
    }

    /**
     * 多图片上传
     */
    public void addCompressImagePathList(String imgPar, List<File> pathList, List<String> targetPathList, int quality) {
        for (int i = 0; i < pathList.size(); i++) {
            addCompressImagePath(imgPar, pathList.get(i), targetPathList.get(i), quality);
        }
    }

    /**
     * 单图片上传
     *
     * @param imgPar 上传填入参数，上传有所不同 如：img
     */
    public void addImagePath(String imgPar, File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        imgMap.put(imgPar + "" + "\";filename=\"" + file.getName(), requestBody);
    }

    /**
     * 多图片不压缩上传
     */
    public void addImagePathList(String imgPar, List<File> pathList) {
        for (int i = 0; i < pathList.size(); i++) {
//            addImagePath(imgPar, pathList.get(i));
            addCompressImagePath(imgPar, pathList.get(i), HttpConstantValue.PATH_IMG + i + ".png", 80);
        }
    }

    /**
     * 图文上传的  文
     */
    private void putData() {
        imgMap.put("data", RequestBody.create(MediaType.parse("text/plain"), getData()));
    }

    public HashMap<String, RequestBody> getImgData() {
        putData();
        return imgMap;
    }

    private void putNormalData() {
        imgMap.put("data", RequestBody.create(MediaType.parse("text/plain"), getData()));
    }

    public HashMap<String, RequestBody> getImgNormalData() {
        putNormalData();
        return imgMap;
    }
}
