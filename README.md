**今天不Code 明天就GG**
  <br />
 ![try1.gif](./pictures/try1.gif)
# Rainbowlibrary
*   RainBowLibrary以一个Android 开发的简易底层库，用于快捷搭建项目，
    UI 默认实现沉浸式状态栏和MD风格，顶部TitleBa,在Activity和Fragment中显示Loding Logo。
    主题切换，语言切换。提供主流项目的导航主页，只提供内部Fragment切换管理。陆续加入Http相关封装，
    也正在学习使用 RxJava + okhttp + Retrofit

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

### rainbowlibrary 的使用
>截图
<br />
   	![login.png](./pictures/login.png) ![ui1.png](./pictures/ui2.png)
   	<br />
   	![ui2.png](./pictures/ui3.png) ![ui3.png](./pictures/ui3.png)
   <br />
   	![ui4.png](./pictures/ui4.png)   	

    	
### 为了能熟练使用Retrofit 我决定 两种方式(Observer ,callback ) 都使用，先从callback开始吧
    
   
   


