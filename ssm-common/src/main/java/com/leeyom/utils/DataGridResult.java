package com.leeyom.utils;

import java.util.List;

public class DataGridResult {


	/**
	 * description: 分页结果封装
	 * author: leeyom
	 * date: 2017-07-31 09:43
	 * Copyright © 2017 by leeyom
	 */
	// 总的记录数
	Long total;
	// 数据集
	List<?> rows;

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
