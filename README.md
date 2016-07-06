# colorfulcloudslibrary
 ## android UI 快速开发Base框架
  代码示例
  
```java
//-----------------------------------------Activity------------------------------------------
    //**实际项目 MyBaseActivity 
    public abstract class  MyBaseActivity extends TBaseActivity{
    
         @Override
        public void onLayoutLoading() {
            //这里初始化 titleBar  和 statusBar
             setStatusColor(R.color.testmodelblue);
             getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.testmodelblue));
             getTitleBar().setCenterNormalTextView("引用colorfulCloundsLibrary").setTextColor(Color.WHITE);
             initLayout();
        }
        
        //提供给子类操作
        public abstract  void initLayout();
    
        //提供几个子类加载resid的方法
        
        public void setStatusColor(int resid){
                super.reSetStatusColor(getResources().getColor(resid));
           }
            
         public View inflater(int layoutId){
            return LayoutInflater.from(this).inflate(layoutId,null);
        }
        
        public void loadContentView(int layoutId){
          loadContentView(inflater(layoutId));
         }
  
        public void loadContentView(View view){
              super.setContentLayout(view);
          }
    }
    
    //目标Activity
    public class TextAct extends MyBaseActivity {
    
        @Bind(R.id.gosliding)RippleView gosliding;
        @Bind(R.id.goButtomNavigate) RippleView goButtomNavigate;
    
        @Override
        public void initLayout() {
            setStatusColor(R.color.testmodelblue);
            getTitleBar().setTitleBarBackgroundColor(getResources().getColor(R.color.testmodelblue));
            getTitleBar().setCenterNormalTextView("引用colorfulCloundsLibrary").setTextColor(Color.WHITE);
            loadContentView(R.layout.act_main);
            ButterKnife.bind(this);//在MyBaseActivity 中初始化 自定义View绑定会失效（原因不明）
            setOnRippleComplete(gosliding,goButtomNavigate);
        }
    }
   
   //-------------------------------------Fragment ------------------------------------
    //实际项目 FragmentGroupActivity
     public class AbFragmentGroupAct extends TBaseFragmentGroupActivity {
            @Override
                public int fragmentContainerId() {//返回内容布局的FrameLayout id
                    return R.id.bn_buttomNaviagte_framelayout;
                }
                @Override
                public View setLayoutView() {//返回包含FrameLayou 的布局View
                    return LayoutInflater.from(this).inflate(R.layout.act_buttomnavigate,null);
                }
        
        
           @Override
            public void onLayoutloaded() {
                
                //根据时间 选择替换Fragment 直接调用调用switchFragment(Class<?extends TBaseFragment>);
                 
            
            }
        }
        
       //实际项目BaseFragment
        public class AbFragment extends TBaseFragment {
            /**
             * 覆盖上层TbaseActivity 避免 在getResoures 查找资源失败
             */
            private AbFragmentGroupAct abGroupActivity;
        
            @Override
            public void onAttach(Activity activity) {
            //上层会检查activity 是否继承子TBaseFragmentGroupActivity，
            //并抛出异常 colorful- This Activity does not extends TBaseFragmentGroupActivity !!!!!!!!
                super.onAttach(activity);
                //一定要在super 之后调用
                this.abGroupActivity = (AbFragmentGroupAct) activity;
            }
        
            //提供给子类安全的Activity引用
             @Override
            public TBaseFragmentGroupActivity getTBaseFGActivity() {
                return abGroupActivity;
            }
        
            //必须实现的初始化布局方法
             @Override
            public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                    
        
            }
        }
        
        //目标Fragment
        public class FragmentA extends AbFragment {
         @Override
            public void onInitLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                view = inflater.inflate(R.layout.fragment_a,container,false);
                setContentLayout(view);
                ((TextView)view.findViewById(R.id.content)).setText(getText());
        
            }
        }

```
