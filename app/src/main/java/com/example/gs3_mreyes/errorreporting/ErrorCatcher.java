package com.example.gs3_mreyes.errorreporting;

import com.example.gs3_mreyes.errorreporting.task.SendStringToServerViaHttpTask;

/**
 * Created by GS3-MREYES on 9/14/2015.
 */
public class ErrorCatcher {

    private String errorMessage;

    public ErrorCatcher(){
        this.errorMessage = "";
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void sendErrorToServer(String errorMessage){
        SendStringToServerViaHttpTask sendStringToServerViaHttpTask = new SendStringToServerViaHttpTask(errorMessage);
        sendStringToServerViaHttpTask.execute();
    }
}
