package com.ns.common.util.exception.errorcode;

/**
 * Created by xuezhucao on 2017/6/7.
 */
public interface CommonErrorCode {
    ErrorCode DATABASE_OPERATION_EXCEPTION = new ErrorCode(1, "数据库操作失败");
    ErrorCode PARAMETER_EXCEPTION = new ErrorCode(2, "参数错误: %s");
    ErrorCode SYSTEM_INTERNAL_EXCEPTION = new ErrorCode(3, "系统内部错误");
    ErrorCode INVALID_TOKEN = new ErrorCode(4, "无效的token");
    ErrorCode HTTP_EXCEPTION = new ErrorCode(5, "http异常");
    ErrorCode CREATE_TOKEN_FAIL = new ErrorCode(6, "创建token失败");
    ErrorCode GET_PARAM_FAIL = new ErrorCode(7, "获取参数失败");
    ErrorCode UNKOWN_EXCEPTION = new ErrorCode(8, "未知异常");
    ErrorCode OPERATIOIN_FAIL = new ErrorCode(9, "操作失败");
    ErrorCode FEIGN_RESPONSE_FAIL = new ErrorCode(10, "%s系统获取不到数据");
    ErrorCode ROLE_PATTERN_EXCEPTION = new ErrorCode(11, "%s规则文件格式不正确, 实际为%s, 应为%s");
    ErrorCode COMPOENT_STATUS_ERROR = new ErrorCode(12, "%s状态不正常, 状态为: %s");
}
