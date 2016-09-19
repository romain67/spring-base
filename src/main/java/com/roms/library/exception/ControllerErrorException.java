package com.roms.library.exception;

public class ControllerErrorException extends RuntimeException {

    private static final long serialVersionUID = 2949088781177641754L;

    private String messageKey;
    private Object[] messageArgs;
    private String errorCode;

    public ControllerErrorException(String message, String messageKey, Object[] messageArgs, String errorCode) {
        super(message);
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.errorCode = errorCode;
    }

    public ControllerErrorException(String message, String messageKey, String errorCode) {
        super(message);
        this.messageKey = messageKey;
        this.errorCode = errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

}
