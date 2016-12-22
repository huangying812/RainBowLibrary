## tbablibrary 封装类说明
[**AbAdapter**](https://github.com/HarkBen/RainBowLibrary/blob/master/tbablibrary/src/main/java/tbablibrary/com/commoniml/AbAdapter.java)
适用于ListView ，对BaseAdapter实现需要的通用代码进行封装，继承使用只需要提供构造实现```java void bindData(WidgetHolder holder,T model);```方法即可