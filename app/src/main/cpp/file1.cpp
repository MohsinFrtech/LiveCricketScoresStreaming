//
// Created by FrTech on 21-Jan-23.
//
#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_tv_activities_TvMainActivity_getStringArray1(
        JNIEnv *env, jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(19, env->FindClass("java/lang/String"), nullptr);
    std::string vs1 = "I95JjzV16YHotJQLTw0kMD5QxKpQ5YlkQyRCyPTb";
    std::string KDa = "wnls9qbnlT4jEp2lIlLfVjtCs9PenXl7s8bL83Qy";
    std::string Ks5 = "fOCQIlptLdlK1Uv6BzhRdTzJa5wqy0FEE7lSGViu";
    std::string Sdj = "URPnB5nYjWGEmp5tJf6pZ3YOYfnGBj0kJlzmB5Bx";
    std::string N71 = "LS6ApW9BU4s7Bq8fJ45XYxsQiyR2ZsimFIZznnba";
    std::string FPT = "i1643BjkWwx4kKA6NNgvbLPQVsHb9s5rPGZa8Ix1";
    std::string unQ = "NfkacH8jLJMzg7W0UQp7lPYtMrRwwjmdBKJsoXvH";
    std::string y5 = "uC1RMH0CoAMlCb2szChxpShwwzZLtk4unfzyNMja";
    std::string ikG = "3jsUFNSn5SMzg7W0UQp7KB84Ff0yC3mz2pLzsntg";
    std::string GV9 = "rsnI9vvm105AYDAYnynT5ip8OvqV8qlS1xQ4RZ5E";
//    std::string t1 = "c52bbd1b7502ed60ed06d78b8b7581546a4d816b";

    std::string t1 = "201d81c0fa63460d68ef63fee05c3f303fb8ff86";
