# Gson - keep model classes used in JSON parsing
-keepclassmembers class com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.** {
    <fields>;
    <methods>;
}

# Keep all inner classes of ApiResponse (like Data, Section)
-keep class com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ApiResponse$* {
    <fields>;
    <methods>;
}

# Keep ButtonItem (used in lists)
-keep class com.wRaptureReadyEndTimesNewsProphecyDoctrineofPreTribRapture.data.ButtonItem {
    <fields>;
    <methods>;
}

# General rule for Gson: prevent stripping of classes with no-arg constructors
-keep class * {
    public <init>();
}

# (Optional but safe) Keep anything used by Gson
-keepattributes Signature
-keepattributes *Annotation*
