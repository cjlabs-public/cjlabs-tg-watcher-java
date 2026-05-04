package com.cjlabs.tgwatcher.business.tg.service;

import com.cjlabs.db.domain.FmkPageResponse;
import com.cjlabs.db.domain.FmkRequest;
import com.cjlabs.tgwatcher.business.tg.convertReq.TgGroupAccountMessageReqConvert;
import com.cjlabs.tgwatcher.business.tg.mysql.TgGroupAccountMessage;
import com.cjlabs.tgwatcher.business.tg.reqquery.TgGroupAccountMessageReqQuery;
import com.cjlabs.tgwatcher.business.tg.reqsave.TgGroupAccountMessageReqSave;
import com.cjlabs.tgwatcher.business.tg.requpdate.TgGroupAccountMessageReqUpdate;
import com.cjlabs.tgwatcher.business.tg.wrapmapper.TgGroupAccountMessageWrapMapper;
import com.cjlabs.web.check.FmkCheckUtil;
import com.cjlabs.domain.exception.Error200ExceptionEnum;
import com.cjlabs.web.json.FmkJacksonUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * tg_group_account_message TG群消息解析账号记录
 *
 * 2026-05-04 23:35:56
 */
@Slf4j
@Service
public class TgGroupAccountMessageService {
    
    @Autowired
    private TgGroupAccountMessageWrapMapper tgGroupAccountMessageWrapMapper;
	
    public TgGroupAccountMessage getById(FmkRequest<Void> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(StringUtils.isBlank(input.getBusinessKey()));

        String id = input.getBusinessKey();
        return tgGroupAccountMessageWrapMapper.getById(id);
    }

    public TgGroupAccountMessage save(TgGroupAccountMessageReqSave request) {
        FmkCheckUtil.checkInput(Objects.isNull(request));
        
        TgGroupAccountMessage db = TgGroupAccountMessageReqConvert.toDb(request);

        int saved = tgGroupAccountMessageWrapMapper.save(db);
        FmkCheckUtil.throw200Error(saved == 0, Error200ExceptionEnum.DATA_NOT_FOUND);
        return db;
    }


    public boolean update(TgGroupAccountMessageReqUpdate request) {
        FmkCheckUtil.checkInput(Objects.isNull(request));
        
        TgGroupAccountMessage db = TgGroupAccountMessageReqConvert.toDb(request);
        
        int updated = tgGroupAccountMessageWrapMapper.updateById(db);
        if (updated > 0) {
            log.info("TgGroupAccountMessageService|update|update={}|id={}", updated, request.getId());
            return true;
        }
        return false;
    }

    public boolean deleteById(String businessKey) {
        // 参数校验
        FmkCheckUtil.checkInput(StringUtils.isBlank(businessKey));
        
        int deleted = tgGroupAccountMessageWrapMapper.deleteById(businessKey);
        if (deleted > 0) {
            log.info("TgGroupAccountMessageService|deleteById|deleteById={}|id={}", deleted, businessKey);
            return true;
        }
        return false;
    }

    /**
     * 查询所有（不分页）
     */
    public List<TgGroupAccountMessage> listAll() {
        List<TgGroupAccountMessage> entityList = tgGroupAccountMessageWrapMapper.listAllLimitService();
        return entityList;
    }
    
      /**
     * 分页查询
     */
    public FmkPageResponse<TgGroupAccountMessage> pageQuery(FmkRequest<TgGroupAccountMessageReqQuery> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getRequest()));

        // 执行分页查询
        FmkPageResponse<TgGroupAccountMessage> entityPage = tgGroupAccountMessageWrapMapper.pageQuery(input);

        return entityPage;
    }
}