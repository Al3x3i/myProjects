package com.brouwershuis.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.ShiftDAO;
import com.brouwershuis.db.model.Shift;

@Service
public class ShiftService {

	@Inject
	ShiftDAO shiftDao;
	
	@Transactional
	public List<Shift> findAll(){
		return shiftDao.findAll();
	}
}
