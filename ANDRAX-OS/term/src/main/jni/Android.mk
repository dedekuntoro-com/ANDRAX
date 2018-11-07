LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := libandrax-axterminal
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_LDLIBS := \
	-llog \

LOCAL_SRC_FILES := \
	/home/mythical/AndroidStudioProjects/ANDRAX-OS/term/src/main/jni/common.cpp \
	/home/mythical/AndroidStudioProjects/ANDRAX-OS/term/src/main/jni/fileCompat.cpp \
	/home/mythical/AndroidStudioProjects/ANDRAX-OS/term/src/main/jni/termExec.cpp \

LOCAL_C_INCLUDES += /home/mythical/AndroidStudioProjects/ANDRAX-OS/term/src/main/jni
LOCAL_C_INCLUDES += /home/mythical/AndroidStudioProjects/ANDRAX-OS/term/src/release/jni

include $(BUILD_SHARED_LIBRARY)
