**今天不Code 明天就GG**
    
 
# Rainbowlibrary
*   RainBowLibrary是一个方便搭建Android项目的库(TBaseActivity,TitleBar,Fragment管理，常用的View)，
    UI 默认实现沉浸式状态栏和MD风格，全局获取TitleBa,在Activity和Fragment中显示Loding Logo。
    主题切换，语言切换。抽象出了侧滑菜单，只提供内部Fragment切换管理。

### Gradle
>   Step 1. 在你的根build.gradle文件中增加JitPack仓库依赖。
   
```gradle
         allprojects {
                repositories {
                 jcenter()
                 maven { url "https://jitpack.io" }
                }
            }
```
    
>   Step 2. 在你的module的build.gradle文件中增加RainbowLibrary依赖。

```gradle
            dependencies {
    	                 compile 'com.github.HarkBen:RainBowLibrary:1.0.1'
    	       }
```

### rainbowlibrary 的使用

### TbaseActivity & TbaseFragment

1. **T extends TbaseActivity** 
<p>  选择使用或者不使用TbaseTitleBar ,</p> 
<p>  可以隐藏和删除，statusBar也可以删除，但都不提供再重新添加</p>
<p>  需要使用loadContentView(View v)方法设置布局，方便点再加个BaseAct 做中间层提供资源初始化和Http初始化
>
 
    ```java
                @Override
                public void onLayoutLoading() {
                        getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.testmodelblue));
                        setStatusColor(R.color.testmodelblue);
                        getTitleBar().setLeftNormalButton(new TbaseTitleBar.OnTbaseTitleLeftViewClickListener() {
                      @Override
                      public void onClick(View v) {
                             finish();
                             }
                        }).setBackgroundResource(R.mipmap.back_f);
                                 //removeTBaseTitleBar();
                                 // removeStatusBar();
                                 loadContentView(R.layout.act_login);
                                  ButterKnife.bind(this);
               } 
               ···日常省略
    ```  
                                      
  
2. **切换主题**
   * 先保存一个默认主题用来显示: 

    ```java
         SharedPUtils.saveNormalTheme(this,R.style.AppTheme,Color.BLUE);
                 switchTheme(R.style.AppTheme,Color.BLUE);
    ```
    
   * 在需要的地方切换:

    ```java
            switchTheme(R.style.AppTheme,Color.BLUE);  
    ```
            
3. **切换语言**
   * 先保存一个默认语言

    ```java
        SharedPUtils.saveLanguageSetting(this, LanguageTAG.ZH);
    ```
        
   * 切换语言
        
    ```java
        switchLanguage(getResources(),LanguageTAG.FRENCH);
    ```

4. **初始加载动画**

    ```java
            startLoadAnim();
            stopLoadAnim();
    ```

5. **Fragment切换**   
   * T extends TBaseFragmentGroupActivity
   * 使用switchFragment()
    
    ```java
         
        public abstract class TBaseFragmentGroupActivity extends TBaseActivity {
                public TBaseFragment switchFragment(Class<?extends TBaseFragment> clazz){
                     return switchFragment(fragmentContainerId(),clazz);
                }
                ···日常省略
        }
        
        class T extends TBaseFragmentGroupActivity{
                    ···日常省略
             ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
                        @Override
                        public boolean onTabSelected(int position, boolean wasSelected) {
                                switchFragment(map.get(position));
                            return true;
                        }
                    });
                    ···日常省略
        }
    ```
        
6. **日志跟踪和打印**
    * 提供日志开启关闭 默认开启  
    ```java
    
    //L.setDeBug(true);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printLogE("");
    //输出结果--rainbowL--runing>>com.zsw.testmodel.ui.act.SlidingMenuAct>>)
    }
    
    ```
           
    ```java
        public String printLogD(String log){
               L.printD(getClass().getName(),log);
               return getClass()+">msg=="+log;
           }
           public String printLogE(String log){
               L.printE(getClass().getName(),log);
               return getClass()+">msg=="+log;
           }
               
            
    ```     
      
    * 或者 
        
    ```java
             L.printD(getClass().getName(),log);
    ```
    未完待续。。。
***         
         
      
         
            
        
        



 

    
   
   


