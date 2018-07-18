package com.ns.common.dao;

import com.ns.common.bean.Param;
import com.ns.common.dao.spi.jpa.JpaDao;

import java.util.List;

public interface ParamDao extends JpaDao<Param, String> {

    List<Param> findByParentName(String parentName);

}
