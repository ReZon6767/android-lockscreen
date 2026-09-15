# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html

-dontusemixedcaseclassnames
-verbose

# Preserve line numbers for debugging stack traces.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep all public classes, methods, and fields.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep class androidx.** { *; }
-keep class com.google.** { *; }
