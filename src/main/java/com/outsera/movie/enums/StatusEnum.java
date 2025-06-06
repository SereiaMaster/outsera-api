package com.outsera.movie.enums;

public enum StatusEnum {

    ACTIVE(1), INACTIVE(0), DELETED(2);

    public int status;
    StatusEnum(int status) {
        status = status;
    }

    public int getStatus(){
        return status;
    }

}
