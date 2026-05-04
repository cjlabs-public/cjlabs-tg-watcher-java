package com.cjlabs.tgwatcher.business.tg.wrapmapper;

import com.cjlabs.db.mp.FmkService;
import com.cjlabs.db.domain.FmkOrderItem;
import com.cjlabs.db.domain.FmkPageResponse;
import com.cjlabs.db.domain.FmkRequest;
import com.cjlabs.tgwatcher.business.tg.mapper.TgGroupAccountMessageMapper;
import com.cjlabs.tgwatcher.business.tg.mysql.TgGroupAccountMessage;
import com.cjlabs.tgwatcher.business.tg.reqquery.TgGroupAccountMessageReqQuery;
import com.cjlabs.web.check.FmkCheckUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
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
public class TgGroupAccountMessageWrapMapper extends FmkService<TgGroupAccountMessageMapper, TgGroupAccountMessage> {
    
    protected TgGroupAccountMessageWrapMapper(TgGroupAccountMessageMapper mapper) {
        super(mapper);
    }
    
    @Override
    protected Class<TgGroupAccountMessage> getEntityClass() {
        return TgGroupAccountMessage.class;
    }

 	/**
     * 分页查询
     */
    public FmkPageResponse<TgGroupAccountMessage> pageQuery(FmkRequest<TgGroupAccountMessageReqQuery> input) {
        // 参数校验
        FmkCheckUtil.checkInput(Objects.isNull(input));
        FmkCheckUtil.checkInput(Objects.isNull(input.getRequest()));

        // 构建分页对象
        Page<TgGroupAccountMessage> page = new Page<>(input.getCurrent(), input.getSize());
        TgGroupAccountMessageReqQuery request = input.getRequest();

        // 构建查询条件
        LambdaQueryWrapper<TgGroupAccountMessage> lambdaQuery = buildLambdaQuery();


        List<FmkOrderItem> orderItemList = input.getOrderItemList();

        // 执行分页查询
        IPage<TgGroupAccountMessage> dbPage = super.pageByCondition(page, lambdaQuery, orderItemList);

        return FmkPageResponse.of(dbPage);
    }
}