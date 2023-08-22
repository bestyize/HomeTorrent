# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-ignorewarnings
-dontoptimize
-dontpreverify
-keepattributes SourceFile,LineNumberTable,Exceptions,InnerClasses,Signature,EnclosingMethod,*Annotation*
# rename attribute SourceFile
-renamesourcefileattribute BL


-assumenosideeffects class android.util.Log {
    public static *** v(...);
}
# Add any project specific keep options here:

-keepclassmembers class * {
   public <init>(android.content.Context);
}

-keep class androidx.annotation.**{ *;}
-keep class androidx.fragment.app.Fragment* {*;}
-keep class androidx.fragment.app.DialogFragment {*;}
-keep public class com.google.android.material.**{public *;}
-keep class com.google.android.material.floatingactionbutton.FloatingActionButtonImpl {*;}
-keep class com.google.android.material.floatingactionbutton.FloatingActionButton { <fields>;}

-keep class com.home.torrent.model.**{*;}
-keep class com.google.gson.**{*;}
-keep class com.home.torrentcenter.**{*;}
-keep class org.jsoup.**{*;}

# used in xml
-keepclasseswithmembers class * extends androidx.preference.PreferenceFragmentCompat {
    public <init>();
}
-keep public class * extends androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior {<methods>;}
-keepnames class * extends androidx.recyclerview.widget.RecyclerView$LayoutManager
-keepnames class * extends androidx.fragment.app.Fragment
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable { <fields>;}

-keep interface * extends android.os.IInterface {public <methods>;}

-keepnames public class tv.danmaku.android.annotations.**{*;}
# native methods
-keep class tv.cjump.jni.*
-keepclassmembers class * { native <methods>; }


### all player
-keep class tv.danmaku.ijk.media.**{*;}

### web view ###
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepclassmembers class * extends android.webkit.WebChromeClient{ <methods>; }

-keepattributes Signature
-keepclassmembers class * implements java.io.Serializable { *; }

### kotlin kotlin-reflect
# Keep Metadata annotations so they can be parsed at runtime.
-keep class kotlin.Metadata { *; }
-keep class * implements kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class kotlinx.coroutines.CoroutineExceptionHandler {}

# Keep implementations of service loaded interfaces
# R8 will automatically handle these these in 1.6+
-keep interface kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader
-keep class * implements kotlin.reflect.jvm.internal.impl.builtins.BuiltInsLoader { public protected *; }
-keep interface kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition
-keep class * implements kotlin.reflect.jvm.internal.impl.resolve.ExternalOverridabilityCondition { public protected *; }

# Keep generic signatures and annotations at runtime.
# R8 requires InnerClasses and EnclosingMethod if you keepattributes Signature.
-keepattributes InnerClasses,Signature,RuntimeVisible*Annotations,EnclosingMethod

# Don't note on API calls from different JVM versions as they're gated properly at runtime.
-dontnote kotlin.internal.PlatformImplementationsKt

# Don't note on internal APIs, as there is some class relocating that shrinkers may unnecessarily find suspicious.
-dontwarn kotlin.reflect.jvm.internal.**

### bugly
# https://bugly.qq.com/docs/user-guide/instruction-manual-android/
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.** { *; }

###ucrop
-dontwarn com.yalantis.ucrop**

# eventbus

-keepattributes *Annotation*


#eventbus 混淆
-keepclassmembers class ** {
    public void onEvent*(**);
}


#rx java
-dontwarn sun.misc.**

# retrofit2
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8
-keepnames class retrofit2.http.**
-keepclasseswithmembernames interface * {
    @retrofit2.http.GET <methods>;
    @retrofit2.http.POST <methods>;
}

##for clip
-keep class com.coremedia.iso.** { *; }
-keep interface com.coremedia.iso.** { *; }

-keep class com.mp4parser.** { *; }
-keep interface com.mp4parser.** { *; }

-keep class com.googlecode.mp4parser.** { *; }
-keep interface com.googlecode.mp4parser.** { *; }
-dontwarn com.coremedia.iso.boxes.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.mjpeg.**
-dontwarn com.googlecode.mp4parser.authoring.tracks.ttml.**


# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn com.android.volley.toolbox.**
-dontwarn com.facebook.infer.**


#for event bus
# http://greenrobot.org/eventbus/documentation/proguard
# Don't warn for missing support classes
-dontwarn de.greenrobot.event.util.*$Support
-dontwarn de.greenrobot.event.util.*$SupportManagerFragment

