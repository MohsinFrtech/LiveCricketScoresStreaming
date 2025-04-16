# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
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
-dontshrink
-dontoptimize
-ignorewarnings

-keep class  com.traumsportzone.live.cricket.tv.scores.streaming.models.** { *; }
-keep class  com.traumsportzone.live.cricket.tv.scores.score.model.** { *; }

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-verbose

-keepattributes Annotation,Signature,EnclosingMethod
-dontwarn kotlin.Unit

-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

-keepclassmembers class com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants {
public static final *;
}

-keepclassmembers class com.traumsportzone.live.cricket.tv.scores.streaming.utils.objects.Constants {
final *;
}

-keepclassmembers class com.traumsportzone.live.cricket.tv.scores.score.utility.Cons {
public static final *;
}

-keepclassmembers class com.traumsportzone.live.cricket.tv.scores.score.utility.Cons {
final *;
}

#-dontwarn com.p2pengine.**
#-keep class com.p2pengine.**{*;}
#-keep interface com.p2pengine.**{*;}
#-keep class com.cdnbye.libdc.**{*;}
#-keep interface com.cdnbye.libdc.**{*;}
#-keep class com.snapchat.djinni.**{*;}