# rainbowlibrary Doc
## TbaseActivity 继承自AppCompatActivity,风骚的MD风格你懂的。
>下面放技能+5毛特效 -部分代码示例-  详情参考simpleDemo即可
<
1. 提供TbaseTitleBar-类似ActionBar比ActionBar方便自定义
2. TBaseFragmentGroupActivity 可快速搭建起一个带导航的主页 提供Fragment管理（切换和缓存）
3. 快速主题切换和语言切换
4. 全局可使用的loading动画 也支持自定义drawable动画
5. 向下兼容的透明状态栏-可删除后设置沉浸式

> TbaseTitleBar 使用<
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
>TBaseFragmentGroupActivity 使用<
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
