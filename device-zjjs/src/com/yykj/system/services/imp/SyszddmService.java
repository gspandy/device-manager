package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.base.services.imp.BaseService;
import com.yykj.system.dao.ISysZddmDao;
import com.yykj.system.entity.SysZddm;
import com.yykj.system.services.ISysZddmService;

@Service("syszddmService")
public class SyszddmService extends BaseService implements ISysZddmService {

	@Resource(name="sysZddmDao")
	private ISysZddmDao zddmDao;
	
	@Override
	public SysZddm getZddmByid(int id) {
		return (SysZddm)this.getEntitie(SysZddm.class, id);
	}

	@Override
	public List<SysZddm> getZddmByClassType(String classname) {
		return zddmDao.getSysZddmByClassType(classname);
	}

	@Override
	public List<SysZddm> getAllZddm() {
		return this.findByExample(new SysZddm());
	}

	@Override
	public boolean updateJylsh(String lsh) {
		return zddmDao.updateJylsh(lsh);
	}

	@Override
	public String getJylsh() {
		return zddmDao.getJylsh();
	}
	
}
