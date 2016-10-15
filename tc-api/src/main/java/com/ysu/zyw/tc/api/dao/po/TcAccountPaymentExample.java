package com.ysu.zyw.tc.api.dao.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TcAccountPaymentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer startLine;

    protected Integer pageSize;

    public TcAccountPaymentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSupWeixinIsNull() {
            addCriterion("sup_weixin is null");
            return (Criteria) this;
        }

        public Criteria andSupWeixinIsNotNull() {
            addCriterion("sup_weixin is not null");
            return (Criteria) this;
        }

        public Criteria andSupWeixinEqualTo(Boolean value) {
            addCriterion("sup_weixin =", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinNotEqualTo(Boolean value) {
            addCriterion("sup_weixin <>", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinGreaterThan(Boolean value) {
            addCriterion("sup_weixin >", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sup_weixin >=", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinLessThan(Boolean value) {
            addCriterion("sup_weixin <", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinLessThanOrEqualTo(Boolean value) {
            addCriterion("sup_weixin <=", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinLike(Boolean value) {
            addCriterion("sup_weixin like", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinNotLike(Boolean value) {
            addCriterion("sup_weixin not like", value, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinIn(List<Boolean> values) {
            addCriterion("sup_weixin in", values, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinNotIn(List<Boolean> values) {
            addCriterion("sup_weixin not in", values, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_weixin between", value1, value2, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupWeixinNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_weixin not between", value1, value2, "supWeixin");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoIsNull() {
            addCriterion("sup_zhifubao is null");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoIsNotNull() {
            addCriterion("sup_zhifubao is not null");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoEqualTo(Boolean value) {
            addCriterion("sup_zhifubao =", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoNotEqualTo(Boolean value) {
            addCriterion("sup_zhifubao <>", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoGreaterThan(Boolean value) {
            addCriterion("sup_zhifubao >", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sup_zhifubao >=", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoLessThan(Boolean value) {
            addCriterion("sup_zhifubao <", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoLessThanOrEqualTo(Boolean value) {
            addCriterion("sup_zhifubao <=", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoLike(Boolean value) {
            addCriterion("sup_zhifubao like", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoNotLike(Boolean value) {
            addCriterion("sup_zhifubao not like", value, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoIn(List<Boolean> values) {
            addCriterion("sup_zhifubao in", values, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoNotIn(List<Boolean> values) {
            addCriterion("sup_zhifubao not in", values, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_zhifubao between", value1, value2, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupZhifubaoNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_zhifubao not between", value1, value2, "supZhifubao");
            return (Criteria) this;
        }

        public Criteria andSupCodIsNull() {
            addCriterion("sup_cod is null");
            return (Criteria) this;
        }

        public Criteria andSupCodIsNotNull() {
            addCriterion("sup_cod is not null");
            return (Criteria) this;
        }

        public Criteria andSupCodEqualTo(Boolean value) {
            addCriterion("sup_cod =", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodNotEqualTo(Boolean value) {
            addCriterion("sup_cod <>", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodGreaterThan(Boolean value) {
            addCriterion("sup_cod >", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodGreaterThanOrEqualTo(Boolean value) {
            addCriterion("sup_cod >=", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodLessThan(Boolean value) {
            addCriterion("sup_cod <", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodLessThanOrEqualTo(Boolean value) {
            addCriterion("sup_cod <=", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodLike(Boolean value) {
            addCriterion("sup_cod like", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodNotLike(Boolean value) {
            addCriterion("sup_cod not like", value, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodIn(List<Boolean> values) {
            addCriterion("sup_cod in", values, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodNotIn(List<Boolean> values) {
            addCriterion("sup_cod not in", values, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_cod between", value1, value2, "supCod");
            return (Criteria) this;
        }

        public Criteria andSupCodNotBetween(Boolean value1, Boolean value2) {
            addCriterion("sup_cod not between", value1, value2, "supCod");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountIsNull() {
            addCriterion("weixin_account is null");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountIsNotNull() {
            addCriterion("weixin_account is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountEqualTo(String value) {
            addCriterion("weixin_account =", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountNotEqualTo(String value) {
            addCriterion("weixin_account <>", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountGreaterThan(String value) {
            addCriterion("weixin_account >", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_account >=", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountLessThan(String value) {
            addCriterion("weixin_account <", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountLessThanOrEqualTo(String value) {
            addCriterion("weixin_account <=", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountLike(String value) {
            addCriterion("weixin_account like", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountNotLike(String value) {
            addCriterion("weixin_account not like", value, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountIn(List<String> values) {
            addCriterion("weixin_account in", values, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountNotIn(List<String> values) {
            addCriterion("weixin_account not in", values, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountBetween(String value1, String value2) {
            addCriterion("weixin_account between", value1, value2, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andWeixinAccountNotBetween(String value1, String value2) {
            addCriterion("weixin_account not between", value1, value2, "weixinAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountIsNull() {
            addCriterion("zhifubao_account is null");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountIsNotNull() {
            addCriterion("zhifubao_account is not null");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountEqualTo(String value) {
            addCriterion("zhifubao_account =", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountNotEqualTo(String value) {
            addCriterion("zhifubao_account <>", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountGreaterThan(String value) {
            addCriterion("zhifubao_account >", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountGreaterThanOrEqualTo(String value) {
            addCriterion("zhifubao_account >=", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountLessThan(String value) {
            addCriterion("zhifubao_account <", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountLessThanOrEqualTo(String value) {
            addCriterion("zhifubao_account <=", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountLike(String value) {
            addCriterion("zhifubao_account like", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountNotLike(String value) {
            addCriterion("zhifubao_account not like", value, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountIn(List<String> values) {
            addCriterion("zhifubao_account in", values, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountNotIn(List<String> values) {
            addCriterion("zhifubao_account not in", values, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountBetween(String value1, String value2) {
            addCriterion("zhifubao_account between", value1, value2, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andZhifubaoAccountNotBetween(String value1, String value2) {
            addCriterion("zhifubao_account not between", value1, value2, "zhifubaoAccount");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonIsNull() {
            addCriterion("updated_person is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonIsNotNull() {
            addCriterion("updated_person is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonEqualTo(String value) {
            addCriterion("updated_person =", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonNotEqualTo(String value) {
            addCriterion("updated_person <>", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonGreaterThan(String value) {
            addCriterion("updated_person >", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonGreaterThanOrEqualTo(String value) {
            addCriterion("updated_person >=", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonLessThan(String value) {
            addCriterion("updated_person <", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonLessThanOrEqualTo(String value) {
            addCriterion("updated_person <=", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonLike(String value) {
            addCriterion("updated_person like", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonNotLike(String value) {
            addCriterion("updated_person not like", value, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonIn(List<String> values) {
            addCriterion("updated_person in", values, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonNotIn(List<String> values) {
            addCriterion("updated_person not in", values, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonBetween(String value1, String value2) {
            addCriterion("updated_person between", value1, value2, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedPersonNotBetween(String value1, String value2) {
            addCriterion("updated_person not between", value1, value2, "updatedPerson");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampIsNull() {
            addCriterion("updated_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampIsNotNull() {
            addCriterion("updated_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampEqualTo(Date value) {
            addCriterion("updated_timestamp =", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampNotEqualTo(Date value) {
            addCriterion("updated_timestamp <>", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampGreaterThan(Date value) {
            addCriterion("updated_timestamp >", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_timestamp >=", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampLessThan(Date value) {
            addCriterion("updated_timestamp <", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampLessThanOrEqualTo(Date value) {
            addCriterion("updated_timestamp <=", value, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampIn(List<Date> values) {
            addCriterion("updated_timestamp in", values, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampNotIn(List<Date> values) {
            addCriterion("updated_timestamp not in", values, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampBetween(Date value1, Date value2) {
            addCriterion("updated_timestamp between", value1, value2, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andUpdatedTimestampNotBetween(Date value1, Date value2) {
            addCriterion("updated_timestamp not between", value1, value2, "updatedTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonIsNull() {
            addCriterion("created_person is null");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonIsNotNull() {
            addCriterion("created_person is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonEqualTo(String value) {
            addCriterion("created_person =", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonNotEqualTo(String value) {
            addCriterion("created_person <>", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonGreaterThan(String value) {
            addCriterion("created_person >", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonGreaterThanOrEqualTo(String value) {
            addCriterion("created_person >=", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonLessThan(String value) {
            addCriterion("created_person <", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonLessThanOrEqualTo(String value) {
            addCriterion("created_person <=", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonLike(String value) {
            addCriterion("created_person like", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonNotLike(String value) {
            addCriterion("created_person not like", value, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonIn(List<String> values) {
            addCriterion("created_person in", values, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonNotIn(List<String> values) {
            addCriterion("created_person not in", values, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonBetween(String value1, String value2) {
            addCriterion("created_person between", value1, value2, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedPersonNotBetween(String value1, String value2) {
            addCriterion("created_person not between", value1, value2, "createdPerson");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampIsNull() {
            addCriterion("created_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampIsNotNull() {
            addCriterion("created_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampEqualTo(Date value) {
            addCriterion("created_timestamp =", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampNotEqualTo(Date value) {
            addCriterion("created_timestamp <>", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampGreaterThan(Date value) {
            addCriterion("created_timestamp >", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("created_timestamp >=", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampLessThan(Date value) {
            addCriterion("created_timestamp <", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampLessThanOrEqualTo(Date value) {
            addCriterion("created_timestamp <=", value, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampIn(List<Date> values) {
            addCriterion("created_timestamp in", values, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampNotIn(List<Date> values) {
            addCriterion("created_timestamp not in", values, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampBetween(Date value1, Date value2) {
            addCriterion("created_timestamp between", value1, value2, "createdTimestamp");
            return (Criteria) this;
        }

        public Criteria andCreatedTimestampNotBetween(Date value1, Date value2) {
            addCriterion("created_timestamp not between", value1, value2, "createdTimestamp");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}