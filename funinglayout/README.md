>啊哈最近又闲下来了，但一直有个事儿没放下。那就是项目中用到的一个开源控件-圆形进度条，虽然样式和UI设计图完全一样了，但是体验我觉得还能提升，加个动画什么的。反正闲了点干脆自己写吧。

先上图：

![circleProgressView.gif](http://upload-images.jianshu.io/upload_images/2909203-bc398e69354e2968.gif?imageMogr2/auto-orient/strip)
属性使用：

```xml
<rg.funinglayout.views.CircleProgressView
        android:id="@+id/ac_CircleProgressView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:cp_underColor="@color/grey300"
        app:cp_progressColor="@color/colorPrimary"
        app:cp_progress="10"
        app:cp_reverseAngle="true"
        app:cp_max="100"
        app:cp_textColor="@color/colorPrimary"
        app:cp_textSize="25sp"
        app:cp_startAngle="270"
        app:cp_strokeWidth="15dp"
        app:cp_padding="5dp"
        app:cp_drawText="true"
        />
```
<br>
有了之前对View自定义绘制测量和参数的学习，在有了思路后很快就实现出来了。

* 主要就三个模块：
  1.弧角度计算绘制
  2.文字绘制
  3.进度切割动画


 * 踩坑：

 一开始在绘制`RectF`边界时使用了坐标确定位置，后来发现这是多余的，也是错误的。

 `drawArc `在在设置边界`RectF`时，只用考虑绘制面积，无需考虑绘制坐标。

  界限面积公式 ：S = ` getMeasureWidth() * getMeasureHeight()`

  也就是说 `rectF.top`,`rectF.letf`在无边距时就是 0 。

 实际起点就是 `padding`。

***
源码地址：[github - CircleProgressView](https://github.com/HarkBen/RainBowLibrary/blob/master/funinglayout/src/main/java/rg/funinglayout/views/CircleProgressView.java)



