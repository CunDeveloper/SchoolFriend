package com.nju.service;

import com.nju.dao.impl.BaseDaoImpl;
import com.nju.dao.impl.PraiseDaoImpl;
import com.nju.model.Praise;

public class PraiseService {
	private BaseDaoImpl<Praise> baseDao;
	
	public PraiseService() {
		baseDao = new PraiseDaoImpl();
	}

	public int save(Praise praise) {
		return baseDao.save(praise);
	}
}
