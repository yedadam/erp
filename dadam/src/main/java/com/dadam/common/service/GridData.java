package com.dadam.common.service;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import lombok.Data;

@Data
public class GridData<T> {
		private List<T> updatedRows;
		private List<T> deleteRows; 
		private List<T> createdRows; 
}