-keepattributes *Annotation*

-keepnames public class com.google.android.material.bottomnavigation.BottomNavigationView { *; }
-keepnames public class com.google.android.material.bottomnavigation.BottomNavigationMenuView { *; }
-keepnames public class com.google.android.material.bottomnavigation.BottomNavigationPresenter { *; }
-keepnames public class com.google.android.material.bottomnavigation.BottomNavigationItemView { *; }

-keepclassmembernames class androidx.appcompat.widget.PopupMenu { private androidx.appcompat.view.menu.MenuPopupHelper mPopup; }
-keepclassmembernames class androidx.appcompat.view.menu.MenuPopupHelper { public void setForceShowIcon(boolean); }

# wire
-keepnames class com.squareup.wire.** { *; }


-keepclassmembernames class androidx.customview.widget.ViewDragHelper { private int mEdgeSize; }

# for sobot picasso
-dontwarn com.squareup.okhttp.**

# wexin
-keepnames class com.tencent.**
-dontnote com.tencent.stat.**
# sina
-keepnames class com.sina.**
-dontnote com.sina.**
## umeng ###
-keepclassmembers class * {
    public <init>(org.json.JSONObject);
}
-keepnames class u.aly.**

# The rules introduced by dependencies exposed too many
# notes that we can do nothing in this file. Let them go!
-dontnote com.mall.**
-dontnote com.sobot.chat.**
-dontnote com.alipay.**
-dontnote kotlin.**
# okhttp
-dontnote okhttp3.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

-printconfiguration build/merged-rules.txt

# ProGuard configurations for Bonree-Agent
-keep public class com.bonree.**{*;}
-keep public class bonree.**{*;}
-dontwarn com.bonree.**
-dontwarn bonree.**

-keep class com.intertrust.wasabi.** {*;}

# meishe sdk
-keep class com.cdv.**  {*;}
-keep class com.meicam.**  {*;}
-keep class com.sensetime.** {*;}
-keep class com.meishe.** {*;}
-keep class org.qtproject.**  {*;}
# Tablayout
-keep class com.google.android.material.tabs.TabLayout {*;}
-keep class com.google.android.material.tabs.TabLayout$* {*;}
# AppBarLayout
-keep class com.google.android.material.appbar.AppBarLayout {*;}
-keep class com.google.android.material.appbar.AppBarLayout$* {*;}


# jpush
-dontnote cn.jpush.**
-dontnote cn.jiguang.**

# mobile quick login
-dontwarn com.cmic.sso.sdk.**
-keep class com.cmic.sso.sdk.** {*;}
# telecom quick login
-keep class cn.com.chinatelecom.account.**{*;}
# unicom quick login
-dontwarn com.unicom.xiaowo.login.**
-keep class com.unicom.xiaowo.login.**{*;}

# bbmedia sdk
-keep class com.baidu.**{*;}
-keep interface com.baidu.**{*;}

-keep class com.sensetime.**{*;}
-keep interface com.sensetime.**{*;}


-keep class versa.recognize.**{*;}
-keep interface versa.recognize.**{*;}

-keep class **$Properties{*;}


#tcent location sdk
-keepattributes *Annotation*
-keepclassmembers class ** {
    public void on*Event(...);
}
-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn  org.eclipse.jdt.annotation.**
# tcent location sdk sdk版本小于18时需要以下配置, 建议使用18或以上版本的sdk编译
-dontwarn  android.location.Location
-dontwarn  android.net.wifi.WifiManager
-dontnote ct.**
-if class **$Properties{*;}
-keepnames class <1>


# 注解不混淆（解决新版r8 apply mapping注解失效）
-keepnames class * implements java.lang.annotation.Annotation { *; }
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
     public static void check*(...);
}

-keepclassmembers class ** implements androidx.viewbinding.ViewBinding {
    public static *** bind(***);
    public static *** inflate(...);
}

-keep class com.mammon.audiosdk.**{*;}


### Aurora sdk
-keep class androidx.exifinterface.media.* { *;}
-keep class org.libpag.* { *;}

-keep class com.hippo.quickjs.android.**{*;}
-keep interface com.hippo.quickjs.android.**{*;}

-keep class org.msgpack.** {*;}
-keep class com.google.protobuf.** { *; }


-keep class org.webrtc.** {*;}