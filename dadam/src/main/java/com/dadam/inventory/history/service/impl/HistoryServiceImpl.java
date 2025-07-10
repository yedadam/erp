package com.dadam.inventory.history.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dadam.inventory.history.mapper.HistoryMapper;
import com.dadam.inventory.history.service.HistoryService;

@Transactional
@Service
public class HistoryServiceImpl implements HistoryService{
	
	@Autowired
	HistoryMapper historymapper;
	
}
