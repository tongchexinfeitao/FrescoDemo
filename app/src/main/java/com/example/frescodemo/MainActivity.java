package com.example.frescodemo;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class MainActivity extends AppCompatActivity {

    private SimpleDraweeView simple_drawee_view;

    public static final String mImageUrl = "http://pic39.nipic.com/20140226/18071023_164300608000_2.jpg";
    public static final String mErrorUrl = "http://pic39.nipic.com/20140226/18071023_1643006080dddd00_2.jpg";
    public static final String mGifUrl = "http://img.zcool.cn/community/0139505792e5fc0000018c1bbb7271.gif";
    public static final String mJpegUrl = "http://attach.foyuan.net/portal/201308/03/09/2013080309223742492.jpg";

    /**
     * 1、SimpleDraweeView宽高必须设置为固定值
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化Fresco
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);


        Uri uri = Uri.parse(mJpegUrl);

        simple_drawee_view = (SimpleDraweeView) findViewById(R.id.simple_drawee_view);

        //第一种设置图片地址
//        simple_drawee_view.setImageURI(uri);


        //在java代码中设置view的一些xml属性 失败、重试
//        GenericDraweeHierarchy draweeHierarchy = new GenericDraweeHierarchyBuilder(getResources())
//                .setFailureImage(ContextCompat.getDrawable(this, R.drawable.icon_failure))
//                .build();
//
//        simple_drawee_view.setHierarchy(draweeHierarchy);

        BaseControllerListener<ImageInfo> baseControllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
//                animatable.start();
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        };

        //也可以控制图片请求的一些特性
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                //设置支持jpeg渐进式展示（从模糊到清晰）
                .setProgressiveRenderingEnabled(true)
                .build();


        //控制图片加载的一些特性
        AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                //第二种设置图片地址
//                .setUri(uri)
                //设置可以重试 （重试4次）
//                .setTapToRetryEnabled(true)
                //设置自动播放
//                .setAutoPlayAnimations(true)
                //监听图片加载
                .setControllerListener(baseControllerListener)
                .setOldController(simple_drawee_view.getController())
                .build();
        simple_drawee_view.setController(controller);
    }

    public void play(View view) {
        Animatable animatable = simple_drawee_view.getController().getAnimatable();
        animatable.start();
    }
}
