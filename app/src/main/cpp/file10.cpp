#include <jni.h>
#include <string>

//
// Created by FrTech on 21-Jan-23.
//


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_streaming_ui_activities_HomeScreen_getStringArray10(JNIEnv *env,
                                                                                   jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(10, env->FindClass("java/lang/String"), nullptr);
    std::string kDL = "MDFyIfEx5TQi6TNFAZ%qi#G6IBvbjQ1GF7VX@n07";
    env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(kDL.c_str()));
    std::string lZC = "4qGDQDDnIgKindhVps8lsrhKBaKUIN5Y8Zl3cTGm";
    env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(lZC.c_str()));
    std::string B08 = "5Sx$hoO0ncL^a@nFa5szQ03kVsbFcovKh#RLBmA@";
    env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(B08.c_str()));
    std::string JQ = "F58vtUZ3kWRWAmxBU0Bsc7XKUZtKNDbxTmpLefga";
    env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(JQ.c_str()));
    std::string M0U = "%AgNHLRqN%80elfCumtRR&beS^qoDvsL%QdIevi&";
    env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(M0U.c_str()));
    std::string IJM = "QDWGir^YuM@WkpbOP$YjP^sPDGaWX4GA5CasTrqu";
    env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(IJM.c_str()));
    std::string wQ3 = "6wAae3QJ@9rWmvIeGr07w&AXEMpZDsXPJh2swX4&";
    env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(wQ3.c_str()));
    std::string BvB = "toqaaNzZxxXiknFJEXldszrLp0i4SdUdFgV08g54";
    env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(BvB.c_str()));
    std::string YnO = "Z$L&h3xYQtrKGAv1^m&zKvlVcFcdsCQ$j1wISHjq";
    env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(YnO.c_str()));
    std::string n4R = "SofHXh4N@tq5Be!gItBs06$0^HU6VT^g0Qj0vsgU";
    env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(n4R.c_str()));
    return strarr;
}
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_tv_activities_TvMainActivity_getStringArray10(JNIEnv *env,
                                                                              jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(10, env->FindClass("java/lang/String"), nullptr);
    std::string kDL = "MDFyIfEx5TQi6TNFAZ%qi#G6IBvbjQ1GF7VX@n07";
    env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(kDL.c_str()));
    std::string lZC = "4qGDQDDnIgKindhVps8lsrhKBaKUIN5Y8Zl3cTGm";
    env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(lZC.c_str()));
    std::string B08 = "5Sx$hoO0ncL^a@nFa5szQ03kVsbFcovKh#RLBmA@";
    env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(B08.c_str()));
    std::string JQ = "F58vtUZ3kWRWAmxBU0Bsc7XKUZtKNDbxTmpLefga";
    env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(JQ.c_str()));
    std::string M0U = "%AgNHLRqN%80elfCumtRR&beS^qoDvsL%QdIevi&";
    env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(M0U.c_str()));
    std::string IJM = "QDWGir^YuM@WkpbOP$YjP^sPDGaWX4GA5CasTrqu";
    env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(IJM.c_str()));
    std::string wQ3 = "6wAae3QJ@9rWmvIeGr07w&AXEMpZDsXPJh2swX4&";
    env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(wQ3.c_str()));
    std::string BvB = "toqaaNzZxxXiknFJEXldszrLp0i4SdUdFgV08g54";
    env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(BvB.c_str()));
    std::string YnO = "Z$L&h3xYQtrKGAv1^m&zKvlVcFcdsCQ$j1wISHjq";
    env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(YnO.c_str()));
    std::string n4R = "SofHXh4N@tq5Be!gItBs06$0^HU6VT^g0Qj0vsgU";
    env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(n4R.c_str()));
    return strarr;
}