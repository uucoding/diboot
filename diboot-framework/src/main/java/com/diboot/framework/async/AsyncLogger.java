package com.diboot.framework.async;

import com.diboot.framework.config.BaseConfig;
import com.diboot.framework.model.BaseModel;
import com.diboot.framework.model.BaseUser;
import com.diboot.framework.model.OperationLog;
import com.diboot.framework.model.TraceLog;
import com.diboot.framework.service.OperationLogService;
import com.diboot.framework.service.TraceLogService;
import com.diboot.framework.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Set;

/***
 * 异步日志操作类
 * 注意: 异步操作方法不能有返回类型，一律定义为void
 * @author Mazc@dibo.ltd
 * @version 2017年4月5日
 * Copyright @ www.dibo.ltd
 */
@Async
@Component
public class AsyncLogger {
	private static final Logger logger = LoggerFactory.getLogger(AsyncLogger.class);

	@Autowired
	private OperationLogService operationLogService;

    @Autowired
    private TraceLogService traceLogService;

    /***
     * 是否启用追踪日志
     */
    public static final boolean isEnabledTraceLog = BaseConfig.isTrue("diboot.log.trace-enabled");

    /***
	 * 保存用户操作日志
	 * @param user
	 * @param operation
	 * @param model
	 * @param comment
	 */
	public void saveOperationLog(BaseUser user, OperationLog.OPERATION operation, BaseModel model, String...comment){
        OperationLog operationLog = new OperationLog();
        buildUserInfo(operationLog, user);
        operationLog.setOperation(operation.name());
        operationLog.setRelObjId(String.valueOf(model.getPk()));
        operationLog.setRelObjType(model.getClass().getSimpleName());
        if(comment != null && comment.length > 0){
            operationLog.setComment(comment[0]);
        }
        else{
            // 自动设置备注信息
            String username = user != null? user.getRealname() : "";
            operationLog.setComment(username +" "+ OperationLog.OPERATION.getLabel(operation.name()) + "了 编号为 #"+model.getPk() + " 的 " + model.getModelName());
        }
        operationLog.setRelObjData(null);
        // 将记录保存进数据库
		try {
            operationLogService.createModel(operationLog);
		}
		catch (Exception e) {
			logger.error("保存操作日志错误", e);
		}
	}

    /***
     * 保存错误日志
     * @param model
     */
    public void saveErrorLog(BaseModel model, String...comment){
        if(!isEnabledTraceLog){
            return;
        }
        try{
            BaseUser user = BaseHelper.getCurrentUser();
            String newComment = V.notEmpty(comment)? comment[0] : "用户操作发生错误！";
            saveTraceLog(user, TraceLog.OPERATION.ERROR, model, null, null, "ERROR:"+newComment);
        }
        catch (Exception e){
            logger.warn("保存用户操作异常日志出错:" + e.getMessage());
        }
    }

    /***
     * 保存错误日志
     * @param user
     * @param model
     */
	public void saveErrorLog(BaseUser user, BaseModel model, String...comment){
        if(!isEnabledTraceLog){
            return;
        }
        String newComment = V.notEmpty(comment)? comment[0] : "用户操作发生错误！";
        saveTraceLog(user, TraceLog.OPERATION.ERROR, model, null, null, "ERROR:"+newComment);
    }

    /***
     * 保存请求tracelog
     * @param user
     * @param requestMethod
     * @param requestUrl
     * @param requestParams
     */
    public void saveRequestTraceLog(BaseUser user, String requestMethod, String requestUrl, String requestParams, String...comment){
        if(!isEnabledTraceLog){
            return;
        }
        TraceLog traceLog = new TraceLog();
        buildUserInfo(traceLog, user);
        traceLog.setOperation(TraceLog.OPERATION.REQUEST.name());
        traceLog.setRequestUrl(requestUrl);
        traceLog.setRelObjType(requestMethod);
        traceLog.setRelObjId(null);
        traceLog.setRelObjData(S.cut(requestParams, 1000));

        if(V.notEmpty(comment)){
            traceLog.setComment(comment[0]);
        }
        // 将记录保存进数据库
        try {
            traceLogService.createModel(traceLog);
        }
        catch (Exception e) {
            logger.error("保存跟踪日志错误", e);
        }
    }

    /***
     * 保存更新相关的操作日志
     * @param operation
     * @param newModel
     * @param oldModel
     */
    public void saveTraceLog(BaseUser user, TraceLog.OPERATION operation, BaseModel newModel, BaseModel oldModel, Set<String> fields, String...comment){
        if(!isEnabledTraceLog){
            return;
        }
        TraceLog traceLog = new TraceLog();
        buildUserInfo(traceLog, user);
        traceLog.setOperation(operation.name());
        if(newModel != null){
            traceLog.setRelObjId(String.valueOf(newModel.getPk()));
            traceLog.setRelObjType(newModel.getClass().getSimpleName());
            if(V.notEmpty(comment)){
                traceLog.setComment(comment[0]);
            }
            if(oldModel != null){
                String changes = BeanUtils.extractDiff(oldModel, newModel, fields);
                traceLog.setRelObjData(changes);
            }
            else{
                traceLog.setRelObjData(JSON.toJSONString(newModel));
            }
        }
        else{
            if(V.notEmpty(comment)){
                traceLog.setRelObjType(Exception.class.getSimpleName());
                traceLog.setRelObjData(comment[0]);
                traceLog.setComment("系统发生异常!");
            }
        }
        // 将记录保存进数据库
        try {
            traceLogService.createModel(traceLog);
        }
        catch (Exception e) {
            logger.error("保存跟踪日志错误", e);
        }
    }

    /***
     * 附加当前登录用户信息
     * @param logModel
     * @param user
     */
    private void buildUserInfo(OperationLog logModel, BaseUser user){
        if(user != null){
            logModel.setUserType(user.getClass().getSimpleName());
            logModel.setUserId(user.getId());
            logModel.setCreatorName(user.getRealname());
        }
        else{
            logModel.setUserType(BaseUser.class.getSimpleName());
            logModel.setUserId(0L);
        }
    }
}