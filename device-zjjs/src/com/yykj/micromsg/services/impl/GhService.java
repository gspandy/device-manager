package com.yykj.micromsg.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yykj.micromsg.services.IGhService;
import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Doctor4003;
import com.yykj.system.entity.hisentity.Info4002;
import com.yykj.system.entity.hisentity.Info4003;
import com.yykj.system.socket.socketclient4his.HandlerHis4001;
import com.yykj.system.socket.socketclient4his.HandlerHis4002;
import com.yykj.system.socket.socketclient4his.HandlerHis4003;

@Service("ghService")
public class GhService implements IGhService {

	@Override
	public List<String> getGhType() {
		List<String> list = new ArrayList<String>();
		try {
			list= HandlerHis4001.handler4001();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Dept4002> getGhDept(Map<String, String> map) {
		List<Dept4002> depts = new ArrayList<Dept4002>();
		try {
			Info4002 info = HandlerHis4002.handler4002(map);
			depts = info.getDepts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return depts;
	}

	@Override
	public List<Doctor4003> getDoctor(Map<String, String> map) {
		List<Doctor4003> doctors = new ArrayList<Doctor4003>();
		try {
			Info4003 info = HandlerHis4003.handler4003(map);
			doctors = info.getDoctors();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doctors;
	}
}
