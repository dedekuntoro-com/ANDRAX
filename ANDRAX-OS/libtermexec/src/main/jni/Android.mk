LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := libandrax-termexec
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \
	-lc \

LOCAL_SRC_FILES := \
	/home/mythical/AndroidStudioProjects/ANDRAX-OS/libtermexec/src/main/jni/process.cpp \

LOCAL_C_INCLUDES += /home/mythical/AndroidStudioProjects/ANDRAX-OS/libtermexec/src/main/jni
LOCAL_C_INCLUDES += /home/mythical/AndroidStudioProjects/ANDRAX-OS/libtermexec/src/release/jni

include $(BUILD_SHARED_LIBRARY)