//    std::string t2 = "https://score-streaming.nospacedidlove.com/api/applications/";

    std::string t2 = "https://test.nospacedidlove.com/api/applications/";

    std::string t3 = "Asgjkkjhgfdswertyuimnbvcxsdfghj";
    std::string t4 = "154140573615627969442741";
    std::string t5 = "https://nodeapi.streamingsoultions.com/";
    std::string t6 = "https://cricket-api.nospacedidlove.com/api/v1/";
    std::string t7Scores = "1h44!6t2aQr?TGbU=kaN6N3W=4Ncw0eS=obuK8q4jiLQ-U8cHERFf6Ol06G2cKYx";
    std::string t8SocketUrl = "ws://cricket-data.nospacedidlove.com/live_score";
    std::string t9SocketMessage =  "{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"LiveScoreSocketChannel\\\"}\"}";

    env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(vs1.c_str()));
    env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(KDa.c_str()));
    env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(Ks5.c_str()));
    env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(Sdj.c_str()));
    env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(N71.c_str()));
    env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(FPT.c_str()));
    env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(unQ.c_str()));
    env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(y5.c_str()));
    env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(ikG.c_str()));
    env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(GV9.c_str()));
    env->SetObjectArrayElement(strarr, 10, env->NewStringUTF(t1.c_str()));
    env->SetObjectArrayElement(strarr, 11, env->NewStringUTF(t2.c_str()));
    env->SetObjectArrayElement(strarr, 12, env->NewStringUTF(t3.c_str()));
    env->SetObjectArrayElement(strarr, 13, env->NewStringUTF(t4.c_str()));
    env->SetObjectArrayElement(strarr, 14, env->NewStringUTF(t5.c_str()));
    env->SetObjectArrayElement(strarr, 15, env->NewStringUTF(t6.c_str()));
    env->SetObjectArrayElement(strarr, 16, env->NewStringUTF(t7Scores.c_str()));
    env->SetObjectArrayElement(strarr, 17, env->NewStringUTF(t8SocketUrl.c_str()));
    env->SetObjectArrayElement(strarr, 18, env->NewStringUTF(t9SocketMessage.c_str()));

    return strarr;

}
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_streaming_ui_activities_HomeScreen_getStringArray1(
        JNIEnv *env, jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(19, env->FindClass("java/lang/String"), nullptr);
    std::string vs1 = "I95JjzV16YHotJQLTw0kMD5QxKpQ5YlkQyRCyPTb";
    std::string KDa = "wnls9qbnlT4jEp2lIlLfVjtCs9PenXl7s8bL83Qy";
    std::string Ks5 = "fOCQIlptLdlK1Uv6BzhRdTzJa5wqy0FEE7lSGViu";
    std::string Sdj = "URPnB5nYjWGEmp5tJf6pZ3YOYfnGBj0kJlzmB5Bx";
    std::string N71 = "LS6ApW9BU4s7Bq8fJ45XYxsQiyR2ZsimFIZznnba";
    std::string FPT = "i1643BjkWwx4kKA6NNgvbLPQVsHb9s5rPGZa8Ix1";
    std::string unQ = "NfkacH8jLJMzg7W0UQp7lPYtMrRwwjmdBKJsoXvH";
    std::string y5 = "uC1RMH0CoAMlCb2szChxpShwwzZLtk4unfzyNMja";
    std::string ikG = "3jsUFNSn5SMzg7W0UQp7KB84Ff0yC3mz2pLzsntg";
    std::string GV9 = "rsnI9vvm105AYDAYnynT5ip8OvqV8qlS1xQ4RZ5E";
//    std::string t1 = "c52bbd1b7502ed60ed06d78b8b7581546a4d816b";
    std::string t1 = "201d81c0fa63460d68ef63fee05c3f303fb8ff86";
//    std::string t2 = "https://score-streaming.nospacedidlove.com/api/applications/";
    std::string t2 = "https://test.nospacedidlove.com/api/applications/";

    std::string t3 = "Asgjkkjhgfdswertyuimnbvcxsdfghj";
    std::string t4 = "154140573615627969442741";
    std::string t5 = "https://nodeapi.streamingsoultions.com/";
    std::string t6 = "https://cricket-api.nospacedidlove.com/api/v1/";
    std::string t7Scores = "1h44!6t2aQr?TGbU=kaN6N3W=4Ncw0eS=obuK8q4jiLQ-U8cHERFf6Ol06G2cKYx";
    std::string t8SocketUrl = "ws://cricket-data.nospacedidlove.com/live_score";
    std::string t9SocketMessage =  "{\"command\":\"subscribe\",\"identifier\":\"{\\\"channel\\\":\\\"LiveScoreSocketChannel\\\"}\"}";

    env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(vs1.c_str()));
    env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(KDa.c_str()));
    env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(Ks5.c_str()));
    env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(Sdj.c_str()));
    env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(N71.c_str()));
    env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(FPT.c_str()));
    env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(unQ.c_str()));
    env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(y5.c_str()));
    env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(ikG.c_str()));
    env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(GV9.c_str()));
    env->SetObjectArrayElement(strarr, 10, env->NewStringUTF(t1.c_str()));
    env->SetObjectArrayElement(strarr, 11, env->NewStringUTF(t2.c_str()));
    env->SetObjectArrayElement(strarr, 12, env->NewStringUTF(t3.c_str()));
    env->SetObjectArrayElement(strarr, 13, env->NewStringUTF(t4.c_str()));
    env->SetObjectArrayElement(strarr, 14, env->NewStringUTF(t5.c_str()));
    env->SetObjectArrayElement(strarr, 15, env->NewStringUTF(t6.c_str()));
    env->SetObjectArrayElement(strarr, 16, env->NewStringUTF(t7Scores.c_str()));
    env->SetObjectArrayElement(strarr, 17, env->NewStringUTF(t8SocketUrl.c_str()));
    env->SetObjectArrayElement(strarr, 18, env->NewStringUTF(t9SocketMessage.c_str()));

    return strarr;

}