package com.android.internal.telephony;

public interface ITelephony {
    void answerRingingCall();

    boolean endCall();

    void silenceRinger();

    boolean showCallScreenWithDialpad(boolean showDialpad);
}

