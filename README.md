# TinyBlog

基于 WordPress 的个人博客客户端

[TinyBlog 体验地址](https://github.com/IamXiaRui/TinyBlog/blob/master/app/app-release.apk)

## 博客地址

[XiaRui's Zoon - 填满编程路上遇到的每一个坑](http://www.iamxiarui.com/)

## 博客内容 JSON 插件

插件名称：**JSON API**

[插件文档地址](https://wordpress.org/plugins/json-api/)

[插件文档翻译地址](http://www.iamxiarui.com/2016/09/03/%E3%80%90%E8%AF%91%E3%80%91json-api-for-wp-%E6%96%87%E6%A1%A3%E7%BF%BB%E8%AF%91/)

## 软件介绍

软件的功能很简单，就是个人博客的 Android 客户端，主要功能有：

* 博客的阅读、归档、评论与新增
* 知乎日报每日精选、 Github Trending 
* 天气小部件

### 原型制作

![最近与分类](http://www.iamxiarui.com/wp-content/uploads/2017/05/1-1-2.png)

![发现与个人](http://www.iamxiarui.com/wp-content/uploads/2017/05/1-3-4.png)

![分类子列表与新建](http://www.iamxiarui.com/wp-content/uploads/2017/05/2-1-2.png)

![详情与评论](http://www.iamxiarui.com/wp-content/uploads/2017/05/2-3-4.png)

### 代码实现

![主页面](http://www.iamxiarui.com/wp-content/uploads/2017/05/main.gif)

![发现](http://www.iamxiarui.com/wp-content/uploads/2017/05/find.gif)

![分类](http://www.iamxiarui.com/wp-content/uploads/2017/05/cate.gif)

![新建](http://www.iamxiarui.com/wp-content/uploads/2017/05/save.gif)


## 说明

### 已知问题

* 有几个小小的 UI 和 网络请求 Bug
* 没有做适配工作
* 个别地方 GPU 渲染过度，卡顿明显
* 发表文章需要 WordPress 权限
* 富文本编辑器显示异常
* 天气 API 有未知错误
* 多说 API 停止支持

### TODO

* 开放新增文章权限
* 重换评论与天气 API
* 重换富文本编辑器
* 网络优化、布局优化与性能优化
* 一些简单的适配

## 开源支持

特别感谢以下开源库的支持，以开源库最新版本为准。

|开源库|作者|说明|
|:---:|:---:|:---:|
|[BottomNavigation](https://github.com/Ashok-Varma/BottomNavigation)|Ashok-Varma|This Library helps users to use Bottom Navigation Bar (A new pattern from google) with ease and allows ton of customizations.|
|[MaterialEditText](https://github.com/rengwuxian/MaterialEditText)|rengwuxian|EditText in Material Design.|
|[banner](https://github.com/youth5201314/banner)|youth5201314|Android广告图片轮播控件，支持无限循环和多种主题，可以灵活设置轮播样式、动画、轮播和切换时间、位置、图片加载框架等！|
|[glide](https://github.com/bumptech/glide)|bumptech|An image loading and caching library for Android focused on smooth scrolling.|
|[okhttputils](https://github.com/hongyangAndroid/okhttputils)|hongyangAndroid|okhttp的辅助类.|
|[okhttp](https://github.com/square/okhttp)|square|An HTTP+HTTP/2 client for Android and Java applications.|
|[labelview](https://github.com/linger1216/labelview)|linger1216|Sometimes, we need to show a label above an ImageView or any other views. Well, LabelView will be able to help you. It's easy to implement as well!|
|[AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)|Blankj|Android developers should collect the following utils(updating).|
|[Richtext](https://github.com/zzhoujay/RichText)|zzhoujay|Android平台下的富文本解析器，支持Html和Markdown。|
|[TagCloudView](https://github.com/kingideayou/TagCloudView)|kingideayou|支持 SingleLine 模式的标签云效果。|
|[MaterialList](https://github.com/dexafree/MaterialList)|dexafree|An Android library aimed to get the beautiful CardViews that Google shows at its official design specifications.|
|[recyclerview-animators](https://github.com/wasabeef/recyclerview-animators)|wasabeef|An Android Animation library which easily add itemanimator to RecyclerView items.|
|[RecyclerViewItemAnimators](https://github.com/gabrielemariotti/RecyclerViewItemAnimators)|gabrielemariotti|An Android library which provides simple Item animations to RecyclerView items.|
|[Android-AdvancedWebView](https://github.com/delight-im/Android-AdvancedWebView)|delight-im|Enhanced WebView component for Android that works as intended out of the box.|
|[FloatingActionButton](https://github.com/Clans/FloatingActionButton)|Clans|Android Floating Action Button based on Material Design specification.|
|[3dTagCloudAndroid](https://github.com/misakuo/3dTagCloudAndroid)|misakuo|Tagcloud component for android.|
|[material-dialogs](https://github.com/afollestad/material-dialogs)|afollestad|A beautiful, fluid, and customizable dialogs API.|
|[DBFlow](https://github.com/Raizlabs/DBFlow)|Raizlabs|A blazing fast, powerful, and very simple ORM android database library that writes database code for you.|
|[icarus-android](https://github.com/mr5/icarus-android)|mr5|The best rich text editor for android platform, which includes more completely features. 安卓富文本编辑器|

## License

	Copyright 2015 Dmytro Tarianyk
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
