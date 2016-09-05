package com.roms.library.exception;

public class FormErrorException extends Exception {

    private static final long serialVersionUID = -8953242841606759797L;

    /**
     * Should be a code use to make a translation key
     */
    String messageCode;

    public FormErrorException(String message, String code) {
        super(message);
        this.messageCode = code;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

}
