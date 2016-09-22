package com.zsw.rainbowlibrary.utils.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;


/**
 * author  z.sw
 * time  2016/8/2.
 * email  zhusw@visionet.com.cn
 String imageUri = "http://site.com/image.png"; // from Web
 String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
 String imageUri = "content://media/external/audio/albumart/13"; // from content provider
 String imageUri = "assets://image.png"; // from assets
 String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
 */
public class ImageLoaderUtils {
    private static ImageLoader imageLoader;
    /**
     * description 初始化ImageLoader
     * @param context
     * @param cachefile
     */
    public  static void initImageloader(Context context,File cachefile,DisplayImageOptions option){
        if(null == imageLoader) {
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
            builder.memoryCacheExtraOptions(480, 800)//max width max height 保存文件最大宽高
                    .threadPoolSize(3)// 防止 outOfMemory 最好设置在 1 - 5 个 ，当局部过大时应减少并发数
                    .threadPriority(Thread.NORM_PRIORITY - 1)//设置 线程 优先级 比默认向下退位1个level
                    .denyCacheImageMultipleSizesInMemory() //避免在缓存文件中有多个尺寸
                    .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))//内存缓存方式 可以继承重写为自己的方式。具体木有研究
                    .memoryCacheSize(2 * 1024 * 1024)//内存缓存
                    .diskCacheSize(50 * 1024 * 1024)//缓存大小
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将缓存的图片url用MD5加密
                    .tasksProcessingOrder(QueueProcessingType.LIFO)//加载进程 任务处理顺序 last in first out 后进先出 FIFO 先进先出
                    .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000));//设置time out 5s 设置加载图片超时 30秒

            if (null != cachefile && cachefile.exists()) {
                builder.diskCacheFileCount(200)//最大缓存文件个数
                        .diskCache(new UnlimitedDiscCache(cachefile));//自定义缓存路径
            }
            if (null != option) {
                builder.defaultDisplayImageOptions(option);
            } else {
                builder.defaultDisplayImageOptions(DisplayImageOptions.createSimple());
            }
            builder.writeDebugLogs();//remove for release app

            //初始化
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(builder.build());
        }
    }

    public static ImageLoader getInstance(){

        synchronized(ImageLoaderUtils.class){
            if(null == imageLoader) {
                throw new IllegalArgumentException("ImageLoaderUtils.imageLoader not init");
            }
            return  imageLoader;
        }
    }
    /**
     *
     * @param url
     * @return
     */
    public static String checkUrl(String url){
        return url.substring(0,7).equals("http://")?url:"file:///"+url;

    }

    public static void loadImageByOptions(ImageView imageView,String url,DisplayImageOptions options){
        if(null == imageLoader) {
            throw new IllegalArgumentException("ImageLoaderUtils.imageLoader not init");
        }
        imageLoader.displayImage(checkUrl(url),imageView,options);
    }

    public static void loadImageNormal(ImageView imageView,String url){
        if(null == imageLoader) {
            throw new IllegalArgumentException("ImageLoaderUtils.imageLoader not init");
        }
        imageLoader.displayImage(checkUrl(url),imageView);
    }

    /**
     *
     * @param onLoadingResId 加载中的 图片id
     * @param onLoadErrorResId 加载错误的图片id
     * @return
     */
    public static DisplayImageOptions getOptonsRadius(int onLoadingResId,int onLoadErrorResId){
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(onLoadingResId) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(onLoadingResId)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(onLoadErrorResId)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options.)//设置图片的解码配置
                //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                //设置图片加入缓存前，对bitmap进行设置
                //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
//                .displayer(new RoundedBitmapDisplayer(radius))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
        return  options;
    }


}
