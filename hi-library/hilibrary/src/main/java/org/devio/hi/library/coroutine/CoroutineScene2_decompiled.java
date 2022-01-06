package org.devio.hi.library.coroutine;

import android.util.Log;

import androidx.annotation.NonNull;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

public class CoroutineScene2_decompiled {
    private static final String TAG = "CoroutineScene2";
    public static final Object request1(Continuation preCallback) {
        ContinuationImpl request1Callback;
        if(!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl)preCallback).label & Integer.MIN_VALUE) == 0) {
            request1Callback = new ContinuationImpl(preCallback) {
                @Override
                Object invokeSuspend(@NonNull Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    Log.e(TAG, "request1 has resumed");
                    return request1(this);
                }
            };
        }else {
            request1Callback = (ContinuationImpl) preCallback;
        }

        switch(request1Callback.label) {
            case 0: {
//                Object delay = DelayKt.delay(2000, request1Callback);
                Object result2 = request2(request1Callback);
                if(result2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    Log.e(TAG, "request1 has suspended");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }

        Log.e(TAG, "request1 completed");
        return "result from request1 " + request1Callback.result;

    }

    public static final Object request2(Continuation preCallback) {
        ContinuationImpl request2Callback;
        if(!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl)preCallback).label & Integer.MIN_VALUE) == 0) {
            request2Callback = new ContinuationImpl(preCallback) {
                @Override
                Object invokeSuspend(@NonNull Object resumeResult) {
                    this.result = resumeResult;
                    this.label |= Integer.MIN_VALUE;
                    Log.e(TAG, "request2 has resumed");
                    return request2(this);
                }
            };
        }else {
            request2Callback = (ContinuationImpl) preCallback;
        }

        switch(request2Callback.label) {
            case 0: {
                Object delay = DelayKt.delay(2000, request2Callback);
                if(delay== IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    Log.e(TAG, "request2 has suspended");
                    return IntrinsicsKt.getCOROUTINE_SUSPENDED();
                }
            }
        }

        Log.e(TAG, "request2 completed");
        return "result from request2";

    }


    static abstract class ContinuationImpl<T> implements Continuation<T> {

        private Continuation preCallback;
        int label;
        Object result;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NonNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        @Override
        public void resumeWith(@NonNull Object resumeResult) {
            Object suspend = invokeSuspend(resumeResult);
            if(suspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            preCallback.resumeWith(suspend);
        }

        abstract Object invokeSuspend(@NonNull Object resumeResult);
    }
}
