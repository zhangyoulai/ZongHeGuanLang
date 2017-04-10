# 美菱App开发文档
### 项目名称
   MES智能系统 meiling_android_mes
### Git地址：
git@git.tongbaner.com:meiling/meiling_android_mes.git


### 项目开发者
   成员一：王照森
   成员二：寇泽平
   成员三：汪栋甲

### 工具版本
AndroidStudio 2.1.1
#### 测试环境 :
MingJiang AIOP215 (Android 4.4.2,API 19)
#### gradle版本：
2.1.2
#### sdk
compileSdkVersion 24
buildToolsVersion "24.0.1"
minSdkVersion 14
targetSdkVersion 24

### build.gradle:
dependencies {
compile fileTree(dir: 'libs', include: ['*.jar'])
testCompile 'junit:junit:4.12'
compile 'com.android.support:appcompat-v7:24.1.1'
compile project(':')

}

### 额外用到的jar包
circlemenulib模块： nineoldandroids-2.4.0.jar
instruction模块： CrashHelper.jar
jdbc-api模块： jtds.jar
opcuaapi模块： opcua的jar包

### 解决使用HttpClient
错误: Error:(19, 23) 错误: 程序包org.apache.http不存在
原因：Android 6.0后,Android的网络请求强制使用HttpUrlConnection,SDK中已经移除了HttpClient。
解决方案：
1.org.apache.http.legacy.jar 导入工程即可。
2.android studio里在相应的module下的build.gradle中加入：
android {
useLibrary 'org.apache.http.legacy'
}
### 使用lambda
在项目的dependencies中添加：
classpath 'me.tatarka:gradle-retrolambda:2.5.0'
在模块中添加：
apply plugin: 'me.tatarka.retrolambda'

### 常用dependencies版本
compile 'de.greenrobot:eventbus:2.4.1'
compile 'com.squareup.retrofit:retrofit:1.8.0'
compile 'com.squareup.picasso:picasso:2.4.0'
compile 'io.reactivex:rxandroid:0.23.0'
compile 'com.google.zxing:core:3.2.1'
compile 'io.reactivex:rxandroid:0.23.0'
compile 'com.jakewharton:butterknife:6.1.0'
compile 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
compile 'com.squareup.okhttp:okhttp:2.2.0'
compile 'com.loopj.android:android-async-http:1.4.5'

## 模块说明：
4 系统功能设定
MES智能系统模块分为2大部分：基础模块 和 功能模块。

### 基础模块
#### 程序入口（app）
简介：实现跳转到各个功能模块，设置基本信息（基础URL，串口波特率），软件检测更新。
开发人员：王照森
实现方式：
1. 通过使用CircleMenuLib实现环形菜单跳转到各功能模块。
2. 点击中心图标弹出对话空，通过base模块设置基础URL，串口波特率。
3. 2步中点击检测更新，通过appupdata模块进行系统检测升级。

#### 软件自动更新模块（appupdata）
简介：通过请求服务器端存储的版本信息和apk包进行升级。
开发人员：寇泽平
实现方式：
1. 在主界面创建UpDataUtils对象，调用checkUpData方法即可。
mUpDataUtils=new UpDataUtils(MainMenuActivity.this);
try {
mUpDataUtils.checkUpData();
} catch (Exception e) {
e.printStackTrace();
Log.e(log, e.getLocalizedMessage());
}
2. 通过一次请求获取服务器端发布的版本号，与本地对比，当本地版本号与服务器不同时，下载apk并进行安装。
问题解决：有时apk无法完成自动安装（报出签名错误），需要手动删除程序，进入sd手动安装。

#### 基础设置、风格模块（base）

简介：进行基础URL，串口波特率设置，提供串口服务，Button和EditText分格，通过SharedPrefsUtil存储设置信息，ComEvent支持EventBus的信息传递。
开发人员：王照森
实现方式：
1.通过comapi实现串口的调用。
2.通过logapi实现日志记录。
3.通过supertoast实现界面友好的Toast。
4.通过androidbootstrap实现界面友好的基础控件风格。
5.通过herilyalertdialog实现界面友好的对话框。

#### 扫描登录模块（scan）

