#include <jni.h>
#include <string>

//
// Created by FrTech on 21-Jan-23.
//


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_streaming_ui_activities_HomeScreen_getStringArray6(JNIEnv *env,jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(10, env->FindClass("java/lang/String"), nullptr);
    std::string ja = "g&FNQ9aD5VA#!XFswuSk^!gGESUQADFOCO&v89YN";	env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(ja.c_str()));
    std::string l9o = "LzJcxwnjKxBr4Z3SY&NlwnEqv@IBSzfzEnwvhjPf";	env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(l9o.c_str()));
    std::string KI = "QifxpIGaAa^PgVWk4@tHg%S%G4$UxIDLGbaPaEPS";	env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(KI.c_str()));
    std::string ubc = "BbiWrTeDcsfFhyGvl6Pyc6GxU03VhTgFBds@0i^2";	env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(ubc.c_str()));
    std::string uL = "3D4e13YXtu5Bc9HLpBOHNUInDld6Vk7K0DqGo5aM";	env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(uL.c_str()));
    std::string HHe = "L$i6axw2Y%Ho%ggT#uBx6&!0H2Nf57$9J5AjW3&c";	env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(HHe.c_str()));
    std::string LNZ= "L$i6axw2Y%Ho%ggT#uBx6&!0H2Nf57$9J5AjW3&c";	env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(LNZ.c_str()));
    std::string zYK= "PRo^bHEsrx0KIuCofE&r^bTvZYgC7v9zBgOMAmo9";	env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(zYK.c_str()));
    std::string qLQ = "!^RMSa&&2e#xVL3d#Csm5eBoAnaccO5yJkyAB8w0";	env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(qLQ.c_str()));
    std::string GIS = "iKHEw9IocEZU0jS6raGQws8opAGWmGIIxC19cbmI";	env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(GIS.c_str()));

    return strarr;
}
extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_traumsportzone_live_cricket_tv_scores_tv_activities_TvMainActivity_getStringArray6(JNIEnv *env,
                                                                             jobject thiz) {
    jobjectArray strarr = env->NewObjectArray(10, env->FindClass("java/lang/String"), nullptr);
    std::string ja = "g&FNQ9aD5VA#!XFswuSk^!gGESUQADFOCO&v89YN";	env->SetObjectArrayElement(strarr, 0, env->NewStringUTF(ja.c_str()));
    std::string l9o = "LzJcxwnjKxBr4Z3SY&NlwnEqv@IBSzfzEnwvhjPf";	env->SetObjectArrayElement(strarr, 1, env->NewStringUTF(l9o.c_str()));
    std::string KI = "QifxpIGaAa^PgVWk4@tHg%S%G4$UxIDLGbaPaEPS";	env->SetObjectArrayElement(strarr, 2, env->NewStringUTF(KI.c_str()));
    std::string ubc = "BbiWrTeDcsfFhyGvl6Pyc6GxU03VhTgFBds@0i^2";	env->SetObjectArrayElement(strarr, 3, env->NewStringUTF(ubc.c_str()));
    std::string uL = "3D4e13YXtu5Bc9HLpBOHNUInDld6Vk7K0DqGo5aM";	env->SetObjectArrayElement(strarr, 4, env->NewStringUTF(uL.c_str()));
    std::string HHe = "L$i6axw2Y%Ho%ggT#uBx6&!0H2Nf57$9J5AjW3&c";	env->SetObjectArrayElement(strarr, 5, env->NewStringUTF(HHe.c_str()));
    std::string LNZ= "L$i6axw2Y%Ho%ggT#uBx6&!0H2Nf57$9J5AjW3&c";	env->SetObjectArrayElement(strarr, 6, env->NewStringUTF(LNZ.c_str()));
    std::string zYK= "PRo^bHEsrx0KIuCofE&r^bTvZYgC7v9zBgOMAmo9";	env->SetObjectArrayElement(strarr, 7, env->NewStringUTF(zYK.c_str()));
    std::string qLQ = "!^RMSa&&2e#xVL3d#Csm5eBoAnaccO5yJkyAB8w0";	env->SetObjectArrayElement(strarr, 8, env->NewStringUTF(qLQ.c_str()));
    std::string GIS = "iKHEw9IocEZU0jS6raGQws8opAGWmGIIxC19cbmI";	env->SetObjectArrayElement(strarr, 9, env->NewStringUTF(GIS.c_str()));

    return strarr;
}