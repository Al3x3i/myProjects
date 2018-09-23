package com.brouwershuis.service;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.brouwershuis.db.dao.ContractDAO;
import com.brouwershuis.db.model.Contract;

@Service
public class ContractService {

	@Inject
	ContractDAO contractDAO;
	
	@Transactional
	public List<Contract> findAll(){
		return contractDAO.findAll();
	}
	
	@Transactional
	public Contract findByName(String name){
		return contractDAO.finByName(name);
	}
	
}
