package com.ysu.zyw.tc.api.dao.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TcShopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer startLine;

    protected Integer pageSize;

    public TcShopExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andLocationIsNull() {
            addCriterion("location is null");
            return (Criteria) this;
        }

        public Criteria andLocationIsNotNull() {
            addCriterion("location is not null");
            return (Criteria) this;
        }

        public Criteria andLocationEqualTo(String value) {
            addCriterion("location =", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotEqualTo(String value) {
            addCriterion("location <>", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThan(String value) {
            addCriterion("location >", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationGreaterThanOrEqualTo(String value) {
            addCriterion("location >=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThan(String value) {
            addCriterion("location <", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLessThanOrEqualTo(String value) {
            addCriterion("location <=", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationLike(String value) {
            addCriterion("location like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotLike(String value) {
            addCriterion("location not like", value, "location");
            return (Criteria) this;
        }

        public Criteria andLocationIn(List<String> values) {
            addCriterion("location in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotIn(List<String> values) {
            addCriterion("location not in", values, "location");
            return (Criteria) this;
        }

        public Criteria andLocationBetween(String value1, String value2) {
            addCriterion("location between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andLocationNotBetween(String value1, String value2) {
            addCriterion("location not between", value1, value2, "location");
            return (Criteria) this;
        }

        public Criteria andDescribingRateIsNull() {
            addCriterion("describing_rate is null");
            return (Criteria) this;
        }

        public Criteria andDescribingRateIsNotNull() {
            addCriterion("describing_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDescribingRateEqualTo(Integer value) {
            addCriterion("describing_rate =", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateNotEqualTo(Integer value) {
            addCriterion("describing_rate <>", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateGreaterThan(Integer value) {
            addCriterion("describing_rate >", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("describing_rate >=", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateLessThan(Integer value) {
            addCriterion("describing_rate <", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateLessThanOrEqualTo(Integer value) {
            addCriterion("describing_rate <=", value, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateIn(List<Integer> values) {
            addCriterion("describing_rate in", values, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateNotIn(List<Integer> values) {
            addCriterion("describing_rate not in", values, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateBetween(Integer value1, Integer value2) {
            addCriterion("describing_rate between", value1, value2, "describingRate");
            return (Criteria) this;
        }

        public Criteria andDescribingRateNotBetween(Integer value1, Integer value2) {
            addCriterion("describing_rate not between", value1, value2, "describingRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateIsNull() {
            addCriterion("service_rate is null");
            return (Criteria) this;
        }

        public Criteria andServiceRateIsNotNull() {
            addCriterion("service_rate is not null");
            return (Criteria) this;
        }

        public Criteria andServiceRateEqualTo(Integer value) {
            addCriterion("service_rate =", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateNotEqualTo(Integer value) {
            addCriterion("service_rate <>", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateGreaterThan(Integer value) {
            addCriterion("service_rate >", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("service_rate >=", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateLessThan(Integer value) {
            addCriterion("service_rate <", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateLessThanOrEqualTo(Integer value) {
            addCriterion("service_rate <=", value, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateIn(List<Integer> values) {
            addCriterion("service_rate in", values, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateNotIn(List<Integer> values) {
            addCriterion("service_rate not in", values, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateBetween(Integer value1, Integer value2) {
            addCriterion("service_rate between", value1, value2, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andServiceRateNotBetween(Integer value1, Integer value2) {
            addCriterion("service_rate not between", value1, value2, "serviceRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateIsNull() {
            addCriterion("delivery_rate is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateIsNotNull() {
            addCriterion("delivery_rate is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateEqualTo(Integer value) {
            addCriterion("delivery_rate =", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateNotEqualTo(Integer value) {
            addCriterion("delivery_rate <>", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateGreaterThan(Integer value) {
            addCriterion("delivery_rate >", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_rate >=", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateLessThan(Integer value) {
            addCriterion("delivery_rate <", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_rate <=", value, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateIn(List<Integer> values) {
            addCriterion("delivery_rate in", values, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateNotIn(List<Integer> values) {
            addCriterion("delivery_rate not in", values, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateBetween(Integer value1, Integer value2) {
            addCriterion("delivery_rate between", value1, value2, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andDeliveryRateNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_rate not between", value1, value2, "deliveryRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateIsNull() {
            addCriterion("comprehensive_rate is null");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateIsNotNull() {
            addCriterion("comprehensive_rate is not null");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateEqualTo(Integer value) {
            addCriterion("comprehensive_rate =", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateNotEqualTo(Integer value) {
            addCriterion("comprehensive_rate <>", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateGreaterThan(Integer value) {
            addCriterion("comprehensive_rate >", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("comprehensive_rate >=", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateLessThan(Integer value) {
            addCriterion("comprehensive_rate <", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateLessThanOrEqualTo(Integer value) {
            addCriterion("comprehensive_rate <=", value, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateIn(List<Integer> values) {
            addCriterion("comprehensive_rate in", values, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateNotIn(List<Integer> values) {
            addCriterion("comprehensive_rate not in", values, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateBetween(Integer value1, Integer value2) {
            addCriterion("comprehensive_rate between", value1, value2, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andComprehensiveRateNotBetween(Integer value1, Integer value2) {
            addCriterion("comprehensive_rate not between", value1, value2, "comprehensiveRate");
            return (Criteria) this;
        }

        public Criteria andSupportCodIsNull() {
            addCriterion("support_cod is null");
            return (Criteria) this;
        }

        public Criteria andSupportCodIsNotNull() {
            addCriterion("support_cod is not null");
            return (Criteria) this;
        }

        public Criteria andSupportCodEqualTo(Boolean value) {
            addCriterion("support_cod =", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodNotEqualTo(Boolean value) {
            addCriterion("support_cod <>", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodGreaterThan(Boolean value) {
            addCriterion("support_cod >", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodGreaterThanOrEqualTo(Boolean value) {
            addCriterion("support_cod >=", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodLessThan(Boolean value) {
            addCriterion("support_cod <", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodLessThanOrEqualTo(Boolean value) {
            addCriterion("support_cod <=", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodLike(Boolean value) {
            addCriterion("support_cod like", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodNotLike(Boolean value) {
            addCriterion("support_cod not like", value, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodIn(List<Boolean> values) {
            addCriterion("support_cod in", values, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodNotIn(List<Boolean> values) {
            addCriterion("support_cod not in", values, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodBetween(Boolean value1, Boolean value2) {
            addCriterion("support_cod between", value1, value2, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportCodNotBetween(Boolean value1, Boolean value2) {
            addCriterion("support_cod not between", value1, value2, "supportCod");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayIsNull() {
            addCriterion("support_online_pay is null");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayIsNotNull() {
            addCriterion("support_online_pay is not null");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayEqualTo(Boolean value) {
            addCriterion("support_online_pay =", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayNotEqualTo(Boolean value) {
            addCriterion("support_online_pay <>", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayGreaterThan(Boolean value) {
            addCriterion("support_online_pay >", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayGreaterThanOrEqualTo(Boolean value) {
            addCriterion("support_online_pay >=", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayLessThan(Boolean value) {
            addCriterion("support_online_pay <", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayLessThanOrEqualTo(Boolean value) {
            addCriterion("support_online_pay <=", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayLike(Boolean value) {
            addCriterion("support_online_pay like", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayNotLike(Boolean value) {
            addCriterion("support_online_pay not like", value, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayIn(List<Boolean> values) {
            addCriterion("support_online_pay in", values, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayNotIn(List<Boolean> values) {
            addCriterion("support_online_pay not in", values, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayBetween(Boolean value1, Boolean value2) {
            addCriterion("support_online_pay between", value1, value2, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andSupportOnlinePayNotBetween(Boolean value1, Boolean value2) {
            addCriterion("support_online_pay not between", value1, value2, "supportOnlinePay");
            return (Criteria) this;
        }

        public Criteria andLockedIsNull() {
            addCriterion("locked is null");
            return (Criteria) this;
        }

        public Criteria andLockedIsNotNull() {
            addCriterion("locked is not null");
            return (Criteria) this;
        }

        public Criteria andLockedEqualTo(Boolean value) {
            addCriterion("locked =", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotEqualTo(Boolean value) {
            addCriterion("locked <>", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThan(Boolean value) {
            addCriterion("locked >", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("locked >=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThan(Boolean value) {
            addCriterion("locked <", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLessThanOrEqualTo(Boolean value) {
            addCriterion("locked <=", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedLike(Boolean value) {
            addCriterion("locked like", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotLike(Boolean value) {
            addCriterion("locked not like", value, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedIn(List<Boolean> values) {
            addCriterion("locked in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotIn(List<Boolean> values) {
            addCriterion("locked not in", values, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedBetween(Boolean value1, Boolean value2) {
            addCriterion("locked between", value1, value2, "locked");
            return (Criteria) this;
        }

        public Criteria andLockedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("locked not between", value1, value2, "locked");
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