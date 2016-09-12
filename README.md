<img src="https://github.com/HarkBen/RainBowFor-Android/blob/master/pictures/try1.gif" width="30%" />
# Rainbowlibrary
##### RainBowLibrary以一个Android 开发的简易底层库，用于快捷搭建项目，UI 默认实现沉浸式状态栏和MD风格，顶部TitleBa,在Activity和Fragment中显示Loding Logo。主题切换，语言切换。提供主流项目的导航主页，且不会局限开发需求，只提供内部Fragment切换管理。陆续加入Http相关封装，也正在学习使用 RxJava + okhttp + Retrofit


### Gradle
>
>    Step 1. 在你的根build.gradle文件中增加JitPack仓库依赖。
>    
    ```java
        allprojects {
            repositories {
              jcenter()
              maven { url "https://jitpack.io" }
            }
        }
>    
>   Step 2. 在你的model的build.gradle文件中增加RainbowLibrary依赖。
>
    ```java
    dependencies {
    	        compile 'com.github.HarkBen:RainBowLibrary:1.0.1'
    	}
    

    
   
   


