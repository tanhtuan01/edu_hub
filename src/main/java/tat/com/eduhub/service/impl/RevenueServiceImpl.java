package tat.com.eduhub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tat.com.eduhub.entity.Revenue;
import tat.com.eduhub.repository.RevenueRepository;
import tat.com.eduhub.service.RevenueService;

@Service
public class RevenueServiceImpl implements RevenueService{

	@Autowired
	private RevenueRepository repository;
	
	@Override
	public Revenue save(Revenue revenue) {
		// TODO Auto-generated method stub
		return repository.save(revenue);
	}
}
