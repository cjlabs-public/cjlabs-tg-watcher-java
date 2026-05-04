package com.cjlabs.tgwatcher.business.tg.service;

import com.cjlabs.db.domain.FmkPageResponse;
import com.cjlabs.db.domain.FmkRequest;
import com.cjlabs.tgwatcher.business.tg.convert.TgGroupAccountMessageConvert;
import com.cjlabs.tgwatcher.business.tg.mysql.TgGroupAccountMessage;
import com.cjlabs.tgwatcher.business.tg.reqquery.TgGroupAccountMessageReqQuery;
import com.cjlabs.tgwatcher.business.tg.reqsave.TgGroupAccountMessageReqSave;
import com.cjlabs.tgwatcher.business.tg.requpdate.TgGroupAccountMessageReqUpdate;
import com.cjlabs.tgwatcher.business.tg.resp.TgGroupAccountMessageResp;
import com.cjlabs.web.check.FmkCheckUtil;
import com.cjlabs.web.json.FmkJacksonUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
public class TgGroupAccountMessageApiService {
    
    @Autowired
    private TgGroupAccountMessageService tgGroupAccountMessageService;
	
    public TgGroupAccountMessageResp getById(FmkRequest<Void> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(StringUtils.isBlank(input.getBusinessKey()));

        TgGroupAccountMessage tgGroupAccountMessage = tgGroupAccountMessageService.getById(input);
        return TgGroupAccountMessageConvert.toResp(tgGroupAccountMessage);
    }

    public TgGroupAccountMessageResp save(FmkRequest<TgGroupAccountMessageReqSave> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getRequest()));

        TgGroupAccountMessageReqSave request = input.getRequest();
        if (Objects.isNull(request)) {
            log.info("TgGroupAccountMessageApiService|save|request is null");
            return null;
        }

        TgGroupAccountMessage tgGroupAccountMessage = tgGroupAccountMessageService.save(request);
        return TgGroupAccountMessageConvert.toResp(tgGroupAccountMessage);
    }


    public boolean update(FmkRequest<TgGroupAccountMessageReqUpdate> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getRequest()));

        TgGroupAccountMessageReqUpdate request = input.getRequest();
        if (Objects.isNull(request)) {
            log.info("TgGroupAccountMessageApiService|update|request is null");
            return false;
        }
        return tgGroupAccountMessageService.update(request);
    }

    public boolean deleteById(FmkRequest<Void> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getBusinessKey()));

        String businessKey = input.getBusinessKey();
        if (businessKey == null) {
        	log.info("TgGroupAccountMessageApiService|deleteById|request is null");
            return false;
        }
        return tgGroupAccountMessageService.deleteById(businessKey);
    }

    /**
     * 查询所有（不分页）
     */
    public List<TgGroupAccountMessageResp> listAll() {
        List<TgGroupAccountMessage> entityList = tgGroupAccountMessageService.listAll();
        List<TgGroupAccountMessageResp> respList = TgGroupAccountMessageConvert.toResp(entityList);
        return respList;
    }
    
      /**
     * 分页查询
     */
    public FmkPageResponse<TgGroupAccountMessageResp> pageQuery(FmkRequest<TgGroupAccountMessageReqQuery> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getRequest()));

        // 执行分页查询
        FmkPageResponse<TgGroupAccountMessage> entityPage = tgGroupAccountMessageService.pageQuery(input);

        if (Objects.isNull(entityPage) || CollectionUtils.isEmpty(entityPage.getRecords())) {
            return FmkPageResponse.empty();
        }

        FmkPageResponse<TgGroupAccountMessageResp> pageResponse = FmkPageResponse.of(entityPage, TgGroupAccountMessageConvert::toResp);

        return pageResponse;
    }
}