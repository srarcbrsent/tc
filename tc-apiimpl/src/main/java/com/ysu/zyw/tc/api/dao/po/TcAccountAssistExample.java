package com.ysu.zyw.tc.api.dao.po;

import com.ysu.zyw.tc.api.dao.penum.TcPlatform;

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
        protected List<Criterion> signinPlatformCriteria;

        protected List<Criterion> lastSignupPlatformCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            signinPlatformCriteria = new ArrayList<Criterion>();
            lastSignupPlatformCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getSigninPlatformCriteria() {
            return signinPlatformCriteria;
        }

        protected void addSigninPlatformCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            signinPlatformCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        protected void addSigninPlatformCriterion(String condition, TcPlatform value1, TcPlatform value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            signinPlatformCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        public List<Criterion> getLastSignupPlatformCriteria() {
            return lastSignupPlatformCriteria;
        }

        protected void addLastSignupPlatformCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            lastSignupPlatformCriteria.add(new Criterion(condition, value, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        protected void addLastSignupPlatformCriterion(String condition, TcPlatform value1, TcPlatform value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            lastSignupPlatformCriteria.add(new Criterion(condition, value1, value2, "org.apache.ibatis.type.EnumTypeHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                    || signinPlatformCriteria.size() > 0
                    || lastSignupPlatformCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(signinPlatformCriteria);
                allCriteria.addAll(lastSignupPlatformCriteria);
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

        public Criteria andSigninPlatformIsNull() {
            addCriterion("signin_platform is null");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformIsNotNull() {
            addCriterion("signin_platform is not null");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformEqualTo(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform =", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformNotEqualTo(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform <>", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformGreaterThan(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform >", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformGreaterThanOrEqualTo(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform >=", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformLessThan(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform <", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformLessThanOrEqualTo(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform <=", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformLike(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform like", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformNotLike(TcPlatform value) {
            addSigninPlatformCriterion("signin_platform not like", value, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformIn(List<TcPlatform> values) {
            addSigninPlatformCriterion("signin_platform in", values, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformNotIn(List<TcPlatform> values) {
            addSigninPlatformCriterion("signin_platform not in", values, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformBetween(TcPlatform value1, TcPlatform value2) {
            addSigninPlatformCriterion("signin_platform between", value1, value2, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninPlatformNotBetween(TcPlatform value1, TcPlatform value2) {
            addSigninPlatformCriterion("signin_platform not between", value1, value2, "signinPlatform");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampIsNull() {
            addCriterion("signin_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampIsNotNull() {
            addCriterion("signin_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampEqualTo(Date value) {
            addCriterion("signin_timestamp =", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampNotEqualTo(Date value) {
            addCriterion("signin_timestamp <>", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampGreaterThan(Date value) {
            addCriterion("signin_timestamp >", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("signin_timestamp >=", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampLessThan(Date value) {
            addCriterion("signin_timestamp <", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampLessThanOrEqualTo(Date value) {
            addCriterion("signin_timestamp <=", value, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampIn(List<Date> values) {
            addCriterion("signin_timestamp in", values, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampNotIn(List<Date> values) {
            addCriterion("signin_timestamp not in", values, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampBetween(Date value1, Date value2) {
            addCriterion("signin_timestamp between", value1, value2, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninTimestampNotBetween(Date value1, Date value2) {
            addCriterion("signin_timestamp not between", value1, value2, "signinTimestamp");
            return (Criteria) this;
        }

        public Criteria andSigninIpIsNull() {
            addCriterion("signin_ip is null");
            return (Criteria) this;
        }

        public Criteria andSigninIpIsNotNull() {
            addCriterion("signin_ip is not null");
            return (Criteria) this;
        }

        public Criteria andSigninIpEqualTo(String value) {
            addCriterion("signin_ip =", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpNotEqualTo(String value) {
            addCriterion("signin_ip <>", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpGreaterThan(String value) {
            addCriterion("signin_ip >", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpGreaterThanOrEqualTo(String value) {
            addCriterion("signin_ip >=", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpLessThan(String value) {
            addCriterion("signin_ip <", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpLessThanOrEqualTo(String value) {
            addCriterion("signin_ip <=", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpLike(String value) {
            addCriterion("signin_ip like", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpNotLike(String value) {
            addCriterion("signin_ip not like", value, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpIn(List<String> values) {
            addCriterion("signin_ip in", values, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpNotIn(List<String> values) {
            addCriterion("signin_ip not in", values, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpBetween(String value1, String value2) {
            addCriterion("signin_ip between", value1, value2, "signinIp");
            return (Criteria) this;
        }

        public Criteria andSigninIpNotBetween(String value1, String value2) {
            addCriterion("signin_ip not between", value1, value2, "signinIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformIsNull() {
            addCriterion("last_signup_platform is null");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformIsNotNull() {
            addCriterion("last_signup_platform is not null");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformEqualTo(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform =", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformNotEqualTo(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform <>", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformGreaterThan(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform >", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformGreaterThanOrEqualTo(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform >=", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformLessThan(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform <", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformLessThanOrEqualTo(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform <=", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformLike(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform like", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformNotLike(TcPlatform value) {
            addLastSignupPlatformCriterion("last_signup_platform not like", value, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformIn(List<TcPlatform> values) {
            addLastSignupPlatformCriterion("last_signup_platform in", values, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformNotIn(List<TcPlatform> values) {
            addLastSignupPlatformCriterion("last_signup_platform not in", values, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformBetween(TcPlatform value1, TcPlatform value2) {
            addLastSignupPlatformCriterion("last_signup_platform between", value1, value2, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupPlatformNotBetween(TcPlatform value1, TcPlatform value2) {
            addLastSignupPlatformCriterion("last_signup_platform not between", value1, value2, "lastSignupPlatform");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampIsNull() {
            addCriterion("last_signup_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampIsNotNull() {
            addCriterion("last_signup_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampEqualTo(Date value) {
            addCriterion("last_signup_timestamp =", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampNotEqualTo(Date value) {
            addCriterion("last_signup_timestamp <>", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampGreaterThan(Date value) {
            addCriterion("last_signup_timestamp >", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampGreaterThanOrEqualTo(Date value) {
            addCriterion("last_signup_timestamp >=", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampLessThan(Date value) {
            addCriterion("last_signup_timestamp <", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampLessThanOrEqualTo(Date value) {
            addCriterion("last_signup_timestamp <=", value, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampIn(List<Date> values) {
            addCriterion("last_signup_timestamp in", values, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampNotIn(List<Date> values) {
            addCriterion("last_signup_timestamp not in", values, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampBetween(Date value1, Date value2) {
            addCriterion("last_signup_timestamp between", value1, value2, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupTimestampNotBetween(Date value1, Date value2) {
            addCriterion("last_signup_timestamp not between", value1, value2, "lastSignupTimestamp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpIsNull() {
            addCriterion("last_signup_ip is null");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpIsNotNull() {
            addCriterion("last_signup_ip is not null");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpEqualTo(String value) {
            addCriterion("last_signup_ip =", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpNotEqualTo(String value) {
            addCriterion("last_signup_ip <>", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpGreaterThan(String value) {
            addCriterion("last_signup_ip >", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpGreaterThanOrEqualTo(String value) {
            addCriterion("last_signup_ip >=", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpLessThan(String value) {
            addCriterion("last_signup_ip <", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpLessThanOrEqualTo(String value) {
            addCriterion("last_signup_ip <=", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpLike(String value) {
            addCriterion("last_signup_ip like", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpNotLike(String value) {
            addCriterion("last_signup_ip not like", value, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpIn(List<String> values) {
            addCriterion("last_signup_ip in", values, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpNotIn(List<String> values) {
            addCriterion("last_signup_ip not in", values, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpBetween(String value1, String value2) {
            addCriterion("last_signup_ip between", value1, value2, "lastSignupIp");
            return (Criteria) this;
        }

        public Criteria andLastSignupIpNotBetween(String value1, String value2) {
            addCriterion("last_signup_ip not between", value1, value2, "lastSignupIp");
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