简介：通过监听串口数据，得到岗位编码和用户名称，发送到服务端进行验证。
开发人员：王照森
实现方式：
1.通过PostScanActivity获取岗位编码，并跳转到用户扫描界面。
2.通过UserScanActivity获取用户编码，并进行验证，成功后将岗位编码和session_id加入intent跳转到对应页面。
3. 启动岗位扫描时传入一个标记，记录在ScanApp（单例类）
String functionType = intent.getStringExtra(Constants.FUNCTION_TYPE_NAME);
ScanApp.functionType = functionType;//获取当前功能点类型
4. 验证成功后，通过标记来判断要跳转到哪个Activity
if(Constants.OPER_INSTRUCTION.equals(ScanApp.functionType)){


### 功能模块

#### 生产监控模块（spectaculars）
简介：实现设备状态，线体状态，订单状态，生产状态，物料状态，品质状态的实时显示。
开发人员：寇泽平
实现方式：
1. 主界面通过Viewpage和Fragment实现各功能之间切换，上方通过radioButton指示和切换当前界面，通过下方sppner设置刷新和轮播间隔。
2.各模块通过主界面设置的刷新时间自动更新显示。

#### 质量检测模块（inspection）
简介：展示质检岗位信息，并进行本岗位的质检。
开发人员：寇泽平
实现方式：
1.通过MainActivity进行用户验证，成功后跳转到检测页面。
2.通过扫描冰箱二维码，获取服务器端此冰箱的质检信息。
3.质检完成是通过三种方式提交检测结果：
A.扫描检测合格（result：1），扫描提交（submit）；
B.扫描检测不合格（result：0）可以跳过此步骤。扫描不合格原因（reason1：原因码），扫描不合格位置（position：位置），扫描提交；
C.扫描不合格原因（reason1：原因码），扫描不合格位置（position：位置），扫描检修人员；（线上维修，检测结果是合格）

#### 岗位指导书模块（instruction）

简介：上传冰箱信息，显示岗位指导书。
开发人员：王照森
实现方式：
1.通过请求获取岗位指导书信息，显示文字信息和加载图片，将json  数据和图片进行文件缓存。
2.通过对比获取到岗位指导书的信息，判断是否需要下载，不需要时直接显示缓存数据。

#### 岗位指导书模块（instructionpdf）

简介：上传冰箱信息，显示Pdf版的岗位指导书。
开发人员：寇泽平
实现方式：
1.通过扫描获得冰箱码，并通过接口将数据已json的格式传送mes端。
2.设置webview的基础属性
// 设置可以访问文件
webView.getSettings().setAllowFileAccess(true);
//如果访问的页面中有Javascript，则webview必须设置支持Javascript
webView.getSettings().setJavaScriptEnabled(true);
webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
webView.getSettings().setAllowFileAccess(true);
webView.getSettings().setAppCacheEnabled(true);
webView.getSettings().setDomStorageEnabled(true);
webView.getSettings().setDatabaseEnabled(true);
3.设置Cookie
CookieSyncManager.createInstance(context);
CookieManager cookieManager = CookieManager.getInstance();
cookieManager.setAcceptCookie(true);
StringBuilder sbCookie = new StringBuilder();
sbCookie.append(String.format("session_id=%s", sessionId));
sbCookie.append(";path=/");
//;domain=domain;
String cookieValue = sbCookie.toString();
cookieManager.setCookie(url, cookieValue);
CookieSyncManager.getInstance().sync();
String newCookie = cookieManager.getCookie(url);
4.通过webView.loadUrl(url)加载岗位指导书。
5.将pdf文件的id和last_update作为key，通过map存3个webview实现一个容量为3的缓存，减少网络加载的次数。
String fileName = filePath.getId() + (filePath.get__last_update().replace(" ", "").replace(":", "")) + ".pdf";

#### 线边仓库模块（linestorage）

简介：显示线边库物料信息，实现收料，手动叫料，物料调整，退料操作。
开发人员：寇泽平
实现方式：
1.通过VeiwPage和Fragment实现不同功能之间的切换。
2.收料时，第一次请求此下料点需要卸哪些料，卸料完成后点击卸料完成按钮，通知mes端卸料完成。
3.手动叫料时，通过点击物料后面的叫料按钮实现叫料，mes端将返回本次操作的结果。
4.物料调整时，通过物料后方的+、-按钮确定调整的数量，生成调整清单，无误后点击提交按钮。
5.退料时，通过物料后方的+按钮确定退料的数量，生成退料清单，无误后点击退料按钮提交结果。

#### 物料管理模块（matrtials）

简介：显示中间库，线边库物料信息，实现一键入库操作。
开发人员：王照森
实现方式：
1.通过VeiwPage和Fragment实现不同功能之间的切换。
2.可以通过物料编码、物料名称、库位编号、库区编号、页面大小，当前页面筛选中间库物料显示。
3.可以通过物料编码、物料名称、库位编号、库区编号、页面大小，当前页面筛选线边库物料显示。
4.通过扫描出库单编号，获取出库单信息，点击入库操作即可实现一键入库（未完成功能，需求和实施方法不明确）

#### 上下线点模块（onoffline）

简介：通过扫描冰箱，设置冰箱的上下线。
开发人员：王照森
实现方式：
1.通过扫描获取冰箱信息，设置上线或下线，提交上下线信息到服务端。

#### 预装打码（qrcode）

简介：自动读取日计划，根据美菱编码规则生成二维码，关联订单后传到mes端；提供补打码和报废流水号功能。
开发人员：汪栋甲
实现方式：
1. 主页面显示日计划，可跳转到补打码页面和打印报废流水号页面
2. 打印报废流水号：输入报废单号传到mes，读取到二维码并打印
3. 打印时，如果队列中没有数据，同时给2号打印机发送指令
	加一个状态码 true false
	2号打印机请求时 如果没码 false 有码true
	再次点击打印时判断状态码
//读取日计划
api/interface/public/plan_manage.daily_plan/process_serial_element
入参 日期
//上传订单号+二维码
api/interface/json/public/process_control.process/trace_process
入参 {"params": {"args": [],"kwargs": {"serial_number": "ML3","order_id": "1"}}}
出参
//报废订单 流水号
api/interface/public/quality_manage.scrapped_product/print_code

//
api/interface/public/process_control.process/count_printed_by_daily

#### 线尾一体机打印送货单模块（deliverynotes）

简介：扫描二维码打印送货单。
开发人员：汪栋甲
实现方式：
1. 建立本地数据库，维护送货单表单信息
2. 扫描到二维码，提取R3码和本地数据库做比对得到送货单并打印

#### 一维码二维码比对关联模块（compare）
简介：一维码二维码防错、关联
开发人员：汪栋甲
实现方式：





