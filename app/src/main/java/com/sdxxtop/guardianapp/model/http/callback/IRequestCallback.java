package com.sdxxtop.guardianapp.model.http.callback;

public interface IRequestCallback<T> {
    void onSuccess(T t);
    void onFailure(int code, String error);
}
