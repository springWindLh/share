package com.share.web.daoImpl;

import org.springframework.stereotype.Repository;

import com.share.support.daoImpl.BaseDaoImpl;
import com.share.web.dao.ITypeDao;
import com.share.web.entity.Type;

@Repository("typeDao")
public class TypeDaoImpl extends BaseDaoImpl<Type> implements ITypeDao{

}
