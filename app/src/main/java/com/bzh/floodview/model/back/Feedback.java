package com.bzh.floodview.model.back;
/**
 * 此实体类是添加意见反馈的实体的返回结果
 * 返回添加是否添加成功
 *
 */
public class Feedback {

    boolean submission;

    public boolean getSubmission() {
        return submission;
    }

    public void setSubmission(boolean submission) {
        this.submission = submission;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "submission=" + submission +
                '}';
    }
}
