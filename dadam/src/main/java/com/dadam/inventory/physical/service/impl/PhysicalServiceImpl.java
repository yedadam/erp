package com.dadam.inventory.physical.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.physical.mapper.PhysicalMapper;
import com.dadam.inventory.physical.service.PhysicalService;

@Transactional
@Service
public class PhysicalServiceImpl implements PhysicalService{
	
	@Autowired
	PhysicalMapper physicalmapper;
	
}
