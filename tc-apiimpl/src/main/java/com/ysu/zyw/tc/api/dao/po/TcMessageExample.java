package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.model.menum.TmMessageType;
import com.ysu.zyw.tc.model.menum.TmPlatform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TcMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer startLine;

    protected Integer pageSize;

    public TcMessageExample() {
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
        protected List<Criterion> typeCriteria;

        protected List<Criterion> platformCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            typeCriteria = new ArrayList<Criterion>();
            platformCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getTypeCriteria() {
            return typeCriteria;
        }

        protected void addTypeCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            typeCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        protected void addTypeCriterion(String condition, TmMessageType value1, TmMessageType value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            typeCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getPlatformCriteria() {
            return platformCriteria;
        }

        protected void addPlatformCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            platformCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        protected void addPlatformCriterion(String condition, TmPlatform value1, TmPlatform value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            platformCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                    || typeCriteria.size() > 0
                    || platformCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(typeCriteria);
                allCriteria.addAll(platformCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
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

        public Criteria andReceiverAccountIdIsNull() {
            addCriterion("receiver_account_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdIsNotNull() {
            addCriterion("receiver_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdEqualTo(String value) {
            addCriterion("receiver_account_id =", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdNotEqualTo(String value) {
            addCriterion("receiver_account_id <>", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdGreaterThan(String value) {
            addCriterion("receiver_account_id >", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdGreaterThanOrEqualTo(String value) {
            addCriterion("receiver_account_id >=", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdLessThan(String value) {
            addCriterion("receiver_account_id <", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdLessThanOrEqualTo(String value) {
            addCriterion("receiver_account_id <=", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdLike(String value) {
            addCriterion("receiver_account_id like", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdNotLike(String value) {
            addCriterion("receiver_account_id not like", value, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdIn(List<String> values) {
            addCriterion("receiver_account_id in", values, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdNotIn(List<String> values) {
            addCriterion("receiver_account_id not in", values, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdBetween(String value1, String value2) {
            addCriterion("receiver_account_id between", value1, value2, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverAccountIdNotBetween(String value1, String value2) {
            addCriterion("receiver_account_id not between", value1, value2, "receiverAccountId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdIsNull() {
            addCriterion("receiver_region_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdIsNotNull() {
            addCriterion("receiver_region_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdEqualTo(String value) {
            addCriterion("receiver_region_id =", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdNotEqualTo(String value) {
            addCriterion("receiver_region_id <>", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdGreaterThan(String value) {
            addCriterion("receiver_region_id >", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdGreaterThanOrEqualTo(String value) {
            addCriterion("receiver_region_id >=", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdLessThan(String value) {
            addCriterion("receiver_region_id <", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdLessThanOrEqualTo(String value) {
            addCriterion("receiver_region_id <=", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdLike(String value) {
            addCriterion("receiver_region_id like", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdNotLike(String value) {
            addCriterion("receiver_region_id not like", value, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdIn(List<String> values) {
            addCriterion("receiver_region_id in", values, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdNotIn(List<String> values) {
            addCriterion("receiver_region_id not in", values, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdBetween(String value1, String value2) {
            addCriterion("receiver_region_id between", value1, value2, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andReceiverRegionIdNotBetween(String value1, String value2) {
            addCriterion("receiver_region_id not between", value1, value2, "receiverRegionId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(TmMessageType value) {
            addTypeCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(TmMessageType value) {
            addTypeCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(TmMessageType value) {
            addTypeCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(TmMessageType value) {
            addTypeCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(TmMessageType value) {
            addTypeCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(TmMessageType value) {
            addTypeCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(TmMessageType value) {
            addTypeCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(TmMessageType value) {
            addTypeCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<TmMessageType> values) {
            addTypeCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<TmMessageType> values) {
            addTypeCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(TmMessageType value1, TmMessageType value2) {
            addTypeCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(TmMessageType value1, TmMessageType value2) {
            addTypeCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNull() {
            addCriterion("platform is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIsNotNull() {
            addCriterion("platform is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformEqualTo(TmPlatform value) {
            addPlatformCriterion("platform =", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotEqualTo(TmPlatform value) {
            addPlatformCriterion("platform <>", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThan(TmPlatform value) {
            addPlatformCriterion("platform >", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformGreaterThanOrEqualTo(TmPlatform value) {
            addPlatformCriterion("platform >=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThan(TmPlatform value) {
            addPlatformCriterion("platform <", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLessThanOrEqualTo(TmPlatform value) {
            addPlatformCriterion("platform <=", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformLike(TmPlatform value) {
            addPlatformCriterion("platform like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotLike(TmPlatform value) {
            addPlatformCriterion("platform not like", value, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformIn(List<TmPlatform> values) {
            addPlatformCriterion("platform in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotIn(List<TmPlatform> values) {
            addPlatformCriterion("platform not in", values, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformBetween(TmPlatform value1, TmPlatform value2) {
            addPlatformCriterion("platform between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andPlatformNotBetween(TmPlatform value1, TmPlatform value2) {
            addPlatformCriterion("platform not between", value1, value2, "platform");
            return (Criteria) this;
        }

        public Criteria andReadIsNull() {
            addCriterion("read is null");
            return (Criteria) this;
        }

        public Criteria andReadIsNotNull() {
            addCriterion("read is not null");
            return (Criteria) this;
        }

        public Criteria andReadEqualTo(Boolean value) {
            addCriterion("read =", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotEqualTo(Boolean value) {
            addCriterion("read <>", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadGreaterThan(Boolean value) {
            addCriterion("read >", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadGreaterThanOrEqualTo(Boolean value) {
            addCriterion("read >=", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadLessThan(Boolean value) {
            addCriterion("read <", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadLessThanOrEqualTo(Boolean value) {
            addCriterion("read <=", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadLike(Boolean value) {
            addCriterion("read like", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotLike(Boolean value) {
            addCriterion("read not like", value, "read");
            return (Criteria) this;
        }

        public Criteria andReadIn(List<Boolean> values) {
            addCriterion("read in", values, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotIn(List<Boolean> values) {
            addCriterion("read not in", values, "read");
            return (Criteria) this;
        }

        public Criteria andReadBetween(Boolean value1, Boolean value2) {
            addCriterion("read between", value1, value2, "read");
            return (Criteria) this;
        }

        public Criteria andReadNotBetween(Boolean value1, Boolean value2) {
            addCriterion("read not between", value1, value2, "read");
            return (Criteria) this;
        }

        public Criteria andBizKeyIsNull() {
            addCriterion("biz_key is null");
            return (Criteria) this;
        }

        public Criteria andBizKeyIsNotNull() {
            addCriterion("biz_key is not null");
            return (Criteria) this;
        }

        public Criteria andBizKeyEqualTo(String value) {
            addCriterion("biz_key =", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyNotEqualTo(String value) {
            addCriterion("biz_key <>", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyGreaterThan(String value) {
            addCriterion("biz_key >", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyGreaterThanOrEqualTo(String value) {
            addCriterion("biz_key >=", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyLessThan(String value) {
            addCriterion("biz_key <", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyLessThanOrEqualTo(String value) {
            addCriterion("biz_key <=", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyLike(String value) {
            addCriterion("biz_key like", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyNotLike(String value) {
            addCriterion("biz_key not like", value, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyIn(List<String> values) {
            addCriterion("biz_key in", values, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyNotIn(List<String> values) {
            addCriterion("biz_key not in", values, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyBetween(String value1, String value2) {
            addCriterion("biz_key between", value1, value2, "bizKey");
            return (Criteria) this;
        }

        public Criteria andBizKeyNotBetween(String value1, String value2) {
            addCriterion("biz_key not between", value1, value2, "bizKey");
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