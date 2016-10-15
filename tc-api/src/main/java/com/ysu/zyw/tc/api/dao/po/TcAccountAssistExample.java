package com.ysu.zyw.tc.api.dao.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TcAccountAssistExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer startLine;

    protected Integer pageSize;

    public TcAccountAssistExample() {
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

        public Criteria andSignupPlatformIsNull() {
            addCriterion("signup_platform is null");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformIsNotNull() {
            addCriterion("signup_platform is not null");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformEqualTo(String value) {
            addCriterion("signup_platform =", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformNotEqualTo(String value) {
            addCriterion("signup_platform <>", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformGreaterThan(String value) {
            addCriterion("signup_platform >", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformGreaterThanOrEqualTo(String value) {
            addCriterion("signup_platform >=", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformLessThan(String value) {
            addCriterion("signup_platform <", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformLessThanOrEqualTo(String value) {
            addCriterion("signup_platform <=", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformLike(String value) {
            addCriterion("signup_platform like", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformNotLike(String value) {
            addCriterion("signup_platform not like", value, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformIn(List<String> values) {
            addCriterion("signup_platform in", values, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformNotIn(List<String> values) {
            addCriterion("signup_platform not in", values, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformBetween(String value1, String value2) {
            addCriterion("signup_platform between", value1, value2, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupPlatformNotBetween(String value1, String value2) {
            addCriterion("signup_platform not between", value1, value2, "signupPlatform");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampIsNull() {
            addCriterion("signup_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampIsNotNull() {
            addCriterion("signup_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampEqualTo(Date value) {
            addCriterion("signup_timestamp =", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampNotEqualTo(Date value) {
            addCriterion("signup_timestamp <>", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampGreaterThan(Date value) {
            addCriterion("signup_timestamp >", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("signup_timestamp >=", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampLessThan(Date value) {
            addCriterion("signup_timestamp <", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampLessThanOrEqualTo(Date value) {
            addCriterion("signup_timestamp <=", value, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampIn(List<Date> values) {
            addCriterion("signup_timestamp in", values, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampNotIn(List<Date> values) {
            addCriterion("signup_timestamp not in", values, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampBetween(Date value1, Date value2) {
            addCriterion("signup_timestamp between", value1, value2, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andSignupTimestampNotBetween(Date value1, Date value2) {
            addCriterion("signup_timestamp not between", value1, value2, "signupTimestamp");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedIsNull() {
            addCriterion("phone_activated is null");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedIsNotNull() {
            addCriterion("phone_activated is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedEqualTo(Boolean value) {
            addCriterion("phone_activated =", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedNotEqualTo(Boolean value) {
            addCriterion("phone_activated <>", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedGreaterThan(Boolean value) {
            addCriterion("phone_activated >", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("phone_activated >=", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedLessThan(Boolean value) {
            addCriterion("phone_activated <", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedLessThanOrEqualTo(Boolean value) {
            addCriterion("phone_activated <=", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedLike(Boolean value) {
            addCriterion("phone_activated like", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedNotLike(Boolean value) {
            addCriterion("phone_activated not like", value, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedIn(List<Boolean> values) {
            addCriterion("phone_activated in", values, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedNotIn(List<Boolean> values) {
            addCriterion("phone_activated not in", values, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedBetween(Boolean value1, Boolean value2) {
            addCriterion("phone_activated between", value1, value2, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andPhoneActivatedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("phone_activated not between", value1, value2, "phoneActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedIsNull() {
            addCriterion("email_activated is null");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedIsNotNull() {
            addCriterion("email_activated is not null");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedEqualTo(Boolean value) {
            addCriterion("email_activated =", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedNotEqualTo(Boolean value) {
            addCriterion("email_activated <>", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedGreaterThan(Boolean value) {
            addCriterion("email_activated >", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("email_activated >=", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedLessThan(Boolean value) {
            addCriterion("email_activated <", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedLessThanOrEqualTo(Boolean value) {
            addCriterion("email_activated <=", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedLike(Boolean value) {
            addCriterion("email_activated like", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedNotLike(Boolean value) {
            addCriterion("email_activated not like", value, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedIn(List<Boolean> values) {
            addCriterion("email_activated in", values, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedNotIn(List<Boolean> values) {
            addCriterion("email_activated not in", values, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedBetween(Boolean value1, Boolean value2) {
            addCriterion("email_activated between", value1, value2, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andEmailActivatedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("email_activated not between", value1, value2, "emailActivated");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampIsNull() {
            addCriterion("last_login_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampIsNotNull() {
            addCriterion("last_login_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampEqualTo(Date value) {
            addCriterion("last_login_timestamp =", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampNotEqualTo(Date value) {
            addCriterion("last_login_timestamp <>", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampGreaterThan(Date value) {
            addCriterion("last_login_timestamp >", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("last_login_timestamp >=", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampLessThan(Date value) {
            addCriterion("last_login_timestamp <", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampLessThanOrEqualTo(Date value) {
            addCriterion("last_login_timestamp <=", value, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampIn(List<Date> values) {
            addCriterion("last_login_timestamp in", values, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampNotIn(List<Date> values) {
            addCriterion("last_login_timestamp not in", values, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampBetween(Date value1, Date value2) {
            addCriterion("last_login_timestamp between", value1, value2, "lastLoginTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastLoginTimestampNotBetween(Date value1, Date value2) {
            addCriterion("last_login_timestamp not between", value1, value2, "lastLoginTimestamp");
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