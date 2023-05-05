package com.kusitms.hotsixServer.domain.user.exception;

import com.kusitms.hotsixServer.domain.user.constant.UserConstants;

public class TokenErrorException extends UserException {
    public TokenErrorException() {
        super(UserConstants.UserExceptionList.TOKEN_ERROR.getErrorCode(),
                UserConstants.UserExceptionList.TOKEN_ERROR.getHttpStatus(),
                UserConstants.UserExceptionList.TOKEN_ERROR.getMessage());
    }
}
