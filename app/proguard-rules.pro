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
-keep class androidx.compose.** { *; }

# Keep classes that use Compose's @Composable annotation
-keep @androidx.compose.runtime.Composable class * {
    <methods>;
}

# Keep ViewModel classes to prevent issues with saved state
-keep class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

-keep class com.example.footyfaces.data.model.** { *;}
-keep class com.example.footyfaces.data.mapper.** { *;}
-keep class com.example.footyfaces.data.remote.** { *;}
-keep class com.example.footyfaces.data.repository.** { *;}
-keep class com.example.footyfaces.data.** { *;}

-keep class com.example.footyfaces.domain.repository.** { *;}
-keep class com.example.footyfaces.domain.model.** { *;}
-keep class com.example.footyfaces.domain.usecases.** { *;}

-keep class com.example.footyfaces.framework.connectivity.** { *;}
-keep class com.example.footyfaces.framework.di.** { *;}

-keep class com.example.footyfaces.presentation.intent.** { *;}
-keep class com.example.footyfaces.presentation.components.** { *;}
-keep class com.example.footyfaces.presentation.ui.** { *;}
-keep class com.example.footyfaces.presentation.viewmodel.** { *;}

-keep class com.example.footyfaces.MainActivity {
    public <fields>;
    public <methods>;
}

-keep class com.example.footyfaces.data.model.**{
 private <fields>;
}

-keep class com.example.footyfaces.domain.model.**{
 private <fields>;
}