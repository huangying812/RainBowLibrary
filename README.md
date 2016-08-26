# rainbowlibrary 
> TbaseActivity 继承自AppCompatActivity,提供快速搭建新项目的底层实现，正在完善。。。我还不是老司机，努力中。。。
> 后面陆续会加入常用的UI控件及网络请求.
>
>###### -部分代码示例-  详情参考simpleDemo即可
>
>## 基础模块 
>
1. 提供TbaseTitleBar-类似ActionBar更方便自定义
2. TBaseFragmentGroupActivity 可快速搭建起一个带导航的主页 提供Fragment管理（切换和缓存）
3. 快速主题切换和语言切换
4. 全局可使用的loading动画 也支持自定义drawable
5. 向下兼容的透明状态栏-可删除后设置沉浸式
6. 侧滑菜单

>## http
>

>#### TbaseActivity
>
>    只是为了包装基础模块，给出拓展方法和部分实现。
     已经做了context保存
     关闭所有activity的方法
```java
    public void finishAllAct(){
            if(null != actList && actList.size()>0){
                for(TBaseActivity act:actList){
                    if(null != act){
                        act.finish();
                    }
                }
            }


```
>#### TbaseTitleBar 使用
>

```java
 @Override
    public void initLayout() {
        setStatusColor(R.color.testmodelblue);
//        removeBaseTitleBar();
//        removeStatusBar();
        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.colorAccent));
        getTitleBar().setCenterNormalTextView("Title").setTextColor(Color.WHITE);
        loadContentView(R.layout.act_main);
        ButterKnife.bind(this);
        setOnRippleComplete(gosliding,goButtomNavigate);
    }
```    
>#### TBaseFragmentGroupActivity 使用
>

```java       
 public class AbFragmentGroupAct extends TBaseFragmentGroupActivity{
  //返回 将用来显示fragment的 frameLayout id !! id
     @Override
     public int fragmentContainerId() {
         return R.id.bn_buttomNaviagte_framelayout;
     }
     //返回 layout xml id
     @Override
     public View setLayoutView() {
         return LayoutInflater.from(this).inflate(R.layout.act_buttomnavigate,null);
     }
     
     @Override
     public void onLayoutloaded() {
     
     //上层提供的切换Fragment的方法 必须继承自TbaseFragment
     switchFragment(Class<?extends TBaseFragment> clazz);
 }
 
```
>#### 切换主题及切换语言
>
```java
 /**
     * 启用重绘 更换主题
     * @param styleId
     */
    public void switchTheme(int styleId,@ColorInt int color){
        SharedPUtils.saveInt(this,SharedPUtils.THEME_ID,styleId);
        SharedPUtils.saveInt(this,SharedPUtils.THEME_COLOR,color);
        reStartWindow();
    }
      /**
         *如果要单页面执行语言切换 请在不要设置当前Act的启动模式 为 single task 否则不起效果
         * -详情看act的4种启动模式
         * @param resources res资源管理器 
         * @param tag LanguageTAG 中的语言TAG
         */
        public void switchLanguage(Resources resources , String tag){
            reSetLanguageConfiguration(resources,tag);
            reStartWindow();
            }
```

>#### 在TBaseFragmentGroupActivity的基础上实现的侧滑SlidingMenuActivity
>

```java
**
 * author  z.
 * time  2016/8/26.
 * email  z.com.cn
 * Description-
 */
public class SlidingMenuAct2 extends SlidingMenuActivity {
    @Override
    public void onInitView() {
        //初始化菜单按钮的事件 及其他view的绑定都在这里操作
        getTitleBar().setCenterNormalTextView("侧滑菜单");
        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.mohei_tp));
        getTitleBar().setLeftNormalButton(new TbaseTitleBar.OnTbaseTitleLeftViewClickListener() {
            @Override
            public void onClick(View v) {
               openMenu();
            }
        }).setBackgroundResource(R.mipmap.back_f);
        switchFragment(FragmentA.class);

    }

    @Override
    public View setMenuView() {
        return LayoutInflater.from(this).inflate(R.layout.menu_layout,null);
    }

}

